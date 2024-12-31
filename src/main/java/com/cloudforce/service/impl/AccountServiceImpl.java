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
import com.cloudforce.dao.AccountDAO;
import com.cloudforce.domain.Account;
import com.cloudforce.dto.AccountDTO;
import com.cloudforce.dto.AccountSearchDTO;
import com.cloudforce.dto.AccountPageDTO;
import com.cloudforce.dto.AccountConvertCriteriaDTO;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import com.cloudforce.service.AccountService;
import com.cloudforce.util.ControllerUtils;

@Service
public class AccountServiceImpl extends GenericServiceImpl<Account, Integer> implements AccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	AccountDAO accountDao;

	@Override
	public GenericDAO<Account, Integer> getDAO() {
		return (GenericDAO<Account, Integer>) accountDao;
	}
	
	public List<Account> findAll () {
		List<Account> accounts = accountDao.findAll();
		
		return accounts;	
		
	}

	public ResultDTO addAccount(AccountDTO accountDTO, RequestDTO requestDTO) {

		Account account = new Account();

		account.setAccountId(accountDTO.getAccountId());

		account.setAccountName(accountDTO.getAccountName());

		account.setAccountType(accountDTO.getAccountType());

		account.setIndustry(accountDTO.getIndustry());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		account = accountDao.save(account);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Account> getAllAccounts(Pageable pageable) {
		return accountDao.findAll(pageable);
	}

	public Page<Account> getAllAccounts(Specification<Account> spec, Pageable pageable) {
		return accountDao.findAll(spec, pageable);
	}

	public ResponseEntity<AccountPageDTO> getAccounts(AccountSearchDTO accountSearchDTO) {
	
			Integer accountId = accountSearchDTO.getAccountId(); 
 			String accountName = accountSearchDTO.getAccountName(); 
 			String accountType = accountSearchDTO.getAccountType(); 
 			String industry = accountSearchDTO.getIndustry(); 
 			String sortBy = accountSearchDTO.getSortBy();
			String sortOrder = accountSearchDTO.getSortOrder();
			String searchQuery = accountSearchDTO.getSearchQuery();
			Integer page = accountSearchDTO.getPage();
			Integer size = accountSearchDTO.getSize();

	        Specification<Account> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, accountId, "accountId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, accountName, "accountName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, accountType, "accountType"); 
			
			spec = ControllerUtils.andIfNecessary(spec, industry, "industry"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("accountName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("accountType")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("industry")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Account> accounts = this.getAllAccounts(spec, pageable);
		
		//System.out.println(String.valueOf(accounts.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(accounts.getTotalPages()));
		
		List<Account> accountsList = accounts.getContent();
		
		AccountConvertCriteriaDTO convertCriteria = new AccountConvertCriteriaDTO();
		List<AccountDTO> accountDTOs = this.convertAccountsToAccountDTOs(accountsList,convertCriteria);
		
		AccountPageDTO accountPageDTO = new AccountPageDTO();
		accountPageDTO.setAccounts(accountDTOs);
		accountPageDTO.setTotalElements(accounts.getTotalElements());
		return ResponseEntity.ok(accountPageDTO);
	}

	public List<AccountDTO> convertAccountsToAccountDTOs(List<Account> accounts, AccountConvertCriteriaDTO convertCriteria) {
		
		List<AccountDTO> accountDTOs = new ArrayList<AccountDTO>();
		
		for (Account account : accounts) {
			accountDTOs.add(convertAccountToAccountDTO(account,convertCriteria));
		}
		
		return accountDTOs;

	}
	
	public AccountDTO convertAccountToAccountDTO(Account account, AccountConvertCriteriaDTO convertCriteria) {
		
		AccountDTO accountDTO = new AccountDTO();

		accountDTO.setAccountId(account.getAccountId());

		accountDTO.setAccountName(account.getAccountName());

		accountDTO.setAccountType(account.getAccountType());

		accountDTO.setIndustry(account.getIndustry());
		
		return accountDTO;
	}

	public ResultDTO updateAccount(AccountDTO accountDTO, RequestDTO requestDTO) {
		
		Account account = accountDao.getById(accountDTO.getAccountId());
		
		account.setAccountId(ControllerUtils.setValue(account.getAccountId(), accountDTO.getAccountId()));
		
		account.setAccountName(ControllerUtils.setValue(account.getAccountName(), accountDTO.getAccountName()));
		
		account.setAccountType(ControllerUtils.setValue(account.getAccountType(), accountDTO.getAccountType()));
		
		account.setIndustry(ControllerUtils.setValue(account.getIndustry(), accountDTO.getIndustry()));

        account = accountDao.save(account);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public AccountDTO getAccountDTOById(Integer accountId) {
	
		Account account = accountDao.getById(accountId);
		
		AccountConvertCriteriaDTO convertCriteria = new AccountConvertCriteriaDTO();
		return(this.convertAccountToAccountDTO(account,convertCriteria));
	}
}
