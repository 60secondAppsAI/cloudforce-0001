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
import com.cloudforce.dao.ContactDAO;
import com.cloudforce.domain.Contact;
import com.cloudforce.dto.ContactDTO;
import com.cloudforce.dto.ContactSearchDTO;
import com.cloudforce.dto.ContactPageDTO;
import com.cloudforce.dto.ContactConvertCriteriaDTO;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import com.cloudforce.service.ContactService;
import com.cloudforce.util.ControllerUtils;

@Service
public class ContactServiceImpl extends GenericServiceImpl<Contact, Integer> implements ContactService {

    private final static Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

	@Autowired
	ContactDAO contactDao;

	@Override
	public GenericDAO<Contact, Integer> getDAO() {
		return (GenericDAO<Contact, Integer>) contactDao;
	}
	
	public List<Contact> findAll () {
		List<Contact> contacts = contactDao.findAll();
		
		return contacts;	
		
	}

	public ResultDTO addContact(ContactDTO contactDTO, RequestDTO requestDTO) {

		Contact contact = new Contact();

		contact.setContactId(contactDTO.getContactId());

		contact.setFirstName(contactDTO.getFirstName());

		contact.setLastName(contactDTO.getLastName());

		contact.setEmail(contactDTO.getEmail());

		contact.setPhoneNumber(contactDTO.getPhoneNumber());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		contact = contactDao.save(contact);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Contact> getAllContacts(Pageable pageable) {
		return contactDao.findAll(pageable);
	}

	public Page<Contact> getAllContacts(Specification<Contact> spec, Pageable pageable) {
		return contactDao.findAll(spec, pageable);
	}

	public ResponseEntity<ContactPageDTO> getContacts(ContactSearchDTO contactSearchDTO) {
	
			Integer contactId = contactSearchDTO.getContactId(); 
 			String firstName = contactSearchDTO.getFirstName(); 
 			String lastName = contactSearchDTO.getLastName(); 
 			String email = contactSearchDTO.getEmail(); 
 			String phoneNumber = contactSearchDTO.getPhoneNumber(); 
 			String sortBy = contactSearchDTO.getSortBy();
			String sortOrder = contactSearchDTO.getSortOrder();
			String searchQuery = contactSearchDTO.getSearchQuery();
			Integer page = contactSearchDTO.getPage();
			Integer size = contactSearchDTO.getSize();

	        Specification<Contact> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, contactId, "contactId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, firstName, "firstName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, lastName, "lastName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, email, "email"); 
			
			spec = ControllerUtils.andIfNecessary(spec, phoneNumber, "phoneNumber"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("firstName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("lastName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("email")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("phoneNumber")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Contact> contacts = this.getAllContacts(spec, pageable);
		
		//System.out.println(String.valueOf(contacts.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(contacts.getTotalPages()));
		
		List<Contact> contactsList = contacts.getContent();
		
		ContactConvertCriteriaDTO convertCriteria = new ContactConvertCriteriaDTO();
		List<ContactDTO> contactDTOs = this.convertContactsToContactDTOs(contactsList,convertCriteria);
		
		ContactPageDTO contactPageDTO = new ContactPageDTO();
		contactPageDTO.setContacts(contactDTOs);
		contactPageDTO.setTotalElements(contacts.getTotalElements());
		return ResponseEntity.ok(contactPageDTO);
	}

	public List<ContactDTO> convertContactsToContactDTOs(List<Contact> contacts, ContactConvertCriteriaDTO convertCriteria) {
		
		List<ContactDTO> contactDTOs = new ArrayList<ContactDTO>();
		
		for (Contact contact : contacts) {
			contactDTOs.add(convertContactToContactDTO(contact,convertCriteria));
		}
		
		return contactDTOs;

	}
	
	public ContactDTO convertContactToContactDTO(Contact contact, ContactConvertCriteriaDTO convertCriteria) {
		
		ContactDTO contactDTO = new ContactDTO();

		contactDTO.setContactId(contact.getContactId());

		contactDTO.setFirstName(contact.getFirstName());

		contactDTO.setLastName(contact.getLastName());

		contactDTO.setEmail(contact.getEmail());

		contactDTO.setPhoneNumber(contact.getPhoneNumber());
		
		return contactDTO;
	}

	public ResultDTO updateContact(ContactDTO contactDTO, RequestDTO requestDTO) {
		
		Contact contact = contactDao.getById(contactDTO.getContactId());
		
		contact.setContactId(ControllerUtils.setValue(contact.getContactId(), contactDTO.getContactId()));
		
		contact.setFirstName(ControllerUtils.setValue(contact.getFirstName(), contactDTO.getFirstName()));
		
		contact.setLastName(ControllerUtils.setValue(contact.getLastName(), contactDTO.getLastName()));
		
		contact.setEmail(ControllerUtils.setValue(contact.getEmail(), contactDTO.getEmail()));
		
		contact.setPhoneNumber(ControllerUtils.setValue(contact.getPhoneNumber(), contactDTO.getPhoneNumber()));

        contact = contactDao.save(contact);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ContactDTO getContactDTOById(Integer contactId) {
	
		Contact contact = contactDao.getById(contactId);
		
		ContactConvertCriteriaDTO convertCriteria = new ContactConvertCriteriaDTO();
		return(this.convertContactToContactDTO(contact,convertCriteria));
	}
}
