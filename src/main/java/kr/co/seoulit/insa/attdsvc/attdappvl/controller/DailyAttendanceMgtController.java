package kr.co.seoulit.insa.attdsvc.attdappvl.controller;


import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kr.co.seoulit.insa.attdsvc.attdappvl.service.AttdAppvlService;
import kr.co.seoulit.insa.attdsvc.attdappvl.to.DayAttdMgtTO;

@RestController
@RequestMapping("/attdappvl/*")
public class DailyAttendanceMgtController {
	
	@Autowired
	private AttdAppvlService attdAppvlService;
	ModelMap map = null;
	
	@GetMapping("day-attnd")
	public ModelMap findDayAttdMgtList(HttpServletRequest request, HttpServletResponse response){
		
		map = new ModelMap();
		String applyDay = request.getParameter("applyDay");
		
		response.setContentType("application/json; charset=UTF-8"); // 서버가 보낼 타입을 json 으로 지정함 

		try {
			ArrayList<DayAttdMgtTO> dayAttdMgtList = attdAppvlService.findDayAttdMgtList(applyDay);
			map.put("dayAttdMgtList", dayAttdMgtList);
			map.put("errorMsg","success");
			map.put("errorCode", 0);
			
		} catch (Exception dae){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}

	@PutMapping("day-attnd")
	public ModelMap modifyDayAttdList(HttpServletRequest request, HttpServletResponse response){
		
		map = new ModelMap();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");

		try {	
			Gson gson = new Gson();
			ArrayList<DayAttdMgtTO> dayAttdMgtList = gson.fromJson(sendData, new TypeToken<ArrayList<DayAttdMgtTO>>(){}.getType());
			attdAppvlService.modifyDayAttdMgtList(dayAttdMgtList);
			map.put("errorMsg","success");
			map.put("errorCode", 0);

		} catch (Exception dae){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}	

}
