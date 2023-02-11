package com.trable.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.trable.entity.Post;
import com.trable.entity.PostTag;
import com.trable.entity.Tag;
import com.trable.repository.PostTagRepository;
import com.trable.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
	
	private final TagRepository tagRepository;
	private final PostTagRepository postTagRepository;
	
	
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
	
	public List<Tag> findbypostid(Long postid){
		return tagRepository.findBypostid(postid);
	}
	
	public List<Tag> getTagnamebycount(Long memberid){
		return tagRepository.getTagnamebycount(memberid);
	}
	
	
}
