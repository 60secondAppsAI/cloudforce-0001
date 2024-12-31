package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.EmailTemplate;
import com.cloudforce.dto.EmailTemplateDTO;
import com.cloudforce.dto.EmailTemplateSearchDTO;
import com.cloudforce.dto.EmailTemplatePageDTO;
import com.cloudforce.dto.EmailTemplateConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface EmailTemplateService extends GenericService<EmailTemplate, Integer> {

	List<EmailTemplate> findAll();

	ResultDTO addEmailTemplate(EmailTemplateDTO emailTemplateDTO, RequestDTO requestDTO);

	ResultDTO updateEmailTemplate(EmailTemplateDTO emailTemplateDTO, RequestDTO requestDTO);

    Page<EmailTemplate> getAllEmailTemplates(Pageable pageable);

    Page<EmailTemplate> getAllEmailTemplates(Specification<EmailTemplate> spec, Pageable pageable);

	ResponseEntity<EmailTemplatePageDTO> getEmailTemplates(EmailTemplateSearchDTO emailTemplateSearchDTO);
	
	List<EmailTemplateDTO> convertEmailTemplatesToEmailTemplateDTOs(List<EmailTemplate> emailTemplates, EmailTemplateConvertCriteriaDTO convertCriteria);

	EmailTemplateDTO getEmailTemplateDTOById(Integer emailTemplateId);


}
