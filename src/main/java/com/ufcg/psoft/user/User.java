package com.ufcg.psoft.user;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_client")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String username;

	private String password;

	private String name;

	private boolean admin;

	private String accessToken;

	public User() {

	}

	public User(String username, String password, String name, boolean admin) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.admin = admin;
		this.accessToken = accessToken;
	}

	public User(String username, String password, String name, boolean admin, String accessToken) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.admin = admin;
		this.accessToken = accessToken;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}