package com.cloudforce.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.cloudforce.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.cloudforce.domain.Dashboard;
import com.cloudforce.dto.DashboardDTO;
import com.cloudforce.dto.DashboardSearchDTO;
import com.cloudforce.dto.DashboardPageDTO;
import com.cloudforce.service.DashboardService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/dashboard")
@RestController
public class DashboardController {

	private final static Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	DashboardService dashboardService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Dashboard> getAll() {

		List<Dashboard> dashboards = dashboardService.findAll();
		
		return dashboards;	
	}

	@GetMapping(value = "/{dashboardId}")
	@ResponseBody
	public DashboardDTO getDashboard(@PathVariable Integer dashboardId) {
		
		return (dashboardService.getDashboardDTOById(dashboardId));
	}

 	@RequestMapping(value = "/addDashboard", method = RequestMethod.POST)
	public ResponseEntity<?> addDashboard(@RequestBody DashboardDTO dashboardDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = dashboardService.addDashboard(dashboardDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/dashboards")
	public ResponseEntity<DashboardPageDTO> getDashboards(DashboardSearchDTO dashboardSearchDTO) {
 
		return dashboardService.getDashboards(dashboardSearchDTO);
	}	

	@RequestMapping(value = "/updateDashboard", method = RequestMethod.POST)
	public ResponseEntity<?> updateDashboard(@RequestBody DashboardDTO dashboardDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = dashboardService.updateDashboard(dashboardDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
