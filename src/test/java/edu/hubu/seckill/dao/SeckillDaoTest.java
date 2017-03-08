package edu.hubu.seckill.dao;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.hubu.seckill.entity.Seckill;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 * spring-test,junit
 * @author sucan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	//注入实现依赖
	@Resource
	private SeckillDao seckillDao;

	@Test
	public void testReduceNumber() {
		Date kiillTime = new Date();
		int reduceNum = seckillDao.reduceNumber(1000L,kiillTime);
		System.out.println(reduceNum);
	}

	@Test
	public void testQueryById() {
		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
		/**
		 * 1000元秒杀iphone6s
		   Seckill [seckillId=1000, name=1000元秒杀iphone6s, number=0, startTime=Mon Feb 20 00:00:00 CST 2017, endTime=Sat Feb 21 00:00:00 CST 2015,
 		   createTime=Thu Mar 02 16:20:55 CST 2017]
		 */
	}
	
	//java没有保存形参的记录()
	@Test
	public void testQueryAll() {
		List<Seckill> seckills = seckillDao.queryAll(0, 100);
		for(Seckill seckill:seckills){
			System.out.println(seckill);
		}
		/**
		 * 结果：
		 * Seckill [seckillId=1000, name=1000元秒杀iphone6s, number=100, startTime=Mon Feb 20 00:00:00 CST 2017, endTime=Sat Feb 21 00:00:00 CST 2015, createTime=Thu Mar 02 16:20:55 CST 2017]
		   Seckill [seckillId=1001, name=500元秒杀ipad2, number=200, startTime=Mon Feb 20 00:00:00 CST 2017, endTime=Sat Feb 21 00:00:00 CST 2015, createTime=Thu Mar 02 16:20:55 CST 2017]
           Seckill [seckillId=1002, name=300元秒杀小米5s, number=300, startTime=Mon Feb 20 00:00:00 CST 2017, endTime=Sat Feb 21 00:00:00 CST 2015, createTime=Thu Mar 02 16:20:55 CST 2017]
           Seckill [seckillId=1003, name=200元秒杀红米Note, number=400, startTime=Mon Feb 20 00:00:00 CST 2017, endTime=Sat Feb 21 00:00:00 CST 2015, createTime=Thu Mar 02 16:20:55 CST 2017]

		 */
	}
}
