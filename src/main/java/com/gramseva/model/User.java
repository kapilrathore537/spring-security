package com.gramseva.model;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User extends AuditData{

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String userId;
	private String userName;
	private String password;
	private String email;
	private String profilePicture="defaultProfileName";
	private String contactNumber;
	private String otp;
	@Enumerated(EnumType.STRING)
	private DeactivateStatus deactivateStatus;
    private LocalDateTime statusUpdatedDate;
	private Boolean isOtpExpired;
    
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;
}
