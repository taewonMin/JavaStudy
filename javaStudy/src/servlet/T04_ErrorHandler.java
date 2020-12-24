package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T04_ErrorHandler extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 서블릿 예외 정보 확인
		// 예외 객체
		Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
		// 에러상태코드
		Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
		// 에러 발생한 서블릿 이름
		String servletName = (String) req.getAttribute("javax.servlet.error.servlet_name");
		
		if(servletName == null) {
			servletName = "알수없는 서블릿이름";
		}
		
		// 에러발생 url정보
		String requestUri = (String) req.getAttribute("javax.servlet.error.request_url");
		
		if(requestUri == null) {
			requestUri = "알수없는 URI";
		}
		
		// 응답 결과 생성
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		
		PrintWriter out = resp.getWriter();
		String title = "에러/예외 정보";
		
		out.println("<html>");
		out.println("<head><title>" + title + "</title></head>\n");
		out.println("<body>");
		
		if(throwable == null && statusCode == null) {
			out.println("<h2>에러정보 없음</h2>");
			out.println("홈페이지로 돌아가기: <a href=\"http://localhost/\">홈페이지</a>");
		}else if(statusCode != null) {
			out.println("StatusCode : " + statusCode);
		}else {
			out.println("<h2> 에러 정보 </h2>");
			out.println("서블릿 이름 : " + servletName + "<br><br>");
			out.println("예외 타입 : " + throwable.getClass().getName() +"<br><br>");
			out.println("요청 URI : " + requestUri + "<br><br>");
			out.println("예외 메시지 : " + throwable.getMessage());
		}
		
		out.println("</body>");
		out.println("</html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
