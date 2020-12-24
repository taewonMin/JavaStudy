package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 서블릿 라이프사이클을 확인하기 위한 예제
 * (서블릿이란? 컨테이너(서블릿 엔진)에 의해 관리되는 자바기반 웹 컴포넌트로써,
 *  동적인 웹컨텐츠 생성을 가능하게 해준다.)
 */
public class T01_ServletLifeCycle extends HttpServlet {
	
	@Override
	public void init() throws ServletException {
		// 초기화 코드 작성
		System.out.println("init() 호출됨...");
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 실제적인 작업 수행이 시작되는 지점(자바의 메인메서드 역할)
		System.out.println("service() 호출됨...");
		super.service(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 메서드 방식이 get인 경우 호출됨
		System.out.println("doGet() 호출됨...");
		
		throw new ServletException();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 메서드 방식이 post인 경우 호출됨
		System.out.println("doPost() 호출됨...");
		
		doGet(req, resp);
	}
	
	@Override
	public void destroy() {
		
		// 객체 소멸시(컨테이너로부터 서블릿객체 제거시) 필요한 코드 작성
		System.out.println("destroy() 호출됨...");
		
	}
}
