<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- UTF-8은 요즘 가장 많이 쓰이는 대표적인 형식 (한글,영어) 둘다 지원됨 -->
    <%@ page import="bbs.BbsDAO" %>
    <%@ page import="java.io.PrintWriter" %>
    <% request.setCharacterEncoding("UTF-8"); %> <!-- request로 받는 것들을 다 UTF-8로 바꾸어줌  -->
    <jsp:useBean id="bbs" class="bbs.Bbs" scope="page"/> <!-- 회원가입에서는 5가지의 내용들이 다 사용되기때문에 다 user로 받아준다.  -->
    <jsp:setProperty name="bbs" property="bbsTitle"/>
    <jsp:setProperty name="bbs" property="bbsContent"/>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 게시판 웹사이트 </title>
</head>
<body>
   <% 
   	String userID = null;
   	if(session.getAttribute("userID") != null){
 	  	userID = (String)session.getAttribute("userID");
  	 }
 	  if(userID == null) {
 		 PrintWriter script = response.getWriter();
       	script.println("<script>");
       	script.println("alert('로그인을 하세요')");
       	script.println("location.href = 'login.jsp'");
       	script.println("</script>");
   	} else{
   		if(bbs.getBbsTitle() == null || bbs.getBbsContent() ==null){
   		   	PrintWriter script = response.getWriter();
   	       	script.println("<script>");
   	       	script.println("alert('입력이 안 된 사항이 있습니다.')");
   	       	script.println("history back()");
   	       	script.println("</script>");
   	   	} else {
   		    BbsDAO bbsDAO = new BbsDAO(); //하나의 BbsDAO생성해준다. 
   		      int result = bbsDAO.write(bbs.getBbsTitle(),userID,bbs.getBbsContent());
   		      if(result== -1){ //실패하는 경우 이미 있는 아이디일 경우 실패한다.  
   		         PrintWriter script = response.getWriter();
   		         script.println("<script>");
   		         script.println("alert('글쓰기에 실패 했습니다.')");
   		         script.println("history.back()"); // 이전페이지로 다시 돌아가는 부분 
   		         script.println("</script>");
   		      } 
   		      else {
   		        	 PrintWriter script = response.getWriter();
   		        	 script.println("<script>");
   		        	 script.println("alert('글쓰기 성공')");
   		         	 script.println("location.href = 'bbs.jsp'");
   		         	script.println("</script>");
   		      } //회원가입이 된 경우 로그인 페이지로 이동한다. 
   	}
   	
  	 }
	   
   %>
</body>
</html>