package kr.co.seoulit.insa.newempsvc.documentmgmt.controller;

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

import kr.co.seoulit.insa.newempsvc.documentmgmt.service.NDocumentMgmtService;
import kr.co.seoulit.insa.newempsvc.documentmgmt.to.RecruitmentTO;

import net.sf.json.JSONObject;

@RequestMapping("/documentmgmt/*")
@RestController
public class RecruitmentApprovalController
{

	@Autowired
	private NDocumentMgmtService documentMgmtService;

	ModelMap map = null;

	@GetMapping("/newemprecruit")
	public ModelMap newempRecruitInfo(HttpServletRequest request, HttpServletResponse response)
	{
		String sendData = request.getParameter("sendData");
		map = new ModelMap();
		try
		{
			JSONObject json = JSONObject.fromObject(sendData);
			int year = Integer.parseInt((String) json.get("year"));
			String half = json.getString("half");

			ArrayList<RecruitmentTO> list = documentMgmtService.FindNewemprecruit(year, half);
			for ( RecruitmentTO to : list )
			{
				if ( to.getApprovalStatus().equals("W") )
					to.setApprovalStatus("대기");
				else if ( to.getApprovalStatus().equals("Y") )
					to.setApprovalStatus("승인");
				else
					to.setApprovalStatus("승인취소");
			}
			map.put("Infolist", list);
		} catch (Exception e) {
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
		}
		return map;
	}
	
	@PutMapping("/newemprecruit")
	public ModelMap newempRegist(HttpServletRequest request, HttpServletResponse response)
	{
		String sendData = request.getParameter("sendData");
		map = new ModelMap();
		try
		{
			System.out.println(sendData);
			Gson gson = new Gson();
			ArrayList<RecruitmentTO> dataList = gson.fromJson(sendData ,new TypeToken<ArrayList<RecruitmentTO>>(){}.getType());
			for(RecruitmentTO to : dataList)
			{
				if ( to.getApprovalStatus().equals("대기") )
					to.setApprovalStatus("W");
				else if ( to.getApprovalStatus().equals("승인") )
					to.setApprovalStatus("Y");
				else
					to.setApprovalStatus("N");
			}
			documentMgmtService.RegisterEmp(dataList);
		} catch (Exception e) {
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
		}
		return map;
	}
}
