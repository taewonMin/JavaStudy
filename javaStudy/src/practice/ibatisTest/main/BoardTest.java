package practice.ibatisTest.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import practice.ibatisTest.service.BoardServiceImpl;
import practice.ibatisTest.service.IBoardSerivce;
import practice.ibatisTest.vo.BoardVO;


/*
위의 테이블을 작성하고 게시판을 관리하는
다음 기능들을 구현하시오.

기능 구현하기 ==> 전체 목록 출력, 새글작성, 수정, 삭제, 검색 
 
------------------------------------------------------------

게시판 테이블 구조 및 시퀀스

create table jdbc_board(
    board_no number not null,  -- 번호(자동증가)
    board_title varchar2(100) not null, -- 제목
    board_writer varchar2(50) not null, -- 작성자
    board_date date not null,   -- 작성날짜
    board_content clob,     -- 내용
    constraint pk_jdbc_board primary key (board_no)
);
create sequence board_seq
    start with 1   -- 시작번호
    increment by 1; -- 증가값
		
----------------------------------------------------------

// 시퀀스의 다음 값 구하기
//  시퀀스이름.nextVal

 */
public class BoardTest {
	private IBoardSerivce bs = BoardServiceImpl.getInstance();
	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		BoardTest bt = new BoardTest();
		bt.start();
	}
	
	private void printBanner(String str) {
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("\t\t"+str);
		System.out.println("-----------------------------------");
	}

	private void selectWork() {
		System.out.println("1. 전체 게시판 목록 출력");
		System.out.println("2. 새 글 작성");
		System.out.println("3. 글 수정");
		System.out.println("4. 글 삭제");
		System.out.println("5. 글 검색");
		System.out.println("0. 종료");
	}
	private void start() {
		while(true) {
			printBanner("게시판");
			selectWork();
			System.out.print("메뉴를 선택하세요 => ");
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			switch (input) {
			case 1:
				displayAll();
				break;
			case 2:
				createPost();
				break;
			case 3:
				updatePost();
				break;
			case 4:
				deletePost();
				break;
			case 5:
				searchPost();
				break;
			case 0:
				endProgram();
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	/**
	 * 전체 게시글 조회 메서드
	 */
	private void displayAll() {
		printBanner("글 목록");
		
		List<BoardVO> boardList = bs.displayAll();
		if(boardList.size()==0) {
			System.out.println("등록된 글이 없습니다.");
		}else {
			int cnt = 1;
			for(BoardVO bv : boardList) {
				System.out.println("["+ cnt++ + "] " + bv.getBoardTitle());
				System.out.println("작성자: " + bv.getBoardWriter());
				System.out.println();
				System.out.println(bv.getBoardContent());
				System.out.println();
				System.out.println("------------------------");
			}
		}
	}
	
	/**
	 * 게시글 등록 메서드
	 */
	private void createPost() {
		printBanner("글 등록");
		System.out.print("작성자 이름을 입력하세요. => ");
		String writer = sc.next();
		
		sc.nextLine();
		System.out.println("글 제목을 입력하세요.");
		String title = sc.nextLine();
		
		System.out.println("글 내용을 입력하세요.");
		String content = sc.nextLine();

		BoardVO bv = new BoardVO();
		bv.setBoardWriter(writer);
		bv.setBoardTitle(title);
		bv.setBoardContent(content);
		
		int chk = bs.insertBoard(bv);
		
		if(chk > 0) {
			System.out.println("\n글이 등록되었습니다.");
		}else {
			System.out.println("\n글 등록 실패..");
		}
	}

	/**
	 * 글 수정 메서드
	 */
	private void updatePost() {
		displayAll();
		System.out.print("수정할 글 번호를 입력하세요 => ");
		int postNum  = 0;
		while(true) {
			try {
				postNum = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc.nextLine();
				continue;
			}
			break;
		}
		sc.nextLine();
		System.out.println("글 제목을 입력하세요.");
		String title = sc.nextLine();
		
		System.out.println("글 내용을 입력하세요.");
		String content = sc.nextLine();
		
		BoardVO bv = new BoardVO();
		bv.setBoardNo(postNum);
		bv.setBoardTitle(title);
		bv.setBoardContent(content);
		
		int chk = bs.updateBoard(bv);
		
		if(chk > 0) {
			System.out.println("\n글이 수정되었습니다.");
		}else {
			System.out.println("\n수정 실패..");
		}
	}

	/**
	 * 글 삭제 메서드
	 */
	private void deletePost() {
		displayAll();
		System.out.print("삭제할 글 번호를 입력하세요 => ");
		int postNum  = 0;
		while(true) {
			try {
				postNum = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc.nextLine();
				continue;
			}
			break;
		}
		
		int chk = bs.deleteBoard(postNum);
		
		if(chk > 0) {
			System.out.println("\n글이 삭제되었습니다.");
		}else {
			System.out.println("\n삭제 실패..");
		}
	}

	/**
	 * 글 검색 메서드
	 */
	private void searchPost() {
		System.out.println("검색할 내용을 입력하세요.(제목 + 내용)");
		sc.nextLine();
		String search = sc.nextLine();
		
		List<BoardVO> searchList = bs.searchBoard(search);
		
		if(searchList.size()==0) {
			System.out.println("\n검색 결과가 없습니다.");
		}else {
			printBanner("검색 결과");
			int cnt=1;
			for(BoardVO bv : searchList) {
				System.out.println("["+ cnt++ + "] " + bv.getBoardTitle());
				System.out.println("작성자: " + bv.getBoardWriter());
				System.out.println();
				System.out.println(bv.getBoardContent());
				System.out.println();
				System.out.println("------------------------");
			}
		}
	}

	/**
	 * 프로그램 종료 메서드
	 */
	private void endProgram() {
		System.out.println("\n프로그램을 종료합니다.");
		sc.close();
		System.exit(0);
	}
}
