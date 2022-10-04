package kr.co.seoulit.insa.attdsvc.attdappvl.controller;

import java.util.ArrayList;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kr.co.seoulit.insa.attdsvc.attdappvl.service.AttdAppvlService;
import kr.co.seoulit.insa.attdsvc.attdappvl.to.AnnualLeaveMgtTO;

@RestController
@RequestMapping("/attdappvl/*")
public class AnnualLeaveMgtController {
	
   @Autowired
   private AttdAppvlService attdAppvlService;
   ModelMap map = null;
   
   @GetMapping("/annual-leaveMgt")
   public ModelMap findAnnualVacationMgtList(HttpServletRequest request, HttpServletResponse response){
	  map = new ModelMap();
      String applyYearMonth = request.getParameter("applyYearMonth");
      
      response.setContentType("application/json; charset=UTF-8");
      try {
         
         ArrayList<AnnualLeaveMgtTO> annualVacationMgtList = attdAppvlService.findAnnualVacationMgtList(applyYearMonth);
         
         map.put("annualVacationMgtList", annualVacationMgtList);
         map.put("errorMsg","success");
         map.put("errorCode", 0);
         
      } catch (Exception dae){
    	  map.clear();
    	  map.put("errorCode", -1);
    	  map.put("errorMsg", dae.getMessage());
      }
      return map;
   }
   
   
   @PutMapping("/annual-leaveMgt/1")
   public ModelMap modifyAnnualVacationMgtList(HttpServletRequest request, HttpServletResponse response){
	   map = new ModelMap();
      String sendData = request.getParameter("sendData");
      response.setContentType("application/json; charset=UTF-8");
      try {

         Gson gson = new Gson();
         ArrayList<AnnualLeaveMgtTO> annualVacationMgtList = gson.fromJson(sendData, new TypeToken<ArrayList<AnnualLeaveMgtTO>>(){}.getType());
         attdAppvlService.modifyAnnualVacationMgtList(annualVacationMgtList);
         map.put("errorMsg","success");
         map.put("errorCode", 0);
         
      }catch (Exception dae){
    	  map.clear();
    	  map.put("errorCode", -1);
    	  map.put("errorMsg", dae.getMessage());
      }
      return map;
   } 
   
   
   @PutMapping("/annual-leaveMgt/2")
   public ModelMap cancelAnnualVacationMgtList(HttpServletRequest request, HttpServletResponse response){
	   map = new ModelMap();
      String sendData = request.getParameter("sendData");
      response.setContentType("application/json; charset=UTF-8");
      try {
         Gson gson = new Gson();
         ArrayList<AnnualLeaveMgtTO> annualVacationMgtList = gson.fromJson(sendData, new TypeToken<ArrayList<AnnualLeaveMgtTO>>(){}.getType());
         attdAppvlService.cancelAnnualVacationMgtList(annualVacationMgtList);
         map.put("errorMsg","success");
         map.put("errorCode", 0);
         
      }catch (Exception dae){
    	  map.clear();
    	  map.put("errorCode", -1);
    	  map.put("errorMsg", dae.getMessage());
      }
      return map;
   } 
   
   
}