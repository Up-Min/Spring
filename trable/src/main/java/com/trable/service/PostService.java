package com.trable.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.trable.repository.PostImgRepository;
import com.trable.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

	private final PostRepository postRepository;
	private final PostImgRepository postImgRepository;
	
	
	
}
