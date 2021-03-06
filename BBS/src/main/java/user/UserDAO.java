package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
   private Connection conn;
   private PreparedStatement pstmt;
   private ResultSet rs;
   
   //try catch 구문을 사용하여 예외처리 해준다. 만약 오류가 난다면 그 부분을 print해준다. 
   public UserDAO() {
      try {
         String dbURL = "jdbc:mysql://localhost:3306/BBS?characterEncoding=UTF-8&serverTimezone=Asia/Seoul "; //mysql 3306포트를 사용한다.
         String dbID = "root";
         String dbPassword="@qnpfr54953";
         Class.forName("com.mysql.cj.jdbc.Driver");
         //Driver라는 것은 mysql에 접속할 수 있도록 해준것이다.
         conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   public int login(String userID,String userPassword) {
      String SQL = "SELECT userPassword FROM user WHERE userID = ?";
      //SQL injuction?같은 해킹비법을 방어하기위해 하나의 변수안에 미리 SQL구문을 넣고 PreparedStatement를 통해서 넣어놨다가 미리 준비한 구문에
      // 이제 userID를 넣고선 ?표랑 바꿔주는 것이다. 
      try {
         pstmt = conn.prepareStatement(SQL);
         pstmt.setString(1,userID);
         rs = pstmt.executeQuery();
         if(rs.next()) {
            if(rs.getString(1).equals(userPassword)) {
               return 1; //user가 적은 비밀번호와 userPassword가 동일하다면 접속이 실행된다. 
            }
            else
               return 0;//비밀번호 불일치 
         }
         return -1; //아이디가 없음 
      }catch(Exception e) {
         e.printStackTrace();
      }
      return -2; //데이터 베이스 오류를 나타냄 
   }
   public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?,?,?,?,?) ";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,user.getUserID());
			pstmt.setString(2,user.getUserPassword());
			pstmt.setString(3,user.getUserName());
			pstmt.setString(4,user.getUserGender());
			pstmt.setString(5,user.getUserEmail());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류 
   }
}

