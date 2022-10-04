package kr.co.seoulit.insa.attdsvc.attdmgmt.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kr.co.seoulit.insa.attdsvc.attdmgmt.service.AttdMgmtService;
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.DayAttdTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.to.ResultTO;

@RestController
@RequestMapping("/attdmgmt/*")
public class DailyAttendanceController {
	
	@Autowired
	private AttdMgmtService attdMgmtService;
	ModelMap map = null;
	
	@GetMapping("daily-attnd")
	public ModelMap findDayAttdList(HttpServletRequest request, HttpServletResponse response){	 
		map = new ModelMap();
		String applyDay = request.getParameter("applyDay"); //적용일자
		String empCode = request.getParameter("empCode"); //세션에 저장되어 있던 사원코드
		response.setContentType("application/json; charset=UTF-8");
		
		try {
			ArrayList<DayAttdTO> dayAttdList = attdMgmtService.findDayAttdList(empCode, applyDay);
			map.put("dayAttdList", dayAttdList);
			map.put("errorMsg","success");
			map.put("errorCode", 0);

		}catch (Exception dae){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}

	@PostMapping("daily-attnd")
	public ModelMap registDayAttd(HttpServletRequest request, HttpServletResponse response){
		
		map = new ModelMap();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");

		try {			
			Gson gson = new Gson();
			DayAttdTO dayAttd = gson.fromJson(sendData, DayAttdTO.class);
			ResultTO resultTO = attdMgmtService.registDayAttd(dayAttd);
			map.put("errorMsg",resultTO.getErrorMsg());
			map.put("errorCode", resultTO.getErrorCode());
			
		}catch (Exception dae){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}

	@DeleteMapping("daily-attnd")
	public ModelMap removeDayAttdList(HttpServletRequest request, HttpServletResponse response){
		
		map = new ModelMap();
		String sendData = request.getParameter("sendData");
				response.setContentType("application/json; charset=UTF-8");

		try {
			Gson gson = new Gson();
			ArrayList<DayAttdTO> dayAttdList = gson.fromJson(sendData, new TypeToken<ArrayList<DayAttdTO>>(){}.getType());
			attdMgmtService.removeDayAttdList(dayAttdList);
			map.put("errorMsg","success");
			map.put("errorCode", 0);

		}catch (Exception dae){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}
	
	
	public ModelMap insertDayAttd(HttpServletRequest request, HttpServletResponse response){ 
		
		map = new ModelMap();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");

		try {		
			Gson gson = new Gson();
			DayAttdTO dayAttd = gson.fromJson(sendData, new TypeToken<DayAttdTO>(){}.getType());
			attdMgmtService.insertDayAttd(dayAttd);
			map.put("errorMsg","success");
			map.put("errorCode", 0);
			
		}catch (Exception dae){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}
}
