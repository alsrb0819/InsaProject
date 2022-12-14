package kr.co.seoulit.insa.newempsvc.newempinfomgmt.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import kr.co.seoulit.insa.newempsvc.newempinfomgmt.service.NewEmpInfoService;
import kr.co.seoulit.insa.newempsvc.newempinfomgmt.to.NewResumeTO;
import kr.co.seoulit.insa.newempsvc.newempinfomgmt.to.PersonalityInterviewTO;
import net.sf.json.JSONObject;

@RequestMapping("/newempinfomgmt/*")
@RestController
public class ResumeManagementContoller
{

	@Autowired
	private NewEmpInfoService newempInfoService;
	ModelMap map = null;

	@GetMapping("/resumemgmt")
	public ModelMap resumeList(HttpServletRequest request, HttpServletResponse response)
	{
		String sendData = request.getParameter("sendData");
		map = new ModelMap();
		try {
			JSONObject json = JSONObject.fromObject(sendData);
			int year = Integer.parseInt((String) json.get("year"));
			String half = json.getString("half");
			ArrayList<NewResumeTO> list = newempInfoService.findresumeList(year, half);
			map.put("list", list);

		} catch (Exception e) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
		}
		return map;
	}
	
	@PutMapping("/resumemgmt")
	public ModelMap resumeUpdate(HttpServletRequest request, HttpServletResponse response)
	{
		map = new ModelMap();
		String sendData = request.getParameter("sendData");
		try {
			Gson gson = new Gson();
			NewResumeTO nemp = gson.fromJson(sendData, NewResumeTO.class);
			newempInfoService.updateresumeNewemp(nemp);
		} catch (Exception e) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
		}
		return map;
	}
	
	@GetMapping("/newCode")
	public ModelMap resumeNewCode(@RequestParam("sendData") String sendData, HttpServletResponse response)
	{
		map = new ModelMap();
		try {
			JSONObject json = JSONObject.fromObject(sendData);
			int year = Integer.parseInt((String) json.get("year"));
			int half = Integer.parseInt((String) json.get("half"));
			System.out.println(year);
			System.out.println(half);
			String newcode = newempInfoService.produceNewcode(year, half);
			map.put("newcode",newcode);
		} catch (Exception e) {
			map.clear();
			map.put("errorCode", -1);
			map.put( "errorMsg", e.getMessage() );
		}
		return map;
	}
	
	@PutMapping("/newapplicationreg")
	public ModelMap newApplicationReg(HttpServletRequest request, HttpServletResponse response)
	{
		String sendData = request.getParameter("sendData");
		map = new ModelMap();
		try {
			System.out.println(sendData);
			Gson gson = new Gson();
			NewResumeTO resume = gson.fromJson(sendData, NewResumeTO.class);
			PersonalityInterviewTO pi = gson.fromJson(sendData, PersonalityInterviewTO.class);
			newempInfoService.insertResumeAndPI(resume, pi);
		} catch (Exception e) {
			map.clear();
			map.put("errorCode", -1);
			map.put( "errorMsg", e.getMessage() );
		}
		return map;
	}
}
