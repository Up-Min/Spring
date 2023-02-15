package com.trable.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trable.constant.ShowPost;
import com.trable.entity.Member;
import com.trable.entity.Post;
import com.trable.entity.Tag;

public interface PostRepository extends JpaRepository<Post, Long> {
		
	List<Post> findByMember(Member member);
	
	Optional<Post> findById(Long memberid);
	
	@Query(value =  "select * from post i where i.show_post = 'show'",nativeQuery = true)
	List<Post> findByMemberNotshow();
	
	@Query(value = "select * from post where post.show_post = 'show' and member_id != :MEMBERID", nativeQuery = true)
	List<Post> findbyshowNotmember(@Param("MEMBERID") int memberid);
	
	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG and a.show_post = 'show' ",nativeQuery = true)
	List<Post> findByTag(@Param("TAG") Tag tag);

	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG and a.show_post = 'show' ",nativeQuery = true)
	List<Post> findByTag1(@Param("TAG") String tag);

	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG and a.show_post = 'show' ",nativeQuery = true)
	List<Post> findByTag2(@Param("TAG") Optional<String> tag);


	@Query(value ="select * from post i where i.show_post = 'show' order by heart desc",nativeQuery = true)
	List<Post> findBylike();
	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG and a.show_post = 'show' order by a.heart desc", nativeQuery = true)
	List<Post> findBylikeSearch(@Param("TAG") String tag);
	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG and a.show_post = 'show' order by a.heart desc", nativeQuery = true)
	List<Post> findBylikeSearch(@Param("TAG") Optional<String> tag);
	
	@Query(value ="select * from post i where i.show_post = 'show' order by reg_time",nativeQuery = true)
	List<Post> findByctime();
	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG and a.show_post = 'show' order by a.reg_time", nativeQuery = true)
	List<Post> findByctimeSearch(@Param("TAG") String tag);	
	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG and a.show_post = 'show' order by a.reg_time", nativeQuery = true)
	List<Post> findByctimeSearch(@Param("TAG") Optional<String> tag);	
	
	@Query(value ="select * from post i where i.show_post = 'show' order by reg_time desc",nativeQuery = true)
	List<Post> findByrctime();
	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG and a.show_post = 'show' order by a.reg_time desc", nativeQuery = true)
	List<Post> findByrctimeSearch(@Param("TAG") String tag);	
	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG and a.show_post = 'show' order by a.reg_time desc", nativeQuery = true)
	List<Post> findByrctimeSearch(@Param("TAG") Optional<String> tag);	
	
	@Query(value ="select * from post i where i.show_post = 'show' order by up_date_time",nativeQuery = true)
	List<Post> findByutime();
	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG and a.show_post = 'show' order by a.up_date_time", nativeQuery = true)
	List<Post> findByutimeSearch(@Param("TAG") String tag);	
	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG and a.show_post = 'show' order by a.up_date_time", nativeQuery = true)
	List<Post> findByutimeSearch(@Param("TAG") Optional<String> tag);	
	
	@Query(value ="select * from post i where i.show_post = 'show' order by up_date_time desc",nativeQuery = true)
	List<Post> findByrutime();
	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG and a.show_post = 'show' order by a.up_date_time desc", nativeQuery = true)
	List<Post> findByrutimeSearch(@Param("TAG") String tag);	
	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG and a.show_post = 'show' order by a.up_date_time desc", nativeQuery = true)
	List<Post> findByrutimeSearch(@Param("TAG") Optional<String> tag);	
	
	
	
	


	
	
}
