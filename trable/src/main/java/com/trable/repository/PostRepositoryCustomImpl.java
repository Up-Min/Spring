//package com.trable.repository;
//
//import java.awt.print.Pageable;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//
//import org.springframework.data.domain.Page;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.trable.dto.PostSearchDto;
//import com.trable.entity.Post;
//
//public class PostRepositoryCustomImpl implements PostRepositoryCustom{
//
//	
//	private JPAQueryFactory queryFactory;
//	
//	public PostRepositoryCustomImpl(EntityManager em) {
//		this.queryFactory = new JPAQueryFactory(em);
//	}
//
//	@Override
//	public Page<Post> getItemPage(PostSearchDto postSearchDto, Pageable pageable) {
//
//
//	}	
//}
