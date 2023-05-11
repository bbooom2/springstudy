package com.gdu.app11.util;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Matcher;

import org.springframework.stereotype.Component;

@Component // 등록해놓으면 @Autowired private MyFileUtil myFileUTil이 가능해짐.
public class MyFileUtil {
	// 이스케이프 역슬래시가 문제되는데 이걸 해결하기 위해서 Matcher.quoteReplacement(File.separator) 사용 
	// 폴더 하나에 다 저장하기에는 관리가 힘들기 때문에 매일 저장할 폴더를 서버 안에 새로 만들 것. 
	// 경로 구분자 
	private String sep = Matcher.quoteReplacement(File.separator); // 경로 구분하는 걸 자바가 기억하는 방식 
	
	// String path(저장경로) 만들기 
	public String getPath() {
		LocalDate now = LocalDate.now(); //날짜기준
		// 루트/storage/2023/05/08 이런식으로 저장될 예정. 서버는 리눅스를 사용할 거라 서버에 올라가면 스토리지 폴더가 바로 보일 것. 
		return "/storage" + sep + now.getYear() + sep + String.format("%02d", now.getMonthValue()) + sep + String.format("%02d", now.getDayOfMonth());
	}
	
	// String filesystemName 만들기 
	public String getFilesystemName(String originName) { // 원래 이름의 확장자명은 알아야 하므로 
		
		// 원래 첨부 파일의 확장자 꺼내기 
		String extName = null;
		
		// 확장자에 마침표(.)가 포함된 예외 상황 처리 
		if(originName.endsWith("tar.gz")) { //리눅스 압축파일 확장자 
			extName = "tar.gz";
		} else {
		
			// split(정규식) 
			// 정규식에서 마침표(.)는 모든 문자를 의미하므로 이스케이프 처리하거나 문자 클래스로 처리한다. 
			// 이스케이프 처리 : \.
			// 문자클래스 처리 : [.]
			String[] array = originName.split("\\."); // 자바에서 역슬래시 넣는 법은 역슬래시 2개
			extName = array[array.length-1]; //마지막 인덱스 
		}
		
		// 결과 반환 
		// UUID.extName
		// 이름에 중복이 없어야 함. 
		return UUID.randomUUID().toString().replace("-", "") + "." + extName; // 결과에 모든 하이픈을 없앰. DB에 들어가면 공간차지만 함. 3개 정도 들어갈 텐데 이게 다 빠지게 됨. 
																			  // 랜덤값으로 나온 이름 뒤에 extName -> 확장자를 붙임 	
	}

	// String tempPath 만들기 
	public String getTempPath() {
		return "/storage" + sep + "temp";
	}
	
	// String tempfileName 만들기 (zip 파일) 
	public String getTempfileName() {
		return UUID.randomUUID().toString().replace("-", "") + ".zip";
	}
	
	// String yesterdayPath 만들기 
	public String getYesterdayPath() {
		LocalDate date = LocalDate.now();
		date.minusDays(1); //1일 전 
		return "/storage" + sep + date.getYear() + sep + String.format("%02d", date.getMonthValue()) + sep + String.format("%02d", date.getDayOfMonth());
		
	
	}
	
}
