package com.cloudforce.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="leads")
@Getter @Setter @NoArgsConstructor
public class Lead {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="lead_id")
	private Integer leadId;
    
  	@Column(name="full_name")
	private String fullName;
    
  	@Column(name="email")
	private String email;
    
  	@Column(name="phone_number")
	private String phoneNumber;
    
  	@Column(name="status")
	private String status;
    
}
