package com.cos.kklink.service;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.kklink.config.hanlder.ex.MyImageIdNotFoundException;
import com.cos.kklink.domain.image.Image;
import com.cos.kklink.domain.image.ImageRepository;
import com.cos.kklink.domain.like.LikesRepository;
import com.cos.kklink.domain.noti.NotiRepository;
import com.cos.kklink.domain.noti.NotiType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikesService {
	private final LikesRepository likesRepository;
	private final NotiRepository notiRepository;
	private final ImageRepository imageRepository;
	
	@Transactional
	public void 좋아요(int imageId, int loginUserId) {
		likesRepository.mSave(imageId, loginUserId);
		Image imageEntity = imageRepository.findById(imageId).orElseThrow(new Supplier<MyImageIdNotFoundException>() {
			@Override
			public MyImageIdNotFoundException get() {
				return new MyImageIdNotFoundException();
			}
		});
		notiRepository.mSave(loginUserId, imageEntity.getUser().getId(), NotiType.LIKE.name());
	}
	
	@Transactional
	public void 싫어요(int imageId, int loginUserId) {
		likesRepository.mDelete(imageId, loginUserId);
	}
}
