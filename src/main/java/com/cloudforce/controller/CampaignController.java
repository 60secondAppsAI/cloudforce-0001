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

import com.cloudforce.domain.Campaign;
import com.cloudforce.dto.CampaignDTO;
import com.cloudforce.dto.CampaignSearchDTO;
import com.cloudforce.dto.CampaignPageDTO;
import com.cloudforce.service.CampaignService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/campaign")
@RestController
public class CampaignController {

	private final static Logger logger = LoggerFactory.getLogger(CampaignController.class);

	@Autowired
	CampaignService campaignService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Campaign> getAll() {

		List<Campaign> campaigns = campaignService.findAll();
		
		return campaigns;	
	}

	@GetMapping(value = "/{campaignId}")
	@ResponseBody
	public CampaignDTO getCampaign(@PathVariable Integer campaignId) {
		
		return (campaignService.getCampaignDTOById(campaignId));
	}

 	@RequestMapping(value = "/addCampaign", method = RequestMethod.POST)
	public ResponseEntity<?> addCampaign(@RequestBody CampaignDTO campaignDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = campaignService.addCampaign(campaignDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/campaigns")
	public ResponseEntity<CampaignPageDTO> getCampaigns(CampaignSearchDTO campaignSearchDTO) {
 
		return campaignService.getCampaigns(campaignSearchDTO);
	}	

	@RequestMapping(value = "/updateCampaign", method = RequestMethod.POST)
	public ResponseEntity<?> updateCampaign(@RequestBody CampaignDTO campaignDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = campaignService.updateCampaign(campaignDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
