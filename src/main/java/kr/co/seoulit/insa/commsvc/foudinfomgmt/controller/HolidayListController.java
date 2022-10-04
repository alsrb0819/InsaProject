package kr.co.seoulit.insa.commsvc.foudinfomgmt.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.seoulit.insa.commsvc.foudinfomgmt.service.FoudInfoMgmtService;
import kr.co.seoulit.insa.commsvc.foudinfomgmt.to.HolidayTO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/foudinfomgmt/*")
@RestController
public class HolidayListController {
	
	@Autowired
	private FoudInfoMgmtService foudInfoMgmtService;	
	ModelMap map = null;

	@GetMapping("holiday")
	public ModelMap findHolidayList(HttpServletRequest request, HttpServletResponse response) {

		map = new ModelMap();
		response.setContentType("application/json; charset=UTF-8");
		try {
			ArrayList<HolidayTO> holidayList = foudInfoMgmtService.findHolidayList();
			HolidayTO holito = new HolidayTO();
			map.put("holidayList", holidayList);
			map.put("emptyHoilday", holito);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
			
		} catch (Exception dae) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}

	@GetMapping("holidayweek")
	public ModelMap findWeekDayCount(HttpServletRequest request, HttpServletResponse response) {
		map = new ModelMap();
		response.setContentType("application/json; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		try {
			String weekdayCount = foudInfoMgmtService.findWeekDayCount(startDate, endDate);
			map.put("weekdayCount", weekdayCount);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
			
		} catch (Exception dae) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}

	
	@PutMapping("holiday")
	public ModelMap batchHolidayProcess(HttpServletRequest request, HttpServletResponse response) {		
		map = new ModelMap();
		String sendData = request.getParameter("sendData");		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			ArrayList<HolidayTO> holidayList = mapper.readValue(sendData, new TypeReference<ArrayList<HolidayTO>>() {});
			
			foudInfoMgmtService.batchHolidayProcess(holidayList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
				
		} catch (Exception e) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
		}
		return map;
	}

}
