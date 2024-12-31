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

import com.cloudforce.domain.Contact;
import com.cloudforce.dto.ContactDTO;
import com.cloudforce.dto.ContactSearchDTO;
import com.cloudforce.dto.ContactPageDTO;
import com.cloudforce.service.ContactService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/contact")
@RestController
public class ContactController {

	private final static Logger logger = LoggerFactory.getLogger(ContactController.class);

	@Autowired
	ContactService contactService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Contact> getAll() {

		List<Contact> contacts = contactService.findAll();
		
		return contacts;	
	}

	@GetMapping(value = "/{contactId}")
	@ResponseBody
	public ContactDTO getContact(@PathVariable Integer contactId) {
		
		return (contactService.getContactDTOById(contactId));
	}

 	@RequestMapping(value = "/addContact", method = RequestMethod.POST)
	public ResponseEntity<?> addContact(@RequestBody ContactDTO contactDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = contactService.addContact(contactDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/contacts")
	public ResponseEntity<ContactPageDTO> getContacts(ContactSearchDTO contactSearchDTO) {
 
		return contactService.getContacts(contactSearchDTO);
	}	

	@RequestMapping(value = "/updateContact", method = RequestMethod.POST)
	public ResponseEntity<?> updateContact(@RequestBody ContactDTO contactDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = contactService.updateContact(contactDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
