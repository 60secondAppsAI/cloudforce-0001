package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.Role;
import com.cloudforce.dto.RoleDTO;
import com.cloudforce.dto.RoleSearchDTO;
import com.cloudforce.dto.RolePageDTO;
import com.cloudforce.dto.RoleConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface RoleService extends GenericService<Role, Integer> {

	List<Role> findAll();

	ResultDTO addRole(RoleDTO roleDTO, RequestDTO requestDTO);

	ResultDTO updateRole(RoleDTO roleDTO, RequestDTO requestDTO);

    Page<Role> getAllRoles(Pageable pageable);

    Page<Role> getAllRoles(Specification<Role> spec, Pageable pageable);

	ResponseEntity<RolePageDTO> getRoles(RoleSearchDTO roleSearchDTO);
	
	List<RoleDTO> convertRolesToRoleDTOs(List<Role> roles, RoleConvertCriteriaDTO convertCriteria);

	RoleDTO getRoleDTOById(Integer roleId);


}
