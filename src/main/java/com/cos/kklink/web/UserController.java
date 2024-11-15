package com.cos.kklink.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cos.kklink.config.auth.LoginUserAnnotation;
import com.cos.kklink.config.auth.dto.LoginUser;
import com.cos.kklink.domain.image.Image;
import com.cos.kklink.domain.message.Message;
import com.cos.kklink.domain.user.User;
import com.cos.kklink.service.FollowService;
import com.cos.kklink.service.NotiService;
import com.cos.kklink.service.UserService;
import com.cos.kklink.web.dto.UserProfileRespDto;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;
	
	private final NotiService notiService;

	// 팔로워,팔로우 모달창에 구현
	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable int pageUserId, @LoginUserAnnotation LoginUser loginUser, Model model) {
		UserProfileRespDto userProfileRespDto = userService.회원프로필(pageUserId, loginUser);
		model.addAttribute("respDto", userProfileRespDto);
		model.addAttribute("followingList", followService.팔로잉리스트(loginUser.getId(), pageUserId));
		model.addAttribute("followerList", followService.팔로워리스트(loginUser.getId(), pageUserId));
		model.addAttribute("notis", notiService.알림리스트(loginUser.getId()));

		// 프로필 페이지에서 특정 회원의 게시물정보를 받아오기위해 추가한 부분
		List<Image> UserBoard = userService.특정유저게시물(pageUserId, loginUser.getId());
		model.addAttribute("board", UserBoard);
		return "user/profile";
	}

	@GetMapping("/user/profileEdit")
	public String profileEdit(@LoginUserAnnotation LoginUser loginUser, Model model) {
		User userEntity = userService.회원정보(loginUser);
//		model.addAttribute("respDto", userProfileRespDto);
		model.addAttribute("respDto",userService.회원프로필(loginUser.getId(), loginUser));
		model.addAttribute("user", userEntity);
		model.addAttribute("notis", notiService.알림리스트(loginUser.getId()));
		return "user/profile-edit";
	}
	
	@PostMapping("/user/profileEditUpload")
	public String profileEdit(@RequestParam("profileImage") MultipartFile file, int userId,
			@LoginUserAnnotation LoginUser loginUser) {
		if (userId == loginUser.getId()) {
			userService.프로필사진업로드(loginUser, file);
		}

		return "redirect:/user/profile-edit";
	}

	@PutMapping("/user")
	public ResponseEntity<?> userUpdate(User user) {
		userService.회원수정(user);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	// 원래는 put으로 하는게 맞는데 편하게 하기 위해
	@PostMapping("/user/profileUpload")
	public String userProfileUpload(@RequestParam("profileImage") MultipartFile file, int userId,
			@LoginUserAnnotation LoginUser loginUser) {
		if (userId == loginUser.getId()) {
			userService.프로필사진업로드(loginUser, file);
		}

		return "redirect:/user/" + userId;
	}

	// LKH DM 을 위한 UserController 소스
	@MessageMapping("/user") // 최초로 오는 사용자들을 받습니다.
	public void StoreUser(User user) {
		System.out.println("최초로 오는 사용자 : " + user.getName());
	}

	@MessageMapping("/topic/user/{userid}") // 매핑되는 곳
	@SendTo("/topic/user/{userid}") // 이 토픽을 구독하고 있는 모든 클라이언트에게 메시지를 보낸다.
	public Message sendToUser(@DestinationVariable String userid, Message message) {
		System.out.println(message.toString());
		Message msg = new Message(message.getId(), message.getMessage());
		return msg;
	}

}
