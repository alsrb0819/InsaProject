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
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.RestAttdTO;

@RestController
@RequestMapping("/attdmgmt/*")
public class ExcusedAttendanceController {
	
	@Autowired
	private AttdMgmtService attdMgmtService;	
	ModelMap map = null;
	
	
	@PostMapping("/excused-attnd")
	public ModelMap registRestAttd(HttpServletRequest request, HttpServletResponse response) {		
		map = new ModelMap();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");
		try {
			Gson gson = new Gson();
			RestAttdTO restAttd = gson.fromJson(sendData, RestAttdTO.class);
			attdMgmtService.registRestAttd(restAttd);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
			
		} catch (Exception dae) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}

	
	@GetMapping("/excused-attnd")
	public ModelMap findRestAttdList(HttpServletRequest request, HttpServletResponse response) {
		 
		map = new ModelMap();
		String empCode = request.getParameter("empCode");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String code = request.getParameter("code");
		
		response.setContentType("application/json; charset=UTF-8");
		
		try {
			ArrayList<RestAttdTO> restAttdList = attdMgmtService.findRestAttdList(empCode, startDate, endDate, code); // 테이블 분리 해놔야 될 거 같은데 존나 병신같음 이거 
			map.put("restAttdList", restAttdList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
			
		}catch (Exception dae) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}

	@DeleteMapping("/excused-attnd")
	public ModelMap removeRestAttdList(HttpServletRequest request, HttpServletResponse response) {
		
		map = new ModelMap();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");
		try {
			Gson gson = new Gson();
			ArrayList<RestAttdTO> restAttdList = gson.fromJson(sendData, new TypeToken<ArrayList<RestAttdTO>>() {
			}.getType());
			attdMgmtService.removeRestAttdList(restAttdList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);

		} catch (Exception dae) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}

}
