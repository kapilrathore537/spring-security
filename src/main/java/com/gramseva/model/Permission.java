package com.gramseva.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Permission extends AuditData {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String permissionId;
	private Boolean isCreate;
	private Boolean isUpdate;
	private Boolean isRead;
	private Boolean isDelete;

	@Enumerated(EnumType.STRING)
	private PermissionTitle permissionTitle;

	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;
}
