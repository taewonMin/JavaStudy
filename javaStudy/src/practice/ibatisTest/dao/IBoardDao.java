package practice.ibatisTest.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import practice.ibatisTest.vo.BoardVO;


public interface IBoardDao {
	
	/**
	 * 게시판 글 추가 메서드
	 * @param smc SqlMapClient 객체
	 * @param bv 추가할 글의 모든 정보
	 * @return 추가에 성공하면 1, 아니면 0 반환
	 * @throws SQLException JDBC관련 예외객체 
	 */
	public int insertBoard(SqlMapClient smc, BoardVO bv) throws SQLException;
	
	/**
	 * 게시판 전체 글 조회 메서드
	 * @param smc SqlMapClient 객체
	 * @return 게시판의 모든 글을 list로 반환
	 * @throws SQLException JDBC관련 예외객체 
	 */
	public List<BoardVO> displayAll(SqlMapClient smc) throws SQLException;
	
	/**
	 * 게시판의 글 검색 메서드
	 * @param smc SqlMapClient 객체
	 * @param search 검색할 글의 내용
	 * @return 해당 내용을 포함한 글들을 list로 반환
	 * @throws SQLException JDBC관련 예외객체 
	 */
	public List<BoardVO> searchBoard(SqlMapClient smc, String search) throws SQLException;
	
	/**
	 * 게시판 글 업데이트 메서드
	 * @param smc SqlMapClient 객체
	 * @param bv 업데이트할 글의 모든 정보
	 * @return 업데이트에 성공하면 1, 아니면 0 반환
	 * @throws SQLException JDBC관련 예외객체 
	 */
	public int updateBoard(SqlMapClient smc, BoardVO bv) throws SQLException;
	
	/**
	 * 게시판 글 삭제 메서드
	 * @param smc SqlMapClient 객체
	 * @param boardNo 삭제할 글의 번호
	 * @return 삭제에 성공하면 1, 아니면 0 반환
	 * @throws SQLException JDBC관련 예외객체 
	 */
	public int deleteBoard(SqlMapClient smc, int boardNo) throws SQLException;
}
