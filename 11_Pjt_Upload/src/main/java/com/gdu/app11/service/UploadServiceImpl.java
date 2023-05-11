package com.gdu.app11.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app11.domain.AttachDTO;
import com.gdu.app11.domain.UploadDTO;
import com.gdu.app11.mapper.UploadMapper;
import com.gdu.app11.util.MyFileUtil;
import com.gdu.app11.util.PageUtil;

import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@Service
@AllArgsConstructor // field @Autowired 처리 
public class UploadServiceImpl implements UploadService {
	
	/// field
	private UploadMapper uploadMapper;
	private MyFileUtil myFileUtil;
	private PageUtil pageUtil;
	

	@Override
	public void getUploadList(HttpServletRequest request, Model model)  {
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		int uploadCount = uploadMapper.getUploadCount();
		
		int recordPerPage = 10;
		
		pageUtil.setPageUtil(page, uploadCount, recordPerPage);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		List<UploadDTO> uploadList = uploadMapper.getUploadList(map);
		model.addAttribute("uploadList", uploadList);
		model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/upload/list.do"));
		
	}

	
	@Transactional(readOnly = true) //(readOnly = true) 성능향상을 위해 사용
								    // INSERT문을 2개 이상 수행하기 때문에 트랜잭션 처리가 필요하다.
	@Override
	public int addUpload(MultipartHttpServletRequest multipartRequest) {
		
		/* Upload 테이블에 UploadDTO 넣기*/
		
		// 제목, 내용 파라미터 (multipartRequest는 파일 첨부 기능이 있는 request이기 때문에 일반적인 request를 사용할 때와 동일하게 사용) 
		String uploadTitle = multipartRequest.getParameter("uploadTitle");
		String uploadContent = multipartRequest.getParameter("uploadContent");
		
		// DB로 보낼 UploadDTO 만들기 
		UploadDTO uploadDTO = new UploadDTO();
		uploadDTO.setUploadTitle(uploadTitle);
		uploadDTO.setUploadContent(uploadContent);
		
		// DB로 UploadDTO 보내기 
		int uploadResult = uploadMapper.addUpload(uploadDTO); // <selectKey>에 의해서 uploadDTO 객체의 uploadNo 필드에 UPLOAD_SEQ.NEXTVAL 값이 저장된다.
		
		/* Attach 테이블에 AttachDTO 넣기 (AttachDTO는 uploadNo가 필요함 / 이걸 해결하기 위해서 2교시에 본적없는 코드 사용할 예정)*/
		
		// 첨부된 파일 목록 
		// 첨부에 들어간 name이 files이라 넘어오는 것.  
		List<MultipartFile> files = multipartRequest.getFiles("files"); // <input type="file" name="files"
		
		// 첨부된 파일이 있는지 체크 
		if(files != null && files.isEmpty() == false) {
			
			// 첨부된 파일 목록 순회 
			for(MultipartFile multipartFile : files) {
				
				// 예외 처리 
				try {
					
					/* HDD에 첨부 파일 저장하기 */
					
					// 첨부 파일의 저장 경로 
					String path = myFileUtil.getPath();
					
					// 첨부 파일의 저장 경로가 없으면 만들기 
					File dir = new File(path);
					if(dir.exists() == false ) {
						dir.mkdirs();
					}
					// 첨부 파일의 원래 이름 
					String originName = multipartFile.getOriginalFilename();
					// 이름이 -\-\ 이런식으로 경로를 다 가지고 오고 마지막으로 파일명을 가져오는 브라우저가 있음 
					// 대표적인게 인터넷 익스플로러 
					// 마지막 \ 만 찾아서 그 뒤에 거 가져오게 하는 코드임. 
					originName= originName.substring(originName.lastIndexOf("\\") + 1); // IE는 전체 경로가 오기 때문에 마지막 역슬래시 뒤에 있는 파일명만 사용한다.
					
					// 첨부 파일의 저장 이름 
					String filesystemName = myFileUtil.getFilesystemName(originName);
					
					// 첨부 파일의 File 객체 (HDD에 저장할 첨부 파일) 
					File file = new File(dir, filesystemName);
					
					// 첨부 파일을 HDD에 저장
					multipartFile.transferTo(file); // 실제로 서버에 저장된다. 
					
					/* 썸네일(첨부 파일이 이미지인 경우에만 썸네일이 가능) */
					// 첨부파일의 확장자를 알아야 함 
					
					// 첨부 파일의 Content-Type 확인 
					String contentType = Files.probeContentType(file.toPath()); // 첨부파일의 파일객체가 나와있기 때문에 해당 path값을 쉽게 알아낼 수 있다 // 이미지 파일의 Content-Type : image/jpeg, image/png, image/gif, ...
					
					// DB에 썸네일 유무 정보 처리 
					boolean hasThumbnail = contentType != null && contentType.startsWith("image");
					
					// 첨부 파일의 Content-Type이 이미지로 확인되면 썸네일을 만듦
				 
					if(hasThumbnail) {
						
						// HDD에 썸네일 저장하기 (thumbnailator 디펜던시 사용)
						// 원래 이름 앞에 s_(작은이미지라는 뜻으로 우리만의 규칙을 정해놓음) 같은폴더에 s_uuid.jpg 이런식으로 이름 구분해서 outfile 저장 
						File thumbnail = (new File(dir, "s_" + filesystemName));
						Thumbnails.of(file)
								  .size(50, 50)
								  .toFile(thumbnail);

					}
					
					/* DB에 첨부 파일 정보 저장하기 */
					
					// DB로 보낼 AttachDTO 만들기 
					AttachDTO attachDTO = new AttachDTO();
					attachDTO.setFilesystemName(filesystemName);
					attachDTO.setHasThumbnail(hasThumbnail ? 1 : 0); //인트 대신 이렇게 가겠음 
					attachDTO.setOriginName(originName);
					attachDTO.setPath(path);
					attachDTO.setUploadNo(uploadDTO.getUploadNo());
					
					// DB로 AttachDTO 보내기
					uploadMapper.addAttach(attachDTO);
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		
		}
		return uploadResult;
	}

	@Override
	public void getUploadByNo(int uploadNo, Model model) {
		model.addAttribute("upload", uploadMapper.getUploadByNo(uploadNo));
		model.addAttribute("attachList", uploadMapper.getAttachList(uploadNo));
		
	}
	
	@Override
	public ResponseEntity<byte[]> display(int attachNo) {
		AttachDTO attachDTO = uploadMapper.getAttachByNo(attachNo);
		ResponseEntity<byte[]> image = null; 
		
		try {
			
			File thumnail = new File(attachDTO.getPath(), "s_" + attachDTO.getFilesystemName());
			image = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(thumnail), HttpStatus.OK); //썸네일이미지를바이트배열로바꾼거
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	@Override
	public ResponseEntity<Resource> download(int attachNo, String userAgent) {
		// 다운로드 할 첨부 파일의 정보(경로, 원래 이름, 저장된 이름) 가져오기
		AttachDTO attachDTO = uploadMapper.getAttachByNo(attachNo);
		
		// 다운로드 할 첨부 파일의 File 객체 -> Resource 객체 
		File file = new File(attachDTO.getPath(), attachDTO.getFilesystemName());
		Resource resource = new FileSystemResource(file);
		
		// 다운로드 할 첨부 파일의 존재 여부 확인(없으면 다운로드 실패를 반환) 
		// 응답코드에 에러메시지 실어서 보냄 
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		// 다운로드 횟수 증가하기 
		uploadMapper.increaseDownloadCount(attachNo);
		
		// 다운로드 되는 파일명(첨부파일의 원래 이름, UserAgent(브라우저)에 따른 인코딩 세팅 
		String originName = attachDTO.getOriginName();
		try { 
			
			// IE (UserAgent에 Trident가 포함되어 있다.)
			if(userAgent.contains("Trident")) {
				originName = URLEncoder.encode(originName, "UTF-8").replace("+", " ");
			}
			
			// Edge (UserAgent에 Edg가 포함되어 있다.)
			else if(userAgent.contains("Edg")) {
				originName = URLEncoder.encode(originName, "UTF-8");
			}
			
			// Other
			else {
				originName = new String(originName.getBytes("UTF-8"), "ISO-8859-1"); // 확장 아스키 코드 
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		// 다운로드 응답 헤더 만들기 (Jsp/Servlet 코드)
		/*
		MultiValueMap<String, String> responseHeader = new HttpHeaders();
		responseHeader.add("Content-Type", "application/octet-stream");
		responseHeader.add("Content-Disposition", "attachment; filename=" + originName);
		responseHeader.add("Content-Length", file.length() + "");
		*/
		
		// 다운로드 응답 헤더 만들기 (Spring 코드)
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		responseHeader.setContentDisposition(ContentDisposition
													.attachment()
													.filename(originName)
													.build());
		responseHeader.setContentLength(file.length());
		
		// 응답 
		return new ResponseEntity<Resource>(resource, responseHeader, HttpStatus.OK);
		
	
 } 
	
	@Override
	public ResponseEntity<Resource> downloadAll(int uploadNo) {
		
		// 모든 첨부 파일을 zip 파일로 다운로드하는 서비스 
		// 압축파일은 서버한테 임시파일 정도밖에 안됨 압축을 해서 다운로드하려면 서버가 압축된 파일을 가지고 있어야 한다. 일단은 압축을해서 서버가 가지고 있어야 하고 그걸 다운로드 코드처럼 처리하면 됨
		// com.gdu.app11.batch RemoveTempfileScheduler에 의해서 주기적으로 zip 파일들은 삭제된다. 
		
		// zip 파일이 저장될 경로 
		String tempPath = myFileUtil.getTempPath();
		File dir = new File(tempPath);
		if(dir.exists() == false) {
			dir.mkdirs(); // 없으면 만들어라 
		}
		
		// zip 파일의 이름
		String tempfileName = myFileUtil.getTempfileName();
		
		// zip 파일의 File 객체
		File zfile = new File(tempPath, tempfileName);
		
		// zip 파일을 생성하기 위한 Java IO Stream 선언 
		BufferedInputStream bin = null; // 각 첨부 파일을 읽어 들이는 스트림 
		ZipOutputStream zout = null;    // zip 파일을 만드는 스트림 
		
		// 다운로드 할 첨부 파일들의 정보(경로, 원래 이름, 저장된 이름) 가져오기
		List<AttachDTO> attachList = uploadMapper.getAttachList(uploadNo);
		
		try {
			
			// ZipOutputStream zout 객체 생성
			zout = new ZipOutputStream(new FileOutputStream(zfile));
			
			// 첨부 파일들을 하나씩 순회하면서 읽어 들인 뒤 zip 파일에 추가하기 + 각 첨부 파일들의 다운로드 횟수 증가 
			for(AttachDTO attachDTO : attachList) {
				
				// zip 파일에 추가할 첨부 파일 이름 등록(첨부 파일의 원래 이름) 
				ZipEntry zipEntry = new ZipEntry(attachDTO.getOriginName());
				zout.putNextEntry(zipEntry);
				
				// zip 파일에 첨부 파일 추가 
				bin = new BufferedInputStream(new FileInputStream(new File(attachDTO.getPath(), attachDTO.getFilesystemName())));
				
			
	
				// bin -> zout으로 파일 복사하기 (Java 코드)
				byte[] b = new byte[1024]; // 첨부 파일을 1KB 단위로 읽겠다. 하지만 실제로 읽어들인 게 1024가 아닐 수 있으니 readByte 사용  
				int readByte = 0; 		   // 실제로 읽어 들인 바이트 수 
				while ((readByte = bin.read(b)) != -1) {
					zout.write(b, 0, readByte);
				}
				bin.close();
				zout.closeEntry();
				

				
				// 각 첨부 파일들의 다운로드 횟수 증가 
				uploadMapper.increaseDownloadCount(attachDTO.getAttachNo());
			}
			zout.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 다운로드 할 zip 파일의 File 객체 -> Resource 객체 
		Resource resource = new FileSystemResource(zfile);
		
		// 다운로드 응답 헤더 만들기 
		/*
		MultiValueMap<String, String> responseHeader = new HttpHeaders();
		responseHeader.add("Content-Disposition", "attachment; filename=" + tempfileName);
		responseHeader.add("Content-Length", zfile.length() + "");
		*/
		
		// 다운로드 응답 헤더 만들기 (Spring 코드)
				HttpHeaders responseHeader = new HttpHeaders();
				responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				responseHeader.setContentDisposition(ContentDisposition
													.attachment()
													.filename(tempfileName)
													.build());
				responseHeader.setContentLength(zfile.length());
		
		// 응답 
		return new ResponseEntity<Resource>(resource, responseHeader, HttpStatus.OK);
		
	}

	@Override
	public int removeUpload(int uploadNo) {
		// 삭제할 첨부 파일들의 정보 
		List<AttachDTO> attachList = uploadMapper.getAttachList(uploadNo);
		
		// 첨부 파일이 있으면 삭제 
		if(attachList != null && attachList.isEmpty() == false) {
			
			// 삭제할 첨부 파일들을 순회하면서 하나씩 삭제 
			for(AttachDTO attachDTO : attachList) {
				
				// 삭제할 첨부 파일의 File 객체 
				File file = new File(attachDTO.getPath(), attachDTO.getFilesystemName());
				
				// 첨부 파일 삭제 
				if(file.exists()) {
					file.delete();
;				}
				
				// 첨부 파일이 이미지 파일이라면 "s_"로 시작하는 썸네일이 함께 존재하므로 함께 제거해야 한다. 
				if(attachDTO.getHasThumbnail() == 1) {
					
					// 삭제할 썸네일의 File 객체 
					File thumbnail = new File(attachDTO.getPath(), "s_" + attachDTO.getFilesystemName());
					
					// 썸네일 삭제 
					if(thumbnail.exists()) {
						thumbnail.delete();
					}
				}
			}
		}
		// DB에서 uploadNo 값을 가지는 데이터를 삭제 
		// 외래키 제약조건에 의해서 (ON DELETE CASCADE) UPLOAD 테이블의 데이터가 삭제되면 
		// ATTACH 테이블의 데이터도 함께 삭제된다. 
		int removeResult = uploadMapper.removeUpload(uploadNo);
		return removeResult;
	}
	
	@Transactional(readOnly = true)  // INSERT문을 2개 이상 수행하기 때문에 트랜잭션 처리가 필요하다.
	@Override
	public int modifyUpload(MultipartHttpServletRequest multipartRequest) {
		
		/* Upload 테이블의 정보 수정하기 */
		
		// 제목, 내용, 업로드번호 파라미터
		String uploadTitle = multipartRequest.getParameter("uploadTitle");
		String uploadContent = multipartRequest.getParameter("uploadContent");
		int uploadNo = Integer.parseInt(multipartRequest.getParameter("uploadNo"));
		
		// DB로 보낼 UploadDTO 만들기
		UploadDTO uploadDTO = new UploadDTO();
		uploadDTO.setUploadTitle(uploadTitle);
		uploadDTO.setUploadContent(uploadContent);
		uploadDTO.setUploadNo(uploadNo);
		
		// DB로 UploadDTO 보내기
		int modifyResult = uploadMapper.modifyUpload(uploadDTO);
		
		/* Attach 테이블에 AttachDTO 넣기 */
		
		// 첨부된 파일 목록
		List<MultipartFile> files = multipartRequest.getFiles("files");  // <input type="file" name="files">

		// 첨부가 없는 경우에도 files 리스트는 비어 있지 않고,
		// [MultipartFile[field="files", filename=, contentType=application/octet-stream, size=0]] 형식으로 MultipartFile을 하나 가진 것으로 처리된다.
		
		// 첨부된 파일 목록 순회
		for(MultipartFile multipartFile : files) {
			
			// 첨부된 파일이 있는지 체크
			if(multipartFile != null && multipartFile.isEmpty() == false) {
				
				// 예외 처리
				try {
					
					/* HDD에 첨부 파일 저장하기 */
					
					// 첨부 파일의 저장 경로
					String path = myFileUtil.getPath();
					
					// 첨부 파일의 저장 경로가 없으면 만들기
					File dir = new File(path);
					if(dir.exists() == false) {
						dir.mkdirs();
					}
					
					// 첨부 파일의 원래 이름
					String originName = multipartFile.getOriginalFilename();
					originName = originName.substring(originName.lastIndexOf("\\") + 1);  // IE는 전체 경로가 오기 때문에 마지막 역슬래시 뒤에 있는 파일명만 사용한다.
					
					// 첨부 파일의 저장 이름
					String filesystemName = myFileUtil.getFilesystemName(originName);
					
					// 첨부 파일의 File 객체 (HDD에 저장할 첨부 파일)
					File file = new File(dir, filesystemName);
					
					// 첨부 파일을 HDD에 저장
					multipartFile.transferTo(file);  // 실제로 서버에 저장된다.
					
					/* 썸네일(첨부 파일이 이미지인 경우에만 썸네일이 가능) */
					
					// 첨부 파일의 Content-Type 확인
					String contentType = Files.probeContentType(file.toPath());  // 이미지 파일의 Content-Type : image/jpeg, image/png, image/gif, ...
					
					// DB에 저장할 썸네일 유무 정보 처리
					boolean hasThumbnail = contentType != null && contentType.startsWith("image");
					
					// 첨부 파일의 Content-Type이 이미지로 확인되면 썸네일을 만듬
					if(hasThumbnail) {
						
						// HDD에 썸네일 저장하기 (thumbnailator 디펜던시 사용)
						File thumbnail = new File(dir, "s_" + filesystemName);
						Thumbnails.of(file)
							.size(50, 50)
							.toFile(thumbnail);
						
					}
					
					/* DB에 첨부 파일 정보 저장하기 */
					
					// DB로 보낼 AttachDTO 만들기
					AttachDTO attachDTO = new AttachDTO();
					attachDTO.setFilesystemName(filesystemName);
					attachDTO.setHasThumbnail(hasThumbnail ? 1 : 0);
					attachDTO.setOriginName(originName);
					attachDTO.setPath(path);
					attachDTO.setUploadNo(uploadDTO.getUploadNo());
					
					// DB로 AttachDTO 보내기
					uploadMapper.addAttach(attachDTO);
					
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
		return modifyResult;
		
	}
	
	
	@Override
	public int removeAttach(int attachNo) {
		
		// 삭제할 첨부 파일의 정보 가져오기
		AttachDTO attachDTO = uploadMapper.getAttachByNo(attachNo);
		
		// 첨부 파일이 있으면 삭제
		if(attachDTO != null) {
			
			// 삭제할 첨부 파일의 File 객체
			File file = new File(attachDTO.getPath(), attachDTO.getFilesystemName());
			
			// 첨부 파일 삭제
			if(file.exists()) {
				file.delete();
			}
			
			// 첨부 파일이 썸네일을 가지고 있다면 "s_"로 시작하는 썸네일이 함께 존재하므로 함께 제거해야 한다.
			if(attachDTO.getHasThumbnail() == 1) {
				
				// 삭제할 썸네일의 File 객체
				File thumbnail = new File(attachDTO.getPath(), "s_" + attachDTO.getFilesystemName());
				
				// 썸네일 삭제
				if(thumbnail.exists()) {
					thumbnail.delete();
				}
					
			}
			
		}

		// DB에서 attachNo값을 가지는 ATTACH 테이블의 데이터를 삭제
		int removeResult = uploadMapper.removeAttach(attachNo);
		
		return removeResult;
		
	}
	
}
