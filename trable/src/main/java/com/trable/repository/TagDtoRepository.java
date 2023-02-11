//package com.trable.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import com.trable.dto.TagDto;
//
//public interface TagDtoRepository extends JpaRepository<TagDto, Long>{
//
//	@Query(value = "select c.tagname from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where a.member_id = :MEMBERID group by c.tagname having count(c.tagname) >= '2'", nativeQuery = true)
//	List<TagDto> getTagnamebycount (@Param("MEMBERID") Long memberid);
//	
//}
