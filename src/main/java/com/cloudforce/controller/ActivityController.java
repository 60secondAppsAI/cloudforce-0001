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

import com.cloudforce.domain.Activity;
import com.cloudforce.dto.ActivityDTO;
import com.cloudforce.dto.ActivitySearchDTO;
import com.cloudforce.dto.ActivityPageDTO;
import com.cloudforce.service.ActivityService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/activity")
@RestController
public class ActivityController {

	private final static Logger logger = LoggerFactory.getLogger(ActivityController.class);

	@Autowired
	ActivityService activityService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Activity> getAll() {

		List<Activity> activitys = activityService.findAll();
		
		return activitys;	
	}

	@GetMapping(value = "/{activityId}")
	@ResponseBody
	public ActivityDTO getActivity(@PathVariable Integer activityId) {
		
		return (activityService.getActivityDTOById(activityId));
	}

 	@RequestMapping(value = "/addActivity", method = RequestMethod.POST)
	public ResponseEntity<?> addActivity(@RequestBody ActivityDTO activityDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = activityService.addActivity(activityDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/activitys")
	public ResponseEntity<ActivityPageDTO> getActivitys(ActivitySearchDTO activitySearchDTO) {
 
		return activityService.getActivitys(activitySearchDTO);
	}	

	@RequestMapping(value = "/updateActivity", method = RequestMethod.POST)
	public ResponseEntity<?> updateActivity(@RequestBody ActivityDTO activityDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = activityService.updateActivity(activityDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
