<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- UTF-8은 요즘 가장 많이 쓰이는 대표적인 형식 (한글,영어) 둘다 지원됨 -->
    <%@ page import="user.UserDAO" %>
    <%@ page import="java.io.PrintWriter" %>
    <% request.setCharacterEncoding("UTF-8"); %> <!-- //request로 받는 것들을 다 UTF-8로 바꾸어줌  -->
    <jsp:useBean id="user" class="user.User" scope="page"/>
    <jsp:setProperty name="user" property="userID"/>
    <jsp:setProperty name="user" property="userPassword"/>
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
      if(userID != null) {
    	  PrintWriter script = response.getWriter();
          script.println("<script>");
          script.println("alert('이미 로그인이 되어있습니다.')");
          script.println("location.href = 'main.jsp'");
          script.println("</script>");
      }
      
      UserDAO userDAO = new UserDAO(); 
      int result = userDAO.login(user.getUserID(), user.getUserPassword());
      if(result==1){
    	 session.setAttribute("userID", user.getUserID());
         PrintWriter script = response.getWriter();
         script.println("<script>");
         script.println("alert('로그인 성공')");
         script.println("location.href = 'main.jsp'");
         script.println("</script>");
      } 
      else if(result == 0){
         PrintWriter script = response.getWriter();
         script.println("<script>");
         script.println("alert('비밀번호가 틀립니다.')");
         script.println("history.back()");   
         script.println("</script>");
      }
      else if(result == -1){
         PrintWriter script = response.getWriter();
         script.println("<script>");
         script.println("alert('존재하지 않는 아이디입니다.')");
         script.println("history.back()");   
         script.println("</script>");
      }
      else if(result == -2){
         PrintWriter script = response.getWriter();
         script.println("<script>");
         script.println("alert('데이터베이스 오류가 발생하였습니다.')");
         script.println("history.back()");   
         script.println("</script>");
      }
   %>
</body>
</html>