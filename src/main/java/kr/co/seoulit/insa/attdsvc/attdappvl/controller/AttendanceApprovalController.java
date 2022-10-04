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
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.RestAttdTO;

@RestController
@RequestMapping("/attdappvl/*")
public class AttendanceApprovalController {
	
	@Autowired
	private AttdAppvlService attdAppvlService;
	ModelMap map = null;
	
	@GetMapping("attnd-approval")
	public ModelMap findRestAttdListByDept(HttpServletRequest request, HttpServletResponse response){
		
		map = new ModelMap();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String deptName = request.getParameter("deptName");
		response.setContentType("application/json; charset=UTF-8");

		try {
			ArrayList<RestAttdTO> restAttdList = attdAppvlService.findRestAttdListByDept(deptName, startDate, endDate);
			map.put("errorMsg","success");
			map.put("errorCode", 0);
			map.put("restAttdList", restAttdList);
			
		} catch (Exception dae){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}
	

	@PutMapping("attnd-approval")
	public ModelMap modifyRestAttdList(HttpServletRequest request, HttpServletResponse response){		
		map = new ModelMap();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");

		try {
			Gson gson = new Gson();
			ArrayList<RestAttdTO> restAttdList = gson.fromJson(sendData, new TypeToken<ArrayList<RestAttdTO>>(){}.getType());
			attdAppvlService.modifyRestAttdList(restAttdList);
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
