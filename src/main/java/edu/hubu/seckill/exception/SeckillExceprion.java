package edu.hubu.seckill.exception;

/**
 * 秒杀业务相关异常
 * @author sucan
 *
 */

public class SeckillExceprion extends RuntimeException {

	public SeckillExceprion(String message){
		super(message);
	}
	
	public SeckillExceprion(String message,Throwable cause){
		super(message,cause);
	}
}
