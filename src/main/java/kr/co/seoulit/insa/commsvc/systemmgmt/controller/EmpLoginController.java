package kr.co.seoulit.insa.commsvc.systemmgmt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kr.co.seoulit.insa.commsvc.systemmgmt.service.SystemMgmtService;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpTO;


@RequestMapping("/systemmgmt/*")
@RestController
public class EmpLoginController {
	
	@Autowired
	private SystemMgmtService systemMgmtService;
	
	ModelMap map = null;
	
	@GetMapping("/login")
	public ModelMap empLogin(HttpServletRequest request, HttpServletResponse response) {		
		map = new ModelMap();		
		try {
			String empName = request.getParameter("empName");
			String empCode = request.getParameter("empCode");
			
			EmpTO empto = systemMgmtService.findEmp(empName, empCode,request, response);
			
				if(empto!=null)
				{
					request.getSession().setAttribute("id", empName);
					request.getSession().setAttribute("dept", empto.getDeptName());
					request.getSession().setAttribute("position", empto.getPosition());
					request.getSession().setAttribute("code", empto.getEmpCode());
					request.getSession().setAttribute("authority", empto.getAuthority());
			
					map.put("me", "enter"); 
				}

		}catch (Exception e) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());		
		}
		return map; 
	}
	
}
