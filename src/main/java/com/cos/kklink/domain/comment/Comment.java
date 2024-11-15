package com.cos.kklink.domain.comment;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.kklink.domain.image.Image;
import com.cos.kklink.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String content;

	@ManyToOne
	@JoinColumn(name="imageId")
	private Image image;
	
	// 수정
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	// 수정
	@CreationTimestamp
	private Timestamp createDate;
	
	@Transient
	private boolean commentHost;
}



