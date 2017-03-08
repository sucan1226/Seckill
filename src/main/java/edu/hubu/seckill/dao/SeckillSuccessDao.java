package edu.hubu.seckill.dao;

import org.apache.ibatis.annotations.Param;

import edu.hubu.seckill.entity.SeckillSuccess;

public interface SeckillSuccessDao {
	
	
	/**
	 * 秒杀成功向秒杀明细表中插入一条记录，可过滤重复
	 * @param seckillId
	 * @param userPhone
	 * @return  插入的行数
	 */
	int insertSuccessKill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
	/**
	 * 
	 * @param seckillId
	 * @return 根据id查询SuccessKilled并携带秒杀产品实体对象
	 */
	SeckillSuccess queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
}
