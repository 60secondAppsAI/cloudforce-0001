package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.Group;
import com.cloudforce.dto.GroupDTO;
import com.cloudforce.dto.GroupSearchDTO;
import com.cloudforce.dto.GroupPageDTO;
import com.cloudforce.dto.GroupConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface GroupService extends GenericService<Group, Integer> {

	List<Group> findAll();

	ResultDTO addGroup(GroupDTO groupDTO, RequestDTO requestDTO);

	ResultDTO updateGroup(GroupDTO groupDTO, RequestDTO requestDTO);

    Page<Group> getAllGroups(Pageable pageable);

    Page<Group> getAllGroups(Specification<Group> spec, Pageable pageable);

	ResponseEntity<GroupPageDTO> getGroups(GroupSearchDTO groupSearchDTO);
	
	List<GroupDTO> convertGroupsToGroupDTOs(List<Group> groups, GroupConvertCriteriaDTO convertCriteria);

	GroupDTO getGroupDTOById(Integer groupId);


}
