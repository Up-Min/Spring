package com.trable.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.trable.entity.BlockMembers;
import com.trable.entity.BlockTags;
import com.trable.entity.Member;
import com.trable.repository.BlockMembersRepository;
import com.trable.repository.BlockTagsRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BlockService {

	private final BlockMembersRepository blockMembersRepository;
	private final BlockTagsRepository blockTagsRepository;
	
	public BlockMembers createBlockMember(Member member, String membername) {
		BlockMembers blockmember = BlockMembers.createBlockMember(member, membername);
		return blockMembersRepository.save(blockmember);
	}
	
	public BlockTags createBlockTag(Member member, String tagname) {
		BlockTags blocktag = BlockTags.createBlockTag(member, tagname);
		return blockTagsRepository.save(blocktag);
	}
	
	public void deleteBlockMember(Long memberid) {
		List<BlockMembers> list = blockMembersRepository.findbmbymemid(memberid);
		if(list.size()>0) {
			for(BlockMembers lis : list) {
				blockMembersRepository.delete(lis);
			}			
		}
	}
	public void deleteBlockTag(Long memberid) {
	 List<BlockTags> list = blockTagsRepository.findbymemid(memberid);
	 if(list.size()>0) {
		 for(BlockTags lis : list) {
			 blockTagsRepository.delete(lis);
		 }		 
	 }
	}
	
	
	
	public BlockMembers getblockMember(Long memberid) {
		return blockMembersRepository.findById(memberid).orElseThrow(EntityNotFoundException::new);
	}
	public BlockTags getblockTag(Long memberid) {
		return blockTagsRepository.findById(memberid).orElseThrow(EntityNotFoundException::new);
	}
	public List<BlockMembers> getblkmem(Long memberid) {
		return blockMembersRepository.findbmbymemid(memberid);
	}
	public List<BlockTags> getblktag(Long memberid){
		return blockTagsRepository.findbymemid(memberid);
	}
	
}
