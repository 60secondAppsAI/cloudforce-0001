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

import com.cloudforce.domain.EmailTemplate;
import com.cloudforce.dto.EmailTemplateDTO;
import com.cloudforce.dto.EmailTemplateSearchDTO;
import com.cloudforce.dto.EmailTemplatePageDTO;
import com.cloudforce.service.EmailTemplateService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/emailTemplate")
@RestController
public class EmailTemplateController {

	private final static Logger logger = LoggerFactory.getLogger(EmailTemplateController.class);

	@Autowired
	EmailTemplateService emailTemplateService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<EmailTemplate> getAll() {

		List<EmailTemplate> emailTemplates = emailTemplateService.findAll();
		
		return emailTemplates;	
	}

	@GetMapping(value = "/{emailTemplateId}")
	@ResponseBody
	public EmailTemplateDTO getEmailTemplate(@PathVariable Integer emailTemplateId) {
		
		return (emailTemplateService.getEmailTemplateDTOById(emailTemplateId));
	}

 	@RequestMapping(value = "/addEmailTemplate", method = RequestMethod.POST)
	public ResponseEntity<?> addEmailTemplate(@RequestBody EmailTemplateDTO emailTemplateDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = emailTemplateService.addEmailTemplate(emailTemplateDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/emailTemplates")
	public ResponseEntity<EmailTemplatePageDTO> getEmailTemplates(EmailTemplateSearchDTO emailTemplateSearchDTO) {
 
		return emailTemplateService.getEmailTemplates(emailTemplateSearchDTO);
	}	

	@RequestMapping(value = "/updateEmailTemplate", method = RequestMethod.POST)
	public ResponseEntity<?> updateEmailTemplate(@RequestBody EmailTemplateDTO emailTemplateDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = emailTemplateService.updateEmailTemplate(emailTemplateDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
