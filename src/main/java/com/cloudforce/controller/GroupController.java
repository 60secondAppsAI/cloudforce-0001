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

import com.cloudforce.domain.Group;
import com.cloudforce.dto.GroupDTO;
import com.cloudforce.dto.GroupSearchDTO;
import com.cloudforce.dto.GroupPageDTO;
import com.cloudforce.service.GroupService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/group")
@RestController
public class GroupController {

	private final static Logger logger = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	GroupService groupService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Group> getAll() {

		List<Group> groups = groupService.findAll();
		
		return groups;	
	}

	@GetMapping(value = "/{groupId}")
	@ResponseBody
	public GroupDTO getGroup(@PathVariable Integer groupId) {
		
		return (groupService.getGroupDTOById(groupId));
	}

 	@RequestMapping(value = "/addGroup", method = RequestMethod.POST)
	public ResponseEntity<?> addGroup(@RequestBody GroupDTO groupDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = groupService.addGroup(groupDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/groups")
	public ResponseEntity<GroupPageDTO> getGroups(GroupSearchDTO groupSearchDTO) {
 
		return groupService.getGroups(groupSearchDTO);
	}	

	@RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
	public ResponseEntity<?> updateGroup(@RequestBody GroupDTO groupDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = groupService.updateGroup(groupDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
