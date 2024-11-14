package com.cos.kklink.service;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.kklink.config.hanlder.ex.MyImageIdNotFoundException;
import com.cos.kklink.domain.comment.CommentRepository;
import com.cos.kklink.domain.image.Image;
import com.cos.kklink.domain.image.ImageRepository;
import com.cos.kklink.domain.noti.NotiRepository;
import com.cos.kklink.domain.noti.NotiType;
import com.cos.kklink.web.dto.CommentRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final NotiRepository notiRepository;
	private final ImageRepository imageRepository;
	
	@Transactional
	public void 댓글쓰기(CommentRespDto commentRespDto) {
		commentRepository.mSave( 
				commentRespDto.getUserId(), 
				commentRespDto.getImageId(), 
				commentRespDto.getContent());
		Image imageEntity = imageRepository.findById(commentRespDto.getImageId()).orElseThrow(new Supplier<MyImageIdNotFoundException>() {
			@Override
			public MyImageIdNotFoundException get() {
				return new MyImageIdNotFoundException();
			}
		});
		notiRepository.mSave(commentRespDto.getUserId(), imageEntity.getUser().getId(), NotiType.COMMENT.name());
	}
	
	@Transactional
	public void 댓글삭제(int id) {
		commentRepository.deleteById(id);
	}
}
