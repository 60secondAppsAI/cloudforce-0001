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
import com.cloudforce.dao.OpportunityDAO;
import com.cloudforce.domain.Opportunity;
import com.cloudforce.dto.OpportunityDTO;
import com.cloudforce.dto.OpportunitySearchDTO;
import com.cloudforce.dto.OpportunityPageDTO;
import com.cloudforce.dto.OpportunityConvertCriteriaDTO;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import com.cloudforce.service.OpportunityService;
import com.cloudforce.util.ControllerUtils;

@Service
public class OpportunityServiceImpl extends GenericServiceImpl<Opportunity, Integer> implements OpportunityService {

    private final static Logger logger = LoggerFactory.getLogger(OpportunityServiceImpl.class);

	@Autowired
	OpportunityDAO opportunityDao;

	@Override
	public GenericDAO<Opportunity, Integer> getDAO() {
		return (GenericDAO<Opportunity, Integer>) opportunityDao;
	}
	
	public List<Opportunity> findAll () {
		List<Opportunity> opportunitys = opportunityDao.findAll();
		
		return opportunitys;	
		
	}

	public ResultDTO addOpportunity(OpportunityDTO opportunityDTO, RequestDTO requestDTO) {

		Opportunity opportunity = new Opportunity();

		opportunity.setOpportunityId(opportunityDTO.getOpportunityId());

		opportunity.setName(opportunityDTO.getName());

		opportunity.setStage(opportunityDTO.getStage());

		opportunity.setAmount(opportunityDTO.getAmount());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		opportunity = opportunityDao.save(opportunity);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Opportunity> getAllOpportunitys(Pageable pageable) {
		return opportunityDao.findAll(pageable);
	}

	public Page<Opportunity> getAllOpportunitys(Specification<Opportunity> spec, Pageable pageable) {
		return opportunityDao.findAll(spec, pageable);
	}

	public ResponseEntity<OpportunityPageDTO> getOpportunitys(OpportunitySearchDTO opportunitySearchDTO) {
	
			Integer opportunityId = opportunitySearchDTO.getOpportunityId(); 
 			String name = opportunitySearchDTO.getName(); 
 			String stage = opportunitySearchDTO.getStage(); 
  			String sortBy = opportunitySearchDTO.getSortBy();
			String sortOrder = opportunitySearchDTO.getSortOrder();
			String searchQuery = opportunitySearchDTO.getSearchQuery();
			Integer page = opportunitySearchDTO.getPage();
			Integer size = opportunitySearchDTO.getSize();

	        Specification<Opportunity> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, opportunityId, "opportunityId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, stage, "stage"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("stage")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Opportunity> opportunitys = this.getAllOpportunitys(spec, pageable);
		
		//System.out.println(String.valueOf(opportunitys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(opportunitys.getTotalPages()));
		
		List<Opportunity> opportunitysList = opportunitys.getContent();
		
		OpportunityConvertCriteriaDTO convertCriteria = new OpportunityConvertCriteriaDTO();
		List<OpportunityDTO> opportunityDTOs = this.convertOpportunitysToOpportunityDTOs(opportunitysList,convertCriteria);
		
		OpportunityPageDTO opportunityPageDTO = new OpportunityPageDTO();
		opportunityPageDTO.setOpportunitys(opportunityDTOs);
		opportunityPageDTO.setTotalElements(opportunitys.getTotalElements());
		return ResponseEntity.ok(opportunityPageDTO);
	}

	public List<OpportunityDTO> convertOpportunitysToOpportunityDTOs(List<Opportunity> opportunitys, OpportunityConvertCriteriaDTO convertCriteria) {
		
		List<OpportunityDTO> opportunityDTOs = new ArrayList<OpportunityDTO>();
		
		for (Opportunity opportunity : opportunitys) {
			opportunityDTOs.add(convertOpportunityToOpportunityDTO(opportunity,convertCriteria));
		}
		
		return opportunityDTOs;

	}
	
	public OpportunityDTO convertOpportunityToOpportunityDTO(Opportunity opportunity, OpportunityConvertCriteriaDTO convertCriteria) {
		
		OpportunityDTO opportunityDTO = new OpportunityDTO();

		opportunityDTO.setOpportunityId(opportunity.getOpportunityId());

		opportunityDTO.setName(opportunity.getName());

		opportunityDTO.setStage(opportunity.getStage());

		opportunityDTO.setAmount(opportunity.getAmount());
		
		return opportunityDTO;
	}

	public ResultDTO updateOpportunity(OpportunityDTO opportunityDTO, RequestDTO requestDTO) {
		
		Opportunity opportunity = opportunityDao.getById(opportunityDTO.getOpportunityId());
		
		opportunity.setOpportunityId(ControllerUtils.setValue(opportunity.getOpportunityId(), opportunityDTO.getOpportunityId()));
		
		opportunity.setName(ControllerUtils.setValue(opportunity.getName(), opportunityDTO.getName()));
		
		opportunity.setStage(ControllerUtils.setValue(opportunity.getStage(), opportunityDTO.getStage()));
		
		opportunity.setAmount(ControllerUtils.setValue(opportunity.getAmount(), opportunityDTO.getAmount()));

        opportunity = opportunityDao.save(opportunity);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public OpportunityDTO getOpportunityDTOById(Integer opportunityId) {
	
		Opportunity opportunity = opportunityDao.getById(opportunityId);
		
		OpportunityConvertCriteriaDTO convertCriteria = new OpportunityConvertCriteriaDTO();
		return(this.convertOpportunityToOpportunityDTO(opportunity,convertCriteria));
	}
}
