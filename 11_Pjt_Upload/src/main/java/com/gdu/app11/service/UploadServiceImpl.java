package com.gdu.app11.service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app11.domain.AttachDTO;
import com.gdu.app11.domain.UploadDTO;
import com.gdu.app11.mapper.UploadMapper;
import com.gdu.app11.util.MyFileUtil;

import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@Service
@AllArgsConstructor // field @Autowired 처리 
public class UploadServiceImpl implements UploadService {
	
	/// field
	private UploadMapper uploadMapper;
	private MyFileUtil myFileUtil;
	
	// 권장사항 : Pagination 처리 해 보기 
	@Override
	public void getUploadList(Model model) {
		List<UploadDTO> uploadList = uploadMapper.getUploadList();
		model.addAttribute("uploadList", uploadList);
		
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
		model.addAttribute("attachList", uploadMapper.getUploadList());
		
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
}

