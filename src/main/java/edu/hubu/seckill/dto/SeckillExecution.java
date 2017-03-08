package edu.hubu.seckill.dto;

import edu.hubu.seckill.entity.SeckillSuccess;
import edu.hubu.seckill.enums.SeckillStatEnum;

/**
 * 封装秒杀执行后
 * @author sucan
 *
 */

public class SeckillExecution {

	private long seckillId;
	//执行结果状态
	private int state;
	//状态表示
	private String stateInfo;
	//秒杀成功对象
	private SeckillSuccess seckillSuccess;
	
	public SeckillExecution(long seckillId, SeckillStatEnum statEnum, SeckillSuccess seckillSuccess) {
		super();
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
		this.seckillSuccess = seckillSuccess;
	}
	
	public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
		super();
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
	}

	public long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public SeckillSuccess getSeckillSuccess() {
		return seckillSuccess;
	}
	public void setSeckillSuccess(SeckillSuccess seckillSuccess) {
		this.seckillSuccess = seckillSuccess;
	}
	
	
	
}
