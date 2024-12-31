package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.Contact;
import com.cloudforce.dto.ContactDTO;
import com.cloudforce.dto.ContactSearchDTO;
import com.cloudforce.dto.ContactPageDTO;
import com.cloudforce.dto.ContactConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ContactService extends GenericService<Contact, Integer> {

	List<Contact> findAll();

	ResultDTO addContact(ContactDTO contactDTO, RequestDTO requestDTO);

	ResultDTO updateContact(ContactDTO contactDTO, RequestDTO requestDTO);

    Page<Contact> getAllContacts(Pageable pageable);

    Page<Contact> getAllContacts(Specification<Contact> spec, Pageable pageable);

	ResponseEntity<ContactPageDTO> getContacts(ContactSearchDTO contactSearchDTO);
	
	List<ContactDTO> convertContactsToContactDTOs(List<Contact> contacts, ContactConvertCriteriaDTO convertCriteria);

	ContactDTO getContactDTOById(Integer contactId);


}
