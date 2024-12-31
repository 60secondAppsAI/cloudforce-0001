package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.Permission;
import com.cloudforce.dto.PermissionDTO;
import com.cloudforce.dto.PermissionSearchDTO;
import com.cloudforce.dto.PermissionPageDTO;
import com.cloudforce.dto.PermissionConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PermissionService extends GenericService<Permission, Integer> {

	List<Permission> findAll();

	ResultDTO addPermission(PermissionDTO permissionDTO, RequestDTO requestDTO);

	ResultDTO updatePermission(PermissionDTO permissionDTO, RequestDTO requestDTO);

    Page<Permission> getAllPermissions(Pageable pageable);

    Page<Permission> getAllPermissions(Specification<Permission> spec, Pageable pageable);

	ResponseEntity<PermissionPageDTO> getPermissions(PermissionSearchDTO permissionSearchDTO);
	
	List<PermissionDTO> convertPermissionsToPermissionDTOs(List<Permission> permissions, PermissionConvertCriteriaDTO convertCriteria);

	PermissionDTO getPermissionDTOById(Integer permissionId);


}
