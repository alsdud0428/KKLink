package com.cos.kklink.config.auth.dto;

import com.cos.kklink.domain.user.User;

import lombok.Data;

@Data
public class LoginUser {
	private int id;
	private String username;
	private String email;
	private String name;
	private String role;
	private String provider;
	private String providerId;
	private String imageUrl;

	public LoginUser(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.name = user.getName();
		this.role = user.getRole().getKey();
		this.provider = user.getProvider();
		this.providerId = user.getProviderId();
		this.imageUrl = user.getProfileImage();
	}

	public User getUser() {
		return User.builder().id(id).build();
	}
}
