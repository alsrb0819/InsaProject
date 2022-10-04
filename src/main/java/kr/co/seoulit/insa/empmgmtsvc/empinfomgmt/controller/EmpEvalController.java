package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.controller;

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
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service.EmpInfoService;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpEvalTO;


@RequestMapping("/empinfomgmt/*")
@RestController
public class EmpEvalController {
	
	@Autowired
	private EmpInfoService empInfoService;
	
	ModelMap map = null;
	
	@PostMapping("evaluation")
	public ModelMap registEmpEval(HttpServletRequest request, HttpServletResponse response){		
		map = new ModelMap();
		String sendData = request.getParameter("sendData");
		
		try{			
			Gson gson = new Gson();
			EmpEvalTO emp = gson.fromJson(sendData, EmpEvalTO.class);
			empInfoService.registEmpEval(emp);
			map.put("errorMsg","success");
			map.put("errorCode", 0);
			
		} catch (Exception dae){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}
	
	
	@GetMapping("/evaluation")
	public ModelMap findEmpEval(HttpServletRequest request, HttpServletResponse response){		
		map = new ModelMap();		
		try{
			
			ArrayList<EmpEvalTO> empevalList = empInfoService.findEmpEval();			
			map.put("empevalList", empevalList);
			map.put("errorMsg","success");
			map.put("errorCode", 0);

		} catch (Exception dae){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", dae.getMessage());
		}
		return map;
	}
	
	@DeleteMapping("evaluation")
	public ModelMap removeEmpEvalList(HttpServletRequest request, HttpServletResponse response){		
		map = new ModelMap();		
		String emp_code = request.getParameter("emp_code");
		String apply_day = request.getParameter("apply_day");
		
		try{			
			empInfoService.removeEmpEvalList(emp_code, apply_day);
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
