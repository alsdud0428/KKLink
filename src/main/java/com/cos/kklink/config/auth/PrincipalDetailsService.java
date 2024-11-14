package com.cos.kklink.config.auth;

import java.util.Optional;
import java.util.function.Function;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.kklink.config.auth.dto.LoginUser;
import com.cos.kklink.domain.user.User;
import com.cos.kklink.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(PrincipalDetailsService.class);
	private final UserRepository userRepository;
	private final HttpSession session;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername : username : " + username);


		Optional<User> searchNullUser = userRepository.findByUsername(username);
		log.info("searchUser : " + searchNullUser);

		if (searchNullUser.equals(Optional.empty())) {
			throw new UsernameNotFoundException(username + "is not found.");
		}

		User userEntity = userRepository.findByUsername(username).map(new Function<User, User>() {
			@Override
			public User apply(User t) {
				session.setAttribute("loginUser", new LoginUser(t));
				return t;
			}
		}).orElse(null);
		return new PrincipalDetails(userEntity);
	}

}
