package com.cloudforce.dao;

import java.util.List;

import com.cloudforce.dao.GenericDAO;
import com.cloudforce.domain.Contact;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ContactDAO extends GenericDAO<Contact, Integer> {
  
	List<Contact> findAll();
	


}

