package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.Case;
import com.cloudforce.dto.CaseDTO;
import com.cloudforce.dto.CaseSearchDTO;
import com.cloudforce.dto.CasePageDTO;
import com.cloudforce.dto.CaseConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface CaseService extends GenericService<Case, Integer> {

	List<Case> findAll();

	ResultDTO addCase(CaseDTO caseDTO, RequestDTO requestDTO);

	ResultDTO updateCase(CaseDTO caseDTO, RequestDTO requestDTO);

    Page<Case> getAllCases(Pageable pageable);

    Page<Case> getAllCases(Specification<Case> spec, Pageable pageable);

	ResponseEntity<CasePageDTO> getCases(CaseSearchDTO caseSearchDTO);
	
	List<CaseDTO> convertCasesToCaseDTOs(List<Case> cases, CaseConvertCriteriaDTO convertCriteria);

	CaseDTO getCaseDTOById(Integer caseId);


}
