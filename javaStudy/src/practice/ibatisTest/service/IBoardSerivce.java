package practice.ibatisTest.service;

import java.util.List;

import practice.ibatisTest.vo.BoardVO;


public interface IBoardSerivce {
	
	/**
	 * 게시판 글 추가 메서드
	 * @param bv 추가할 글의 모든 정보
	 * @return 추가에 성공하면 1, 아니면 0 반환
	 */
	public int insertBoard(BoardVO bv);
	
	/**
	 * 게시판 전체 글 조회 메서드
	 * @return 게시판의 모든 글을 list로 반환
	 */
	public List<BoardVO> displayAll();
	
	/**
	 * 게시판의 글 검색 메서드
	 * @param search 검색할 글의 내용
	 * @return 해당 내용을 포함한 글들을 list로 반환
	 */
	public List<BoardVO> searchBoard(String search);
	
	/**
	 * 게시판 글 업데이트 메서드
	 * @param bv 업데이트할 글의 모든 정보
	 * @return 업데이트에 성공하면 1, 아니면 0 반환
	 */
	public int updateBoard(BoardVO bv);
	
	/**
	 * 게시판 글 삭제 메서드
	 * @param boardNo 삭제할 글의 번호
	 * @return 삭제에 성공하면 1, 아니면 0 반환
	 */
	public int deleteBoard(int boardNo);
}
