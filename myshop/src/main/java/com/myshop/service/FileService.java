package com.myshop.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class FileService {
	// 파일 업로드
	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
		UUID uuid = UUID.randomUUID(); //중복되지 않은 랜덤한 파일 이름 생성
		
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
				//뒤의 확장자는 빼주기 위해 substring으로 좀 잘라준다.
		String savedFileName = uuid.toString() + extension; // 파일 이름 생성
		String fileUploadFullUrl = uploadPath + "/" + savedFileName; // 절대경로 생성
		
		// 실질적인 파일 업로드 과정 //
		// 생성자에 파일이 저장될 위치와 파일이름을 같이 넘겨서 출력스트림을 만든다.
		FileOutputStream fos = new FileOutputStream(fileUploadFullUrl); 
		fos.write(fileData);// 실질적인 파일은 byte단위로 온다. 출력스트림에 파일 데이터를 입력한다. //실제 파일 출력
		fos.close();
		
		return savedFileName; // 변경된 파일 이름을 return해준다.
	}
	
	//파일 삭제
	public void deleteFile(String filePath) throws Exception{
		File deleteFile = new File(filePath); //파일이 저장된 경로를 이용해서 파일 객체를 생성
		
		if(deleteFile.exists()) { //해당 경로에 파일이 있으면
			deleteFile.delete(); // 파일 삭제
			log.info("파일을 삭제하였습니다."); // 삭제 로그 남김. (롬복 Log annotation)
		}
		else {
			log.info("파일이 존재하지 않습니다.");
		}
	}
}
