package edu.hubu.seckill.service.impl;

import java.util.Date;
import java.util.List;

import javax.xml.ws.soap.Addressing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import edu.hubu.seckill.dao.SeckillDao;
import edu.hubu.seckill.dao.SeckillSuccessDao;
import edu.hubu.seckill.dao.cache.RedisDao;
import edu.hubu.seckill.dto.Exposer;
import edu.hubu.seckill.dto.SeckillExecution;
import edu.hubu.seckill.entity.Seckill;
import edu.hubu.seckill.entity.SeckillSuccess;
import edu.hubu.seckill.enums.SeckillStatEnum;
import edu.hubu.seckill.exception.RepeatKillException;
import edu.hubu.seckill.exception.SeckillCloseException;
import edu.hubu.seckill.exception.SeckillExceprion;
import edu.hubu.seckill.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//注入service依赖
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private SeckillSuccessDao seckillSuccessDao;
	
	@Autowired
	private RedisDao redisDao;
	
	//MD5盐值字符串，用于混淆MD5
	private final String slat = "sdasdsddsDERTTRTR#$%%DR";

	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
		 
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
		
	}

	public Exposer exportSeckillUrl(long seckillId) {
		//优化点:缓存优化:超时的基础上维护一致性
		/**
		 * get from cache
		 * if null
		 * 	get db
		 * 	else
		 * 		put cache
		 * logic
		 */
		//1. 访问dedis
		Seckill seckill = redisDao.getSeckill(seckillId);
		if(seckill == null){
			//2.访问数据库
			seckill = seckillDao.queryById(seckillId);
			if(seckill != null){
				//3.加入redis
				redisDao.putSeckill(seckill);
			}else{
				//如果秒杀不存在
				return new Exposer(false, seckillId);
			}
		}
		
		Date start =seckill.getStartTime();
		Date end = seckill.getEndTime();
		Date now = new Date();
		//秒杀开启或者已经结束
		if(start.getTime() > now.getTime() || end.getTime() < now.getTime()){
			return new Exposer(false, seckillId, now.getTime(),start.getTime(),end.getTime());
		}
		//转换特定字符串的过程，不可逆
		String md5 = getMD5(seckillId); //TODO
		return new Exposer(true, md5, seckillId);
	}
	
	//产生md5
	private String getMD5(long seckillId){
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	@Transactional
	/**
	 * 使用注解控制事务方法的优点:
	 * 1.开发团队达成一致约定,明确标注事务方法的编程风格
	 * 2.保证事务方法的执行时间尽可能短,不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
	 * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制。
	 */
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillExceprion, SeckillCloseException, RepeatKillException {
		if(md5 == null || !md5.equals(getMD5(seckillId))){
			throw new SeckillExceprion("seckill data rewrite");
		}
		//执行秒杀逻辑:减库存，加记录秒杀行为
		Date now = new Date();
		//减库存
		try {
			int updateCount = seckillDao.reduceNumber(seckillId,now);
			if(updateCount <= 0){
				//没有更新记录，秒杀结束
				throw new SeckillCloseException("seckill is closed");	
			}else{
				//记录购买行为
				int insertCount = seckillSuccessDao.insertSuccessKill(seckillId, userPhone);
				if(insertCount <= 0){
					throw new RepeatKillException("seckill repeated");
				}else{
					//秒杀成功
					SeckillSuccess seckillSuccess = seckillSuccessDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, seckillSuccess);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		}catch (RepeatKillException e2) {
			throw e2;
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			//所有编译期异常转换成运行期异常
			throw new SeckillExceprion("seckill inner error:"+e.getMessage());
		}
	}

}
