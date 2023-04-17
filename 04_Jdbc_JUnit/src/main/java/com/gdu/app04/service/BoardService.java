package com.gdu.app04.service;

import java.util.List;

import com.gdu.app04.domain.BoardDTO;

public interface BoardService { // 앞으로 인터페이스라고 한들 I 붙이지 않을 예정. 
	// 서비스 계층의 메소드명은 가급적 "사용자 친화적"으로 작성하자.
	public List<BoardDTO> getBoardList(); // 미리 BoardDTO가 만들어져있어야 여기서 임포트 해줄 수 있겠쥬?
	public BoardDTO getBoardByNo(int board_no); // 제이디비씨로 해서 이걸 카멜케이스로 변경할 건 없음. 정말 카멜케이스로 변경하고 싶다면 보드 넘버 셀렉트 가져올때 별명을 붙여서 직접 이름을 수정해서 가져오면 할 수는 있음 그렇지만 권장하지 않는 방법이다. (왜냐하면 별명을 모든 곳에서 사용할 수 있는것은 아니기 때문이다.)번호 전달되어야 함. 상세보기 여기서는 디티오 하나면 됨 
	public int addBoard(BoardDTO board);
	public int modifyBoard(BoardDTO board);
	public int removeBoard(int board_no);
}
