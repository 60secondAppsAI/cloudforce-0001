package com.cloudforce.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.cloudforce.dao.GenericDAO;
import com.cloudforce.service.GenericService;
import com.cloudforce.service.impl.GenericServiceImpl;
import com.cloudforce.dao.GroupDAO;
import com.cloudforce.domain.Group;
import com.cloudforce.dto.GroupDTO;
import com.cloudforce.dto.GroupSearchDTO;
import com.cloudforce.dto.GroupPageDTO;
import com.cloudforce.dto.GroupConvertCriteriaDTO;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import com.cloudforce.service.GroupService;
import com.cloudforce.util.ControllerUtils;

@Service
public class GroupServiceImpl extends GenericServiceImpl<Group, Integer> implements GroupService {

    private final static Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

	@Autowired
	GroupDAO groupDao;

	@Override
	public GenericDAO<Group, Integer> getDAO() {
		return (GenericDAO<Group, Integer>) groupDao;
	}
	
	public List<Group> findAll () {
		List<Group> groups = groupDao.findAll();
		
		return groups;	
		
	}

	public ResultDTO addGroup(GroupDTO groupDTO, RequestDTO requestDTO) {

		Group group = new Group();

		group.setGroupId(groupDTO.getGroupId());

		group.setName(groupDTO.getName());

		group.setDescription(groupDTO.getDescription());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		group = groupDao.save(group);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Group> getAllGroups(Pageable pageable) {
		return groupDao.findAll(pageable);
	}

	public Page<Group> getAllGroups(Specification<Group> spec, Pageable pageable) {
		return groupDao.findAll(spec, pageable);
	}

	public ResponseEntity<GroupPageDTO> getGroups(GroupSearchDTO groupSearchDTO) {
	
			Integer groupId = groupSearchDTO.getGroupId(); 
 			String name = groupSearchDTO.getName(); 
 			String description = groupSearchDTO.getDescription(); 
 			String sortBy = groupSearchDTO.getSortBy();
			String sortOrder = groupSearchDTO.getSortOrder();
			String searchQuery = groupSearchDTO.getSearchQuery();
			Integer page = groupSearchDTO.getPage();
			Integer size = groupSearchDTO.getSize();

	        Specification<Group> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, groupId, "groupId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Group> groups = this.getAllGroups(spec, pageable);
		
		//System.out.println(String.valueOf(groups.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(groups.getTotalPages()));
		
		List<Group> groupsList = groups.getContent();
		
		GroupConvertCriteriaDTO convertCriteria = new GroupConvertCriteriaDTO();
		List<GroupDTO> groupDTOs = this.convertGroupsToGroupDTOs(groupsList,convertCriteria);
		
		GroupPageDTO groupPageDTO = new GroupPageDTO();
		groupPageDTO.setGroups(groupDTOs);
		groupPageDTO.setTotalElements(groups.getTotalElements());
		return ResponseEntity.ok(groupPageDTO);
	}

	public List<GroupDTO> convertGroupsToGroupDTOs(List<Group> groups, GroupConvertCriteriaDTO convertCriteria) {
		
		List<GroupDTO> groupDTOs = new ArrayList<GroupDTO>();
		
		for (Group group : groups) {
			groupDTOs.add(convertGroupToGroupDTO(group,convertCriteria));
		}
		
		return groupDTOs;

	}
	
	public GroupDTO convertGroupToGroupDTO(Group group, GroupConvertCriteriaDTO convertCriteria) {
		
		GroupDTO groupDTO = new GroupDTO();

		groupDTO.setGroupId(group.getGroupId());

		groupDTO.setName(group.getName());

		groupDTO.setDescription(group.getDescription());
		
		return groupDTO;
	}

	public ResultDTO updateGroup(GroupDTO groupDTO, RequestDTO requestDTO) {
		
		Group group = groupDao.getById(groupDTO.getGroupId());
		
		group.setGroupId(ControllerUtils.setValue(group.getGroupId(), groupDTO.getGroupId()));
		
		group.setName(ControllerUtils.setValue(group.getName(), groupDTO.getName()));
		
		group.setDescription(ControllerUtils.setValue(group.getDescription(), groupDTO.getDescription()));

        group = groupDao.save(group);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public GroupDTO getGroupDTOById(Integer groupId) {
	
		Group group = groupDao.getById(groupId);
		
		GroupConvertCriteriaDTO convertCriteria = new GroupConvertCriteriaDTO();
		return(this.convertGroupToGroupDTO(group,convertCriteria));
	}
}
