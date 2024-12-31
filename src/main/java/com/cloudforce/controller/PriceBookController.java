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

import com.cloudforce.domain.PriceBook;
import com.cloudforce.dto.PriceBookDTO;
import com.cloudforce.dto.PriceBookSearchDTO;
import com.cloudforce.dto.PriceBookPageDTO;
import com.cloudforce.service.PriceBookService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/priceBook")
@RestController
public class PriceBookController {

	private final static Logger logger = LoggerFactory.getLogger(PriceBookController.class);

	@Autowired
	PriceBookService priceBookService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PriceBook> getAll() {

		List<PriceBook> priceBooks = priceBookService.findAll();
		
		return priceBooks;	
	}

	@GetMapping(value = "/{priceBookId}")
	@ResponseBody
	public PriceBookDTO getPriceBook(@PathVariable Integer priceBookId) {
		
		return (priceBookService.getPriceBookDTOById(priceBookId));
	}

 	@RequestMapping(value = "/addPriceBook", method = RequestMethod.POST)
	public ResponseEntity<?> addPriceBook(@RequestBody PriceBookDTO priceBookDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = priceBookService.addPriceBook(priceBookDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/priceBooks")
	public ResponseEntity<PriceBookPageDTO> getPriceBooks(PriceBookSearchDTO priceBookSearchDTO) {
 
		return priceBookService.getPriceBooks(priceBookSearchDTO);
	}	

	@RequestMapping(value = "/updatePriceBook", method = RequestMethod.POST)
	public ResponseEntity<?> updatePriceBook(@RequestBody PriceBookDTO priceBookDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = priceBookService.updatePriceBook(priceBookDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
