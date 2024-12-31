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

import com.cloudforce.domain.Case;
import com.cloudforce.dto.CaseDTO;
import com.cloudforce.dto.CaseSearchDTO;
import com.cloudforce.dto.CasePageDTO;
import com.cloudforce.service.CaseService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/case")
@RestController
public class CaseController {

	private final static Logger logger = LoggerFactory.getLogger(CaseController.class);

	@Autowired
	CaseService caseService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Case> getAll() {

		List<Case> cases = caseService.findAll();
		
		return cases;	
	}

	@GetMapping(value = "/{caseId}")
	@ResponseBody
	public CaseDTO getCase(@PathVariable Integer caseId) {
		
		return (caseService.getCaseDTOById(caseId));
	}

 	@RequestMapping(value = "/addCase", method = RequestMethod.POST)
	public ResponseEntity<?> addCase(@RequestBody CaseDTO caseDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = caseService.addCase(caseDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/cases")
	public ResponseEntity<CasePageDTO> getCases(CaseSearchDTO caseSearchDTO) {
 
		return caseService.getCases(caseSearchDTO);
	}	

	@RequestMapping(value = "/updateCase", method = RequestMethod.POST)
	public ResponseEntity<?> updateCase(@RequestBody CaseDTO caseDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = caseService.updateCase(caseDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
