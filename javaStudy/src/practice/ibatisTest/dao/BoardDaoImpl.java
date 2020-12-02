package practice.ibatisTest.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import practice.ibatisTest.vo.BoardVO;


public class BoardDaoImpl implements IBoardDao{
	
	private static BoardDaoImpl boardDao;

	private BoardDaoImpl() {
		
	}
	
	public static BoardDaoImpl getInstance() {
		if(boardDao == null) {
			boardDao = new BoardDaoImpl();
		}
		return boardDao;
	}

	@Override
	public int insertBoard(SqlMapClient smc, BoardVO bv) throws SQLException {
		int chk = 0;
		
		Object obj = smc.insert("board.insertBoard", bv);
		if(obj == null) {
			chk = 1;
		}
		
		return chk;
	}

	@Override
	public List<BoardVO> displayAll(SqlMapClient smc) throws SQLException {
		List<BoardVO> boardList = null;
		
		boardList = smc.queryForList("board.getAllBoard");
		
		return boardList;
	}

	public List<BoardVO> searchBoard(SqlMapClient smc, String search) throws SQLException {
		List<BoardVO> boardList = null;
	
		boardList = smc.queryForList("board.searchBoard", search);
		
		return boardList ;
	}

	@Override
	public int updateBoard(SqlMapClient smc, BoardVO bv) throws SQLException {
		int chk = 0;
		
		chk = smc.update("board.updateBoard", bv);
		
		return chk;
	}

	@Override
	public int deleteBoard(SqlMapClient smc, int boardNo) throws SQLException {
		int chk = 0;
		
		chk = smc.delete("board.deleteBoard", boardNo);
		return chk;
	}
}
