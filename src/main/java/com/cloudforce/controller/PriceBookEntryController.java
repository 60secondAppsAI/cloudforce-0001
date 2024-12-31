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

import com.cloudforce.domain.PriceBookEntry;
import com.cloudforce.dto.PriceBookEntryDTO;
import com.cloudforce.dto.PriceBookEntrySearchDTO;
import com.cloudforce.dto.PriceBookEntryPageDTO;
import com.cloudforce.service.PriceBookEntryService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/priceBookEntry")
@RestController
public class PriceBookEntryController {

	private final static Logger logger = LoggerFactory.getLogger(PriceBookEntryController.class);

	@Autowired
	PriceBookEntryService priceBookEntryService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PriceBookEntry> getAll() {

		List<PriceBookEntry> priceBookEntrys = priceBookEntryService.findAll();
		
		return priceBookEntrys;	
	}

	@GetMapping(value = "/{priceBookEntryId}")
	@ResponseBody
	public PriceBookEntryDTO getPriceBookEntry(@PathVariable Integer priceBookEntryId) {
		
		return (priceBookEntryService.getPriceBookEntryDTOById(priceBookEntryId));
	}

 	@RequestMapping(value = "/addPriceBookEntry", method = RequestMethod.POST)
	public ResponseEntity<?> addPriceBookEntry(@RequestBody PriceBookEntryDTO priceBookEntryDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = priceBookEntryService.addPriceBookEntry(priceBookEntryDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/priceBookEntrys")
	public ResponseEntity<PriceBookEntryPageDTO> getPriceBookEntrys(PriceBookEntrySearchDTO priceBookEntrySearchDTO) {
 
		return priceBookEntryService.getPriceBookEntrys(priceBookEntrySearchDTO);
	}	

	@RequestMapping(value = "/updatePriceBookEntry", method = RequestMethod.POST)
	public ResponseEntity<?> updatePriceBookEntry(@RequestBody PriceBookEntryDTO priceBookEntryDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = priceBookEntryService.updatePriceBookEntry(priceBookEntryDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
