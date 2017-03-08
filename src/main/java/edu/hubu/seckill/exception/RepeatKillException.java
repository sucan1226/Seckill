package edu.hubu.seckill.exception;

/**
 * 重复秒杀异常(运行期异常)
 * @author sucan
 *
 */

public class RepeatKillException extends SeckillExceprion {
	
	public RepeatKillException(String message){
		super(message);
	}
	
	public RepeatKillException(String message,Throwable cause){
		super(message,cause);
	}
}
