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

import com.cloudforce.domain.Opportunity;
import com.cloudforce.dto.OpportunityDTO;
import com.cloudforce.dto.OpportunitySearchDTO;
import com.cloudforce.dto.OpportunityPageDTO;
import com.cloudforce.service.OpportunityService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/opportunity")
@RestController
public class OpportunityController {

	private final static Logger logger = LoggerFactory.getLogger(OpportunityController.class);

	@Autowired
	OpportunityService opportunityService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Opportunity> getAll() {

		List<Opportunity> opportunitys = opportunityService.findAll();
		
		return opportunitys;	
	}

	@GetMapping(value = "/{opportunityId}")
	@ResponseBody
	public OpportunityDTO getOpportunity(@PathVariable Integer opportunityId) {
		
		return (opportunityService.getOpportunityDTOById(opportunityId));
	}

 	@RequestMapping(value = "/addOpportunity", method = RequestMethod.POST)
	public ResponseEntity<?> addOpportunity(@RequestBody OpportunityDTO opportunityDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = opportunityService.addOpportunity(opportunityDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/opportunitys")
	public ResponseEntity<OpportunityPageDTO> getOpportunitys(OpportunitySearchDTO opportunitySearchDTO) {
 
		return opportunityService.getOpportunitys(opportunitySearchDTO);
	}	

	@RequestMapping(value = "/updateOpportunity", method = RequestMethod.POST)
	public ResponseEntity<?> updateOpportunity(@RequestBody OpportunityDTO opportunityDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = opportunityService.updateOpportunity(opportunityDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
