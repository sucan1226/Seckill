package edu.hubu.seckill.web;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.hubu.seckill.dto.Exposer;
import edu.hubu.seckill.dto.SeckillExecution;
import edu.hubu.seckill.dto.SeckillResult;
import edu.hubu.seckill.entity.Seckill;
import edu.hubu.seckill.enums.SeckillStatEnum;
import edu.hubu.seckill.exception.RepeatKillException;
import edu.hubu.seckill.exception.SeckillCloseException;
import edu.hubu.seckill.exception.SeckillExceprion;
import edu.hubu.seckill.service.SeckillService;

@Controller
@RequestMapping("/seckill") //url:模块/资源/{id}细分
public class SeckillController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	/**
	 * 获取列表页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model){
		//获取列表页
		List<Seckill> list = seckillService.getSeckillList();
		model.addAttribute("list",list);
		//list:jsp + model = modelAndView
		return "list";
		
	}
	
	/**
	 * 根据id取详情页
	 * @param seckillId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
	public String detail(@PathVariable("seckillId")Long seckillId,Model model){
		if(seckillId == null){
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if(seckill == null){
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}
	
	/**
	 * 获取秒杀地址，json格式
	 * @param seckillId
	 * @return
	 */
	//ajax json
	@RequestMapping(value="/{seckillId}/exposer",
			method=RequestMethod.POST,
			produces={"application/json;charset=UTF-8"})
	public @ResponseBody SeckillResult<Exposer> exposer(@PathVariable("seckillId")Long seckillId){
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result =  new SeckillResult<Exposer>(true,exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
		
	}
	
	/**
	 * 执行秒杀，json
	 * @param seckillId
	 * @param md5
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/{seckillId}/{md5}/execution",
					method=RequestMethod.POST,
					produces={"application/json;charset=UTF-8"})
	public @ResponseBody SeckillResult<SeckillExecution> execute(@PathVariable("seckillId")Long seckillId,
												   @PathVariable("md5")String md5,
												   @CookieValue(value = "killPhone",required = false)Long phone){
		if(phone == null){
			return new SeckillResult<SeckillExecution>(false,"未注册");
		}
		
		SeckillResult<SeckillExecution> result;
		try {
			SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
			result = new SeckillResult<SeckillExecution>(true, execution);
		} catch (SeckillCloseException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
			result = new SeckillResult<SeckillExecution>(true, execution);
		} catch (RepeatKillException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
			result = new SeckillResult<SeckillExecution>(true, execution);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
			result = new SeckillResult<SeckillExecution>(true, execution);
		}
		
		return result;
	}
	
	/**
	 * 获取系统时间，json
	 * @return
	 */
	@RequestMapping(value="/time/now",
					method=RequestMethod.GET
					)
	@ResponseBody 
	public SeckillResult<Long> time(){
		Date now  = new Date();
		return new SeckillResult<Long>(true,now.getTime());
	}
}
