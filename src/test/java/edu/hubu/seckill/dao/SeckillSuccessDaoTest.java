package edu.hubu.seckill.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.hubu.seckill.entity.SeckillSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillSuccessDaoTest {
	
	
	@Resource
	private SeckillSuccessDao seckillSuccessDao;

	@Test
	public void testInsertSuccessKill() {
		
		long id = 1000L;
		long phone = 13129979382L;
		int insertCount = seckillSuccessDao.insertSuccessKill(id, phone);	
		System.out.println(insertCount);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		long id = 1000L;
		long phone = 13129979382L;
		SeckillSuccess seckillSuccess = seckillSuccessDao.queryByIdWithSeckill(id, phone);
		System.out.println(seckillSuccess);
		System.out.println(seckillSuccess.getSeckill());
		
	}

}
