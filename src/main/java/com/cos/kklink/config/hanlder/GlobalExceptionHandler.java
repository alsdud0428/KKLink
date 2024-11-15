package com.cos.kklink.config.hanlder;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.kklink.config.hanlder.ex.MyImageDeleteException;
import com.cos.kklink.config.hanlder.ex.MyImageIdNotFoundException;
import com.cos.kklink.config.hanlder.ex.MyPasswordCheckException;
import com.cos.kklink.config.hanlder.ex.MyUserIdNotFoundException;
import com.cos.kklink.config.hanlder.ex.MyUserInfoExistException;
import com.cos.kklink.config.hanlder.ex.MyUsernameNotFoundException;
import com.cos.kklink.util.Script;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = MyUserIdNotFoundException.class)
	public String myUserIdNotFoundException(Exception e) {
		return Script.back(e.getMessage());
	}

	@ExceptionHandler(value = MyUsernameNotFoundException.class)
	public String myUsernameNotFoundException(Exception e) {
		return Script.alert(e.getMessage());
	}

	@ExceptionHandler(value = MyImageIdNotFoundException.class)
	public String myImageIdNotFoundException(Exception e) {
		return Script.alert(e.getMessage());
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public String myIllegalArgumentException(Exception e) {
		return Script.alert(e.getMessage());
	}

	// LKH 회원가입 중복체크 에러 핸들러
	@ExceptionHandler(value = MyUserInfoExistException.class)
	public String MyUserInfoExistException(Exception e) {
		return Script.back(e.getMessage());
	}

	// LKH 게시물 삭제 시도시 작성자가 아니면 삭제할수 없음.
	@ExceptionHandler(value = MyImageDeleteException.class)
	public String MyImageDeleteException(Exception e) {
		return (e.getMessage());
	}

	// 현재 비밀번호를 확인하기 위한 예외
	@ExceptionHandler(value = MyPasswordCheckException.class)
	public String MyPasswordCheckException(Exception e) {
		return Script.back(e.getMessage());
	}
}
