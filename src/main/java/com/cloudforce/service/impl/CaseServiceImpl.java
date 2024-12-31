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
import com.cloudforce.dao.CaseDAO;
import com.cloudforce.domain.Case;
import com.cloudforce.dto.CaseDTO;
import com.cloudforce.dto.CaseSearchDTO;
import com.cloudforce.dto.CasePageDTO;
import com.cloudforce.dto.CaseConvertCriteriaDTO;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import com.cloudforce.service.CaseService;
import com.cloudforce.util.ControllerUtils;

@Service
public class CaseServiceImpl extends GenericServiceImpl<Case, Integer> implements CaseService {

    private final static Logger logger = LoggerFactory.getLogger(CaseServiceImpl.class);

	@Autowired
	CaseDAO caseDao;

	@Override
	public GenericDAO<Case, Integer> getDAO() {
		return (GenericDAO<Case, Integer>) caseDao;
	}
	
	public List<Case> findAll () {
		List<Case> cases = caseDao.findAll();
		
		return cases;	
		
	}

	public ResultDTO addCase(CaseDTO caseDTO, RequestDTO requestDTO) {

		Case case = new Case();

		case.setCaseId(caseDTO.getCaseId());

		case.setDescription(caseDTO.getDescription());

		case.setStatus(caseDTO.getStatus());

		case.setPriority(caseDTO.getPriority());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		case = caseDao.save(case);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Case> getAllCases(Pageable pageable) {
		return caseDao.findAll(pageable);
	}

	public Page<Case> getAllCases(Specification<Case> spec, Pageable pageable) {
		return caseDao.findAll(spec, pageable);
	}

	public ResponseEntity<CasePageDTO> getCases(CaseSearchDTO caseSearchDTO) {
	
			Integer caseId = caseSearchDTO.getCaseId(); 
 			String description = caseSearchDTO.getDescription(); 
 			String status = caseSearchDTO.getStatus(); 
 			String priority = caseSearchDTO.getPriority(); 
 			String sortBy = caseSearchDTO.getSortBy();
			String sortOrder = caseSearchDTO.getSortOrder();
			String searchQuery = caseSearchDTO.getSearchQuery();
			Integer page = caseSearchDTO.getPage();
			Integer size = caseSearchDTO.getSize();

	        Specification<Case> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, caseId, "caseId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			
			spec = ControllerUtils.andIfNecessary(spec, priority, "priority"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("priority")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Case> cases = this.getAllCases(spec, pageable);
		
		//System.out.println(String.valueOf(cases.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(cases.getTotalPages()));
		
		List<Case> casesList = cases.getContent();
		
		CaseConvertCriteriaDTO convertCriteria = new CaseConvertCriteriaDTO();
		List<CaseDTO> caseDTOs = this.convertCasesToCaseDTOs(casesList,convertCriteria);
		
		CasePageDTO casePageDTO = new CasePageDTO();
		casePageDTO.setCases(caseDTOs);
		casePageDTO.setTotalElements(cases.getTotalElements());
		return ResponseEntity.ok(casePageDTO);
	}

	public List<CaseDTO> convertCasesToCaseDTOs(List<Case> cases, CaseConvertCriteriaDTO convertCriteria) {
		
		List<CaseDTO> caseDTOs = new ArrayList<CaseDTO>();
		
		for (Case case : cases) {
			caseDTOs.add(convertCaseToCaseDTO(case,convertCriteria));
		}
		
		return caseDTOs;

	}
	
	public CaseDTO convertCaseToCaseDTO(Case case, CaseConvertCriteriaDTO convertCriteria) {
		
		CaseDTO caseDTO = new CaseDTO();

		caseDTO.setCaseId(case.getCaseId());

		caseDTO.setDescription(case.getDescription());

		caseDTO.setStatus(case.getStatus());

		caseDTO.setPriority(case.getPriority());
		
		return caseDTO;
	}

	public ResultDTO updateCase(CaseDTO caseDTO, RequestDTO requestDTO) {
		
		Case case = caseDao.getById(caseDTO.getCaseId());
		
		case.setCaseId(ControllerUtils.setValue(case.getCaseId(), caseDTO.getCaseId()));
		
		case.setDescription(ControllerUtils.setValue(case.getDescription(), caseDTO.getDescription()));
		
		case.setStatus(ControllerUtils.setValue(case.getStatus(), caseDTO.getStatus()));
		
		case.setPriority(ControllerUtils.setValue(case.getPriority(), caseDTO.getPriority()));

        case = caseDao.save(case);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public CaseDTO getCaseDTOById(Integer caseId) {
	
		Case case = caseDao.getById(caseId);
		
		CaseConvertCriteriaDTO convertCriteria = new CaseConvertCriteriaDTO();
		return(this.convertCaseToCaseDTO(case,convertCriteria));
	}
}
