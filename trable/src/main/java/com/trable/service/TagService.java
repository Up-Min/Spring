package com.trable.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.trable.dto.PostFormDto;
import com.trable.entity.Member;
import com.trable.entity.Post;
import com.trable.entity.PostTag;
import com.trable.entity.Tag;
import com.trable.repository.PostRepository;
import com.trable.repository.PostTagRepository;
import com.trable.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
	
	private final TagRepository tagRepository;
	private final PostRepository postRepository;
	private final PostTagRepository postTagRepository;
	private final MemberService memberService;
	
	
	public void saveTag(String tagname, Post post) {
		String tag1 = tagname.trim();
		String[] taglist = tag1.split("#");
		
		for(int i=1; i<taglist.length; i++) {
			try {
				Tag tag = tagRepository.findByTagname(taglist[i]).orElseThrow(EntityNotFoundException::new);
				PostTag postTag = PostTag.createPostTag(post, tag);
				postTagRepository.save(postTag);
				
			} catch (EntityNotFoundException e) {
				Tag tag = Tag.createTag(taglist[i]);
				tagRepository.save(tag);
				PostTag postTag = PostTag.createPostTag(post, tag);
				postTagRepository.save(postTag);
			}
		}
	}
	
	public void updateTag(String tagname, Post post) {
		postTagRepository.deleteposttagbypostid(post.getId());
		
		String tag1 = tagname.trim();
		String[] taglist = tag1.split("#");
		
		for(int i=1; i<taglist.length; i++) {
			try {
				Tag tag = tagRepository.findByTagname(taglist[i]).orElseThrow(EntityNotFoundException::new);
				PostTag postTag = PostTag.createPostTag(post, tag);
				postTagRepository.save(postTag);
				
			} catch (EntityNotFoundException e) {
				Tag tag = Tag.createTag(taglist[i]);
				tagRepository.save(tag);
				PostTag postTag = PostTag.createPostTag(post, tag);
				postTagRepository.save(postTag);
			}
		}
	}
	
	public List<Tag> findbypostid(Long postid){
		return tagRepository.findBypostid(postid);
	}
	
	public List<Tag> getTagnamebycount(Long memberid){
		return tagRepository.getTagnamebycount(memberid);
	}
	
	public List<Tag> findtagsbypostid(Long memberid){
		return tagRepository.findtagsbypostid(memberid);
	}
	
	public List<String> returntagsleast2times(){
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());	
		
		// GET TAGS WHAT USER USED LEAST 2 TIMES.
		List<Tag> tag = tagRepository.getTagnamebycount(member.getId());
		List<String> tagnamelist = new ArrayList<>();
		for(int i = 0; i<tag.size(); i++ ) {
			 tagnamelist.add(tag.get(i).getTagname());
		}
		List<String> enoughtagname = new ArrayList<>();
		for(int i=0; i<tagnamelist.size(); i++) {
			int count = 0;
			for(int j=0; j<tagnamelist.size(); j++) {
				if(tagnamelist.get(i) == tagnamelist.get(j)) {
					count ++;					
				}
			}
			if(count > 1) {
				enoughtagname.add(tagnamelist.get(i));
			}
		}
		List<String> finaltag = new ArrayList<>();
		for(String test : enoughtagname) {
			if(!finaltag.contains(test)) {
				finaltag.add(test);				
			}
		}
		return finaltag;
	}
}
