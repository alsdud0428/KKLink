package com.cos.kklink.domain.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {

	List<Image> findByUserId(int userId);


	@Query(value = "select * from image where userId in (select id from user where id != ?1 and id not in (select toUserId from follow where fromUserId = ?1)) limit 20", nativeQuery = true)
	List<Image> mNonFollowImage(int loginUserId);


	@Query(value = "select * from image where userId = ?1 or userId in (select toUserId from follow where fromUserId = ?1) order by createDate desc", nativeQuery = true)
	List<Image> mFeeds(int loginUserId);


	@Query(value = "select * from image order by createDate desc", nativeQuery = true)
	List<Image> mAllFeeds(int loginUserId);


	@Query(value = "select * from image where id in (select imageId from tag where name like ?1)", nativeQuery = true)
	List<Image> mFeeds(String tag);


	@Query(value = "select * from image where id = ?1", nativeQuery = true)
	List<Image> mBoardImage(int imageId);

}
