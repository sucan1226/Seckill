package edu.hubu.seckill.dao.cache;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.hubu.seckill.dao.SeckillDao;
import edu.hubu.seckill.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class RedisDaoTest {
	private long id = 1001;
	@Autowired
	private RedisDao resdisDao;
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void testSeckill() {
		//get and put
		Seckill seckill = resdisDao.getSeckill(id);
		System.out.println(seckill);
		if(seckill == null){
			seckill = seckillDao.queryById(id);
			if(seckill != null){
				String result = resdisDao.putSeckill(seckill);
				System.out.println(result);
				seckill = resdisDao.getSeckill(id);
				System.out.println(seckill);
			}
		}
	}

}
