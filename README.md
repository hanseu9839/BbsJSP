# BulletinBoradJSP
--------------------
'getList'메소드 - 존재하는 게시글 리스트를 불러오는 메소드 

"Select * from bbs where bbsID < ? and bbsAvailable = 1 order by bbsID desc limit 10"

모든 게시글을 조회한다, 현재 유효번호가 존재하고 새롭게 작성될 게시글 번호보다 작은 모든 게시글 번호를 내림차순 정렬로 최대 10개까지 조회합니다. 
```
     public ArrayList<Bbs> getList(int pageNumber){
			   String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
			   ArrayList<Bbs> list = new ArrayList<Bbs>();
			   try {
				   PreparedStatement pstmt = conn.prepareStatement(SQL); 
				   pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);  
				   // getNext글 다음에 작성될 번호  현재 만약 게시글 5개라고 가정한다면 getNext하면 6개 // 페이지는 1이니깐 결과적으로는 6이라는 값이 결정된다.
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
```
getNext() - (pageNumber - 1) * 10

만약 현재 글이 5개라면 getNext() = 6 , 1페이지이기 때문에 결과값은 6이 나온다. 6보다 작은 5개의 게시글이 출력  만약 현재 글이 24개라면, 3페이지기 때문에 결과값은 2가 나온다. 4보다 작은 3개의 게시글이 출력 

while(rs.next())

결과값이 존재하는 동안 각각의 요소를 담아 하나의 리스트를 완성하며 'getList'로 return
```
 public boolean nextPage(int pageNumber) { //페이징 처리를 위해서 존재한다.
			   String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1";
			   ArrayList<Bbs> list = new ArrayList<Bbs>();
			   try {
				   PreparedStatement pstmt = conn.prepareStatement(SQL); 
				   pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);  
				   // getNext글 다음에 작성될 번호  현재 만약 게시글 5개라고 가정한다면 getNext하면 6개 // 페이지는 1이니깐 결과적으로는 6이라는 값이 결정된다.
				  rs = pstmt.executeQuery();
				  while(rs.next()) {
					 return true; //다음페이지로 넘어갈 수 있게 해준다. 
				  }
			   } catch(Exception e) {
				   e.printStackTrace();
			   }
			   return false;
		   }
```
nextPage - 페이징 처리 메소드

특정한 페이지가 존재하는지 조회하는 메소드 게시글이 10개에서 11개로 넘어갈 때 다음 버튼을 만들어 페이징 처리를 위한 기능 
'bbs.jsp' 를 수정합니다. 먼저 필요한 정보를 쓸 수 있도록 페이지 설정 란에서 'import'코드를 추가합니다. 그리고 아래에서 기본 페이지를 '1'로 설정하고 브라우저에서 넘어오는 페이지번호를 'int'타입으로 캐스팅 해주는 코드를 추가합니다. 파라미터는 기본적으로 데이터 형식이 'object'이기 때문에 넘어오는 데이터를 모두 각각 데이터 형식에 맞게 '형변환'을 해주어야 한다.


게시글 목록을 받아올 수 있는 코드 추가 



1.게시글 번호         'i'번째 리스트의 게시글 번호 표시 

2.글 제목                 'i'번째 글 제목을 누르면 글 내용을 볼 수 있게 링크를 걸어둠 

3.글 작성자             'i'번째 게시글의 사용자 표시 

4.글 작성일자         실제 데이터베이스의 표기되는 0~11번째 자리의 문자를 구하여 날짜를 표기,11~13번째 자리   문자를 구하고 시간을 표시, 14~16번째 자리 문자를 구하여 분을 표시 

[마크다운사용법 링크](https://gist.github.com/ihoneymon/652be052a0727ad59601)
1. 초기 로그인화면
![bbsp1](https://user-images.githubusercontent.com/62438578/139779255-08fbd28d-d539-4dbb-9fa2-710325b53584.PNG)
2. 두번째 회원가입 화면
![bbsp2](https://user-images.githubusercontent.com/62438578/139779338-afd0919e-5822-47bb-95eb-c1516c9d0029.PNG)

3. 회원가입 성공 
![bbs3](https://user-images.githubusercontent.com/62438578/139779359-67a00c92-961c-45d7-9375-99f185e339a0.PNG)

4. 메인화면 
![bbs4](https://user-images.githubusercontent.com/62438578/139779385-6b5d7528-803d-4510-898c-3e54d8a40901.PNG)

5. 게시판 화면
![bbs5](https://user-images.githubusercontent.com/62438578/139779210-729787fc-a68b-4734-90a4-51d076f521fa.PNG)
