package com.cos.kklink.web.dto;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.kklink.config.auth.LoginUserAnnotation;
import com.cos.kklink.config.auth.dto.LoginUser;
import com.cos.kklink.domain.noti.Noti;
import com.cos.kklink.service.NotiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class NotiController {
	private final NotiService notiService;
	
	
	
	@GetMapping("/noti/{loginUserId}")
	public String noti(@PathVariable int loginUserId, Model model, @LoginUserAnnotation LoginUser loginUser) {
		model.addAttribute("notis", notiService.알림리스트(loginUser.getId()));
		//model.addAttribute("notis", notiService.알림팔로우(loginUser.getId()));
		/*
		 * model.addAttribute("notis", notiService.알림리스트(loginUserId));
		 */		
		return "noti/noti";
	}
	
	@GetMapping("/test/noti/{loginUserId}")
	public @ResponseBody List<Noti> testNoti(@PathVariable int loginUserId, Model model) {
		
		return notiService.알림리스트(loginUserId);
	}
}
