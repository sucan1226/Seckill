package edu.hubu.seckill.entity;

import java.util.Date;

/**
 * 秒杀明细实体
 * @author sucan
 *
 */

public class SeckillSuccess {

	private long seckillId;
	private long userPhone;
	private short state;
	private Date createTime;
	
	//多对一（一个seckill对应多个seckillsuccess）
	private Seckill seckill;
	
	public long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}
	public long getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}
	public short getState() {
		return state;
	}
	public void setState(short state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Seckill getSeckill() {
		return seckill;
	}
	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}
	//重写toString
	@Override
	public String toString() {
		return "SeckillSuccess [seckillId=" + seckillId + ", userPhone=" + userPhone + ", state=" + state
				+ ", createTime=" + createTime + ", seckill=" + seckill + "]";
	}
	
}
