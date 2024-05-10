package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Integer id;

	@NotBlank(message = "Username is mandatory")
	@Column(name = "username")
	private String username;

	@NotBlank(message = "Password is mandatory")
	@Column(name = "password")
	private String password;

	@NotBlank(message = "FullName is mandatory")
	@Column(name = "fullname")
	private String fullname;

	@NotBlank(message = "Role is mandatory")
	@Column(name = "role")
	private String role;

	public User() {
		super();
	}

	public User(Integer id, @NotBlank(message = "Username is mandatory") String username,
			@NotBlank(message = "Password is mandatory") String password,
			@NotBlank(message = "FullName is mandatory") String fullname,
			@NotBlank(message = "Role is mandatory") String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
