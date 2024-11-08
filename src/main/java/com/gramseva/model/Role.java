package com.gramseva.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends AuditData{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String roleId;
	private String roleName;
	private String description;
	@Enumerated(EnumType.STRING)
	private RoleType type;

	@OneToMany
	@JoinColumn(name="role_id")
	private List<Permission> permissions;
}
