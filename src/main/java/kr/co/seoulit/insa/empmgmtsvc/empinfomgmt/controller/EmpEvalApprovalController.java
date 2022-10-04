package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.controller;

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
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service.EmpInfoService;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpEvalTO;


@RequestMapping("/empinfomgmt/*")
@RestController
public class EmpEvalApprovalController {
	
	@Autowired
	private EmpInfoService empInfoService;
	
	ModelMap map = null;
	
	@GetMapping("/evaluation-approval")
	public ModelMap findEmpEvalAppoList(HttpServletRequest request, HttpServletResponse response) {
		
		map = new ModelMap();
		String dept = request.getParameter("deptName");
		String year = request.getParameter("year");
		
		try {	
			ArrayList<EmpEvalTO> empEvalList = empInfoService.findEmpEval(dept,year);
			map.put("empEvalList", empEvalList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception dae) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}
	
	
	@PutMapping("evaluation-approval")
	public ModelMap modifyEmpEvalList(HttpServletRequest request, HttpServletResponse response) {
		
		map = new ModelMap();
		String sendData = request.getParameter("sendData");
		
		try {
			Gson gson = new Gson();
			ArrayList<EmpEvalTO> empevalList = gson.fromJson(sendData, new TypeToken<ArrayList<EmpEvalTO>>(){}.getType());
			empInfoService.modifyEmpEvalList(empevalList);
			map.put("errorMsg","success");
			map.put("errorCode", 0);
			
		} catch (Exception dae) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());

		}
		return map;
	}
}
