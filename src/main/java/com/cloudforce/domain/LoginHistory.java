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
@Table(name="login_historys")
@Getter @Setter @NoArgsConstructor
public class LoginHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="login_history_id")
	private Integer loginHistoryId;
    
  	@Column(name="login_time")
	private Date loginTime;
    
  	@Column(name="ip_address")
	private String ipAddress;
    
}
