package edu.hubu.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.hubu.seckill.entity.Seckill;

public interface SeckillDao {
	/**
	 * 减库存
	 * @param seckillId
	 * @param killTime
	 * @return 如果影响行数>1,表示更新的记录行数
	 */
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	
	/**
	 * 根据id查询秒杀对象
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(long seckillId);
	/**
	 * 根据偏移量 查询所有
	 * @param offest
	 * @param limit
	 * @return
	 */
	List<Seckill> queryAll(@Param("offest")int offest,@Param("limit")int limit);
}
