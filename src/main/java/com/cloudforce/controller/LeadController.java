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

import com.cloudforce.domain.Lead;
import com.cloudforce.dto.LeadDTO;
import com.cloudforce.dto.LeadSearchDTO;
import com.cloudforce.dto.LeadPageDTO;
import com.cloudforce.service.LeadService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/lead")
@RestController
public class LeadController {

	private final static Logger logger = LoggerFactory.getLogger(LeadController.class);

	@Autowired
	LeadService leadService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Lead> getAll() {

		List<Lead> leads = leadService.findAll();
		
		return leads;	
	}

	@GetMapping(value = "/{leadId}")
	@ResponseBody
	public LeadDTO getLead(@PathVariable Integer leadId) {
		
		return (leadService.getLeadDTOById(leadId));
	}

 	@RequestMapping(value = "/addLead", method = RequestMethod.POST)
	public ResponseEntity<?> addLead(@RequestBody LeadDTO leadDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = leadService.addLead(leadDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/leads")
	public ResponseEntity<LeadPageDTO> getLeads(LeadSearchDTO leadSearchDTO) {
 
		return leadService.getLeads(leadSearchDTO);
	}	

	@RequestMapping(value = "/updateLead", method = RequestMethod.POST)
	public ResponseEntity<?> updateLead(@RequestBody LeadDTO leadDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = leadService.updateLead(leadDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
