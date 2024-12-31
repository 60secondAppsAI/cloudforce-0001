package com.cloudforce.dao;

import java.util.List;

import com.cloudforce.dao.GenericDAO;
import com.cloudforce.domain.Product;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ProductDAO extends GenericDAO<Product, Integer> {
  
	List<Product> findAll();
	


}
