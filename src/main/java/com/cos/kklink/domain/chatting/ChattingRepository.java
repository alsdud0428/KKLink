package com.cos.kklink.domain.chatting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChattingRepository extends JpaRepository<Chatting, Integer> {

	@Modifying
	@Query(value = "INSERT INTO chatting(fromUserId, toUserId, message, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
	int mChatSave(int fromUserId, int toUserId, String message);

	List<Chatting> findByFromUserIdAndToUserId(int fromUserId, int toUserId);


	@Query("select m from Chatting as m where (m.fromUser.id = :fromUserId and m.toUser.id = :toUserId) or "
			+ "(m.toUser.id = :fromUserId and m.fromUser.id = :toUserId) order by createDate")
	List<Chatting> mChatList(@Param("fromUserId") int fromUserId, @Param("toUserId") int toUserId);
}
