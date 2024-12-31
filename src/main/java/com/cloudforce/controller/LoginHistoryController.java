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

import com.cloudforce.domain.LoginHistory;
import com.cloudforce.dto.LoginHistoryDTO;
import com.cloudforce.dto.LoginHistorySearchDTO;
import com.cloudforce.dto.LoginHistoryPageDTO;
import com.cloudforce.service.LoginHistoryService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/loginHistory")
@RestController
public class LoginHistoryController {

	private final static Logger logger = LoggerFactory.getLogger(LoginHistoryController.class);

	@Autowired
	LoginHistoryService loginHistoryService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<LoginHistory> getAll() {

		List<LoginHistory> loginHistorys = loginHistoryService.findAll();
		
		return loginHistorys;	
	}

	@GetMapping(value = "/{loginHistoryId}")
	@ResponseBody
	public LoginHistoryDTO getLoginHistory(@PathVariable Integer loginHistoryId) {
		
		return (loginHistoryService.getLoginHistoryDTOById(loginHistoryId));
	}

 	@RequestMapping(value = "/addLoginHistory", method = RequestMethod.POST)
	public ResponseEntity<?> addLoginHistory(@RequestBody LoginHistoryDTO loginHistoryDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = loginHistoryService.addLoginHistory(loginHistoryDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/loginHistorys")
	public ResponseEntity<LoginHistoryPageDTO> getLoginHistorys(LoginHistorySearchDTO loginHistorySearchDTO) {
 
		return loginHistoryService.getLoginHistorys(loginHistorySearchDTO);
	}	

	@RequestMapping(value = "/updateLoginHistory", method = RequestMethod.POST)
	public ResponseEntity<?> updateLoginHistory(@RequestBody LoginHistoryDTO loginHistoryDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = loginHistoryService.updateLoginHistory(loginHistoryDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
