package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

//DAO�� ���������ٰ�ü�� �����̴�. 
	public class BbsDAO { //BbsDAO�� �������� �Լ��� ���Ǳ� ������ �����ͺ��̽��� ���ٿ� �־ �浹�� �Ͼ�� �ʵ��� PreparedStatement�� �����ش�. 
		   private Connection conn;
		
		   private ResultSet rs;
		   
		   //try catch ������ ����Ͽ� ����ó�� ���ش�. ���� ������ ���ٸ� �� �κ��� print���ش�. 
		   public BbsDAO() {
		      try {
		         String dbURL = "jdbc:mysql://localhost:3306/BBS?characterEncoding=UTF-8&serverTimezone=Asia/Seoul "; //mysql 3306��Ʈ�� ����Ѵ�.
		         String dbID = "root";
		         String dbPassword="@qnpfr54953";
		         Class.forName("com.mysql.cj.jdbc.Driver");
		         //Driver��� ���� mysql�� ������ �� �ֵ��� ���ذ��̴�.
		         conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
		      }catch(Exception e) {
		         e.printStackTrace();
		      }
		  }
		   public String getDate() {//������ �ð��� �������� �κ����ν� �Խ����� �ۼ��� �� ������ �ð��� �־��ִ� �κ��̴�. 
			   String SQL = "SELECT NOW()";
			   try {
				   PreparedStatement pstmt = conn.prepareStatement(SQL); //���� ����Ǿ��ִ� conn��ü�� �̿��ؼ� SQL�� ������ ���� �غ�ܰ�� ������ش�. 
				   rs = pstmt.executeQuery();
				   if(rs.next()) {
					   return rs.getString(1); //��ȯ���ش�. 
				   }
			   } catch(Exception e) {
				   e.printStackTrace();
			   }
			   return "";
		   }
		   public int getNext() { 
			   String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
			   try {
				   PreparedStatement pstmt = conn.prepareStatement(SQL); //���� ����Ǿ��ִ� conn��ü�� �̿��ؼ� SQL�� ������ ���� �غ�ܰ�� ������ش�. 
				   rs = pstmt.executeQuery();
				   if(rs.next()) {
					   return rs.getInt(1)+1; //��ȯ���ش�. 
				   }
				   return 1; //ù ��° �Խù��� ��� 
			   } catch(Exception e) {
				   e.printStackTrace();
			   }
			   return -1;
		   }
		   public int write(String bbsTitle,String userID,String bbsContent){ 
			   String SQL = "INSERT INTO BBS VALUES(?, ?, ?, ?, ?,?)";
			   try {
				   PreparedStatement pstmt = conn.prepareStatement(SQL); 
				   pstmt.setInt(1, getNext());
				   pstmt.setString(2, bbsTitle);
				   pstmt.setString(3, userID);
				   pstmt.setString(4, getDate());
				   pstmt.setString(5, bbsContent);
				   pstmt.setInt(6, 1);// rs= pstmt.executeQuery(); insert�� �ڵ����� ���� 
				   return pstmt.executeUpdate();
			   } catch(Exception e) {
				   e.printStackTrace();
			   }
			   return -1;
		   }
		   public ArrayList<Bbs> getList(int pageNumber){
			   String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
			   ArrayList<Bbs> list = new ArrayList<Bbs>();
			   try {
				   PreparedStatement pstmt = conn.prepareStatement(SQL); 
				   pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);  
				   // getNext�� ������ �ۼ��� ��ȣ  ���� ���� �Խñ� 5����� �����Ѵٸ� getNext�ϸ� 6�� // �������� 1�̴ϱ� ��������δ� 6�̶�� ���� �����ȴ�.
				  rs = pstmt.executeQuery();
				  while(rs.next()) {
					  Bbs bbs = new Bbs();
					  bbs.setBbsID(rs.getInt(1));
					  bbs.setBbsTitle(rs.getString(2));
					  bbs.setUserID(rs.getString(3));
					  bbs.setBbsDate(rs.getString(4));
					  bbs.setBbsContent(rs.getString(5));
					  bbs.setBbsAvailable(rs.getInt(6));
					  list.add(bbs);
				  }
			   } catch(Exception e) {
				   e.printStackTrace();
			   }
			   return list;
		   }
		   public boolean nextPage(int pageNumber) { //����¡ ó���� ���ؼ� �����Ѵ�.
			   String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1";
			   ArrayList<Bbs> list = new ArrayList<Bbs>();
			   try {
				   PreparedStatement pstmt = conn.prepareStatement(SQL); 
				   pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);  
				   // getNext�� ������ �ۼ��� ��ȣ  ���� ���� �Խñ� 5����� �����Ѵٸ� getNext�ϸ� 6�� // �������� 1�̴ϱ� ��������δ� 6�̶�� ���� �����ȴ�.
				  rs = pstmt.executeQuery();
				  while(rs.next()) {
					 return true; //������������ �Ѿ �� �ְ� ���ش�. 
				  }
			   } catch(Exception e) {
				   e.printStackTrace();
			   }
			   return false;
		   }
		   
		   public Bbs getBbs(int bbsID) {
			   String SQL = "SELECT * FROM BBS WHERE bbsID = ?";
			   ArrayList<Bbs> list = new ArrayList<Bbs>();
			   try {
				   PreparedStatement pstmt = conn.prepareStatement(SQL); 
				   pstmt.setInt(1, bbsID);  
				   // getNext�� ������ �ۼ��� ��ȣ  ���� ���� �Խñ� 5����� �����Ѵٸ� getNext�ϸ� 6�� // �������� 1�̴ϱ� ��������δ� 6�̶�� ���� �����ȴ�.
				  rs = pstmt.executeQuery();
				  while(rs.next()) {
					  Bbs bbs = new Bbs();
					  bbs.setBbsID(rs.getInt(1));
					  bbs.setBbsTitle(rs.getString(2));
					  bbs.setUserID(rs.getString(3));
					  bbs.setBbsDate(rs.getString(4));
					  bbs.setBbsContent(rs.getString(5));
					  bbs.setBbsAvailable(rs.getInt(6));
					  return bbs; //bbs�ν��Ͻ��� �ҷ������� ��ȯ�ϴ� ������� �״�� �����ش�. 
				  }
			   } catch(Exception e) {
				   e.printStackTrace();
			   }
			   return null;
		   }
		   public int update(int bbsID,String bbsTitle,String bbsContent) {
			   String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";
			   try {
				   PreparedStatement pstmt = conn.prepareStatement(SQL); 
				   pstmt.setString(1, bbsTitle);
				   pstmt.setString(2, bbsContent);
				   pstmt.setInt(3, bbsID);
				
				   return pstmt.executeUpdate();
			   } catch(Exception e) {
				   e.printStackTrace();
			   }
			   return -1;
		   }
		   public int delete(int bbsID) {
			   String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?";
			   try {
				   PreparedStatement pstmt = conn.prepareStatement(SQL); 
				   pstmt.setInt(1, bbsID);
				   return pstmt.executeUpdate();
			   } catch(Exception e) {
				   e.printStackTrace();
			   }
			   return -1;
		   }
}

