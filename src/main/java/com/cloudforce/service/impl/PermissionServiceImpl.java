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
import com.cloudforce.dao.PermissionDAO;
import com.cloudforce.domain.Permission;
import com.cloudforce.dto.PermissionDTO;
import com.cloudforce.dto.PermissionSearchDTO;
import com.cloudforce.dto.PermissionPageDTO;
import com.cloudforce.dto.PermissionConvertCriteriaDTO;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import com.cloudforce.service.PermissionService;
import com.cloudforce.util.ControllerUtils;

@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Integer> implements PermissionService {

    private final static Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

	@Autowired
	PermissionDAO permissionDao;

	@Override
	public GenericDAO<Permission, Integer> getDAO() {
		return (GenericDAO<Permission, Integer>) permissionDao;
	}
	
	public List<Permission> findAll () {
		List<Permission> permissions = permissionDao.findAll();
		
		return permissions;	
		
	}

	public ResultDTO addPermission(PermissionDTO permissionDTO, RequestDTO requestDTO) {

		Permission permission = new Permission();

		permission.setPermissionId(permissionDTO.getPermissionId());

		permission.setName(permissionDTO.getName());

		permission.setDescription(permissionDTO.getDescription());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		permission = permissionDao.save(permission);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Permission> getAllPermissions(Pageable pageable) {
		return permissionDao.findAll(pageable);
	}

	public Page<Permission> getAllPermissions(Specification<Permission> spec, Pageable pageable) {
		return permissionDao.findAll(spec, pageable);
	}

	public ResponseEntity<PermissionPageDTO> getPermissions(PermissionSearchDTO permissionSearchDTO) {
	
			Integer permissionId = permissionSearchDTO.getPermissionId(); 
 			String name = permissionSearchDTO.getName(); 
 			String description = permissionSearchDTO.getDescription(); 
 			String sortBy = permissionSearchDTO.getSortBy();
			String sortOrder = permissionSearchDTO.getSortOrder();
			String searchQuery = permissionSearchDTO.getSearchQuery();
			Integer page = permissionSearchDTO.getPage();
			Integer size = permissionSearchDTO.getSize();

	        Specification<Permission> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, permissionId, "permissionId"); 
			
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

		Page<Permission> permissions = this.getAllPermissions(spec, pageable);
		
		//System.out.println(String.valueOf(permissions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(permissions.getTotalPages()));
		
		List<Permission> permissionsList = permissions.getContent();
		
		PermissionConvertCriteriaDTO convertCriteria = new PermissionConvertCriteriaDTO();
		List<PermissionDTO> permissionDTOs = this.convertPermissionsToPermissionDTOs(permissionsList,convertCriteria);
		
		PermissionPageDTO permissionPageDTO = new PermissionPageDTO();
		permissionPageDTO.setPermissions(permissionDTOs);
		permissionPageDTO.setTotalElements(permissions.getTotalElements());
		return ResponseEntity.ok(permissionPageDTO);
	}

	public List<PermissionDTO> convertPermissionsToPermissionDTOs(List<Permission> permissions, PermissionConvertCriteriaDTO convertCriteria) {
		
		List<PermissionDTO> permissionDTOs = new ArrayList<PermissionDTO>();
		
		for (Permission permission : permissions) {
			permissionDTOs.add(convertPermissionToPermissionDTO(permission,convertCriteria));
		}
		
		return permissionDTOs;

	}
	
	public PermissionDTO convertPermissionToPermissionDTO(Permission permission, PermissionConvertCriteriaDTO convertCriteria) {
		
		PermissionDTO permissionDTO = new PermissionDTO();

		permissionDTO.setPermissionId(permission.getPermissionId());

		permissionDTO.setName(permission.getName());

		permissionDTO.setDescription(permission.getDescription());
		
		return permissionDTO;
	}

	public ResultDTO updatePermission(PermissionDTO permissionDTO, RequestDTO requestDTO) {
		
		Permission permission = permissionDao.getById(permissionDTO.getPermissionId());
		
		permission.setPermissionId(ControllerUtils.setValue(permission.getPermissionId(), permissionDTO.getPermissionId()));
		
		permission.setName(ControllerUtils.setValue(permission.getName(), permissionDTO.getName()));
		
		permission.setDescription(ControllerUtils.setValue(permission.getDescription(), permissionDTO.getDescription()));

        permission = permissionDao.save(permission);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PermissionDTO getPermissionDTOById(Integer permissionId) {
	
		Permission permission = permissionDao.getById(permissionId);
		
		PermissionConvertCriteriaDTO convertCriteria = new PermissionConvertCriteriaDTO();
		return(this.convertPermissionToPermissionDTO(permission,convertCriteria));
	}
}
