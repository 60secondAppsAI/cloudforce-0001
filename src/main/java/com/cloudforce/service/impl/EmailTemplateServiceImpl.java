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
import com.cloudforce.dao.EmailTemplateDAO;
import com.cloudforce.domain.EmailTemplate;
import com.cloudforce.dto.EmailTemplateDTO;
import com.cloudforce.dto.EmailTemplateSearchDTO;
import com.cloudforce.dto.EmailTemplatePageDTO;
import com.cloudforce.dto.EmailTemplateConvertCriteriaDTO;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import com.cloudforce.service.EmailTemplateService;
import com.cloudforce.util.ControllerUtils;

@Service
public class EmailTemplateServiceImpl extends GenericServiceImpl<EmailTemplate, Integer> implements EmailTemplateService {

    private final static Logger logger = LoggerFactory.getLogger(EmailTemplateServiceImpl.class);

	@Autowired
	EmailTemplateDAO emailTemplateDao;

	@Override
	public GenericDAO<EmailTemplate, Integer> getDAO() {
		return (GenericDAO<EmailTemplate, Integer>) emailTemplateDao;
	}
	
	public List<EmailTemplate> findAll () {
		List<EmailTemplate> emailTemplates = emailTemplateDao.findAll();
		
		return emailTemplates;	
		
	}

	public ResultDTO addEmailTemplate(EmailTemplateDTO emailTemplateDTO, RequestDTO requestDTO) {

		EmailTemplate emailTemplate = new EmailTemplate();

		emailTemplate.setEmailTemplateId(emailTemplateDTO.getEmailTemplateId());

		emailTemplate.setName(emailTemplateDTO.getName());

		emailTemplate.setSubject(emailTemplateDTO.getSubject());

		emailTemplate.setBody(emailTemplateDTO.getBody());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		emailTemplate = emailTemplateDao.save(emailTemplate);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<EmailTemplate> getAllEmailTemplates(Pageable pageable) {
		return emailTemplateDao.findAll(pageable);
	}

	public Page<EmailTemplate> getAllEmailTemplates(Specification<EmailTemplate> spec, Pageable pageable) {
		return emailTemplateDao.findAll(spec, pageable);
	}

	public ResponseEntity<EmailTemplatePageDTO> getEmailTemplates(EmailTemplateSearchDTO emailTemplateSearchDTO) {
	
			Integer emailTemplateId = emailTemplateSearchDTO.getEmailTemplateId(); 
 			String name = emailTemplateSearchDTO.getName(); 
 			String subject = emailTemplateSearchDTO.getSubject(); 
 			String body = emailTemplateSearchDTO.getBody(); 
 			String sortBy = emailTemplateSearchDTO.getSortBy();
			String sortOrder = emailTemplateSearchDTO.getSortOrder();
			String searchQuery = emailTemplateSearchDTO.getSearchQuery();
			Integer page = emailTemplateSearchDTO.getPage();
			Integer size = emailTemplateSearchDTO.getSize();

	        Specification<EmailTemplate> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, emailTemplateId, "emailTemplateId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, subject, "subject"); 
			
			spec = ControllerUtils.andIfNecessary(spec, body, "body"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("subject")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("body")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<EmailTemplate> emailTemplates = this.getAllEmailTemplates(spec, pageable);
		
		//System.out.println(String.valueOf(emailTemplates.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(emailTemplates.getTotalPages()));
		
		List<EmailTemplate> emailTemplatesList = emailTemplates.getContent();
		
		EmailTemplateConvertCriteriaDTO convertCriteria = new EmailTemplateConvertCriteriaDTO();
		List<EmailTemplateDTO> emailTemplateDTOs = this.convertEmailTemplatesToEmailTemplateDTOs(emailTemplatesList,convertCriteria);
		
		EmailTemplatePageDTO emailTemplatePageDTO = new EmailTemplatePageDTO();
		emailTemplatePageDTO.setEmailTemplates(emailTemplateDTOs);
		emailTemplatePageDTO.setTotalElements(emailTemplates.getTotalElements());
		return ResponseEntity.ok(emailTemplatePageDTO);
	}

	public List<EmailTemplateDTO> convertEmailTemplatesToEmailTemplateDTOs(List<EmailTemplate> emailTemplates, EmailTemplateConvertCriteriaDTO convertCriteria) {
		
		List<EmailTemplateDTO> emailTemplateDTOs = new ArrayList<EmailTemplateDTO>();
		
		for (EmailTemplate emailTemplate : emailTemplates) {
			emailTemplateDTOs.add(convertEmailTemplateToEmailTemplateDTO(emailTemplate,convertCriteria));
		}
		
		return emailTemplateDTOs;

	}
	
	public EmailTemplateDTO convertEmailTemplateToEmailTemplateDTO(EmailTemplate emailTemplate, EmailTemplateConvertCriteriaDTO convertCriteria) {
		
		EmailTemplateDTO emailTemplateDTO = new EmailTemplateDTO();

		emailTemplateDTO.setEmailTemplateId(emailTemplate.getEmailTemplateId());

		emailTemplateDTO.setName(emailTemplate.getName());

		emailTemplateDTO.setSubject(emailTemplate.getSubject());

		emailTemplateDTO.setBody(emailTemplate.getBody());
		
		return emailTemplateDTO;
	}

	public ResultDTO updateEmailTemplate(EmailTemplateDTO emailTemplateDTO, RequestDTO requestDTO) {
		
		EmailTemplate emailTemplate = emailTemplateDao.getById(emailTemplateDTO.getEmailTemplateId());
		
		emailTemplate.setEmailTemplateId(ControllerUtils.setValue(emailTemplate.getEmailTemplateId(), emailTemplateDTO.getEmailTemplateId()));
		
		emailTemplate.setName(ControllerUtils.setValue(emailTemplate.getName(), emailTemplateDTO.getName()));
		
		emailTemplate.setSubject(ControllerUtils.setValue(emailTemplate.getSubject(), emailTemplateDTO.getSubject()));
		
		emailTemplate.setBody(ControllerUtils.setValue(emailTemplate.getBody(), emailTemplateDTO.getBody()));

        emailTemplate = emailTemplateDao.save(emailTemplate);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public EmailTemplateDTO getEmailTemplateDTOById(Integer emailTemplateId) {
	
		EmailTemplate emailTemplate = emailTemplateDao.getById(emailTemplateId);
		
		EmailTemplateConvertCriteriaDTO convertCriteria = new EmailTemplateConvertCriteriaDTO();
		return(this.convertEmailTemplateToEmailTemplateDTO(emailTemplate,convertCriteria));
	}
}
