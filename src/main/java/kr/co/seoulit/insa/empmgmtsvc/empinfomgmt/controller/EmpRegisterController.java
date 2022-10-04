package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service.EmpInfoService;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpTO;


@RequestMapping("/empinfomgmt/*")
@RestController
public class EmpRegisterController {
	
	@Autowired
	private EmpInfoService empInfoService;
	ModelMap map = null;

	@PostMapping("/employee")
	public ModelMap registEmployee(HttpServletRequest request, HttpServletResponse response) {		
		map = new ModelMap();
		String sendData = request.getParameter("sendData");
		try {
			Gson gson = new Gson();
			EmpTO emp = gson.fromJson(sendData, new TypeToken<EmpTO>() {}.getType());			
			empInfoService.registEmployee(emp);			
			map.put("errorMsg","success");
			map.put("errorCode", 0);

		} catch (Exception e) {
			map.put("errorMsg", e.getMessage());
			map.put("errorCode", -1);

		}
		return map;
	}

	
	@GetMapping("/employee")
	public ModelMap findLastEmpCode(HttpServletRequest request, HttpServletResponse response) {
		map = new ModelMap();		
		try {
			String empCode = empInfoService.findLastEmpCode();
			map.put("lastEmpCode", empCode);
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
