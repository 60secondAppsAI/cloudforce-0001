package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.Lead;
import com.cloudforce.dto.LeadDTO;
import com.cloudforce.dto.LeadSearchDTO;
import com.cloudforce.dto.LeadPageDTO;
import com.cloudforce.dto.LeadConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface LeadService extends GenericService<Lead, Integer> {

	List<Lead> findAll();

	ResultDTO addLead(LeadDTO leadDTO, RequestDTO requestDTO);

	ResultDTO updateLead(LeadDTO leadDTO, RequestDTO requestDTO);

    Page<Lead> getAllLeads(Pageable pageable);

    Page<Lead> getAllLeads(Specification<Lead> spec, Pageable pageable);

	ResponseEntity<LeadPageDTO> getLeads(LeadSearchDTO leadSearchDTO);
	
	List<LeadDTO> convertLeadsToLeadDTOs(List<Lead> leads, LeadConvertCriteriaDTO convertCriteria);

	LeadDTO getLeadDTOById(Integer leadId);


}
