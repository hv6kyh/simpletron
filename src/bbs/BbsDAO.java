package bbs;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.MyUtil;



public class BbsDAO {

		// dao : 데이터베이스 접근 객체의 약자

	

		private Connection conn; // connection:db에접근하게 해주는 객체

		//private PreparedStatement pstmt;

		private ResultSet rs;



		// mysql 처리부분

		public BbsDAO() {

			// 생성자를 만들어준다.

			try {

				String dbURL = "jdbc:mysql://localhost:3306/BBS?&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

				String dbID = "root";

				String dbPassword = "mysql";

				Class.forName("com.mysql.jdbc.Driver");

				conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
				MyUtil.logMessage(this, "bbsDAO DB 연결 성공");

			} catch (Exception e) {

				MyUtil.logMessage(this, "DB 연결 불가");
				e.printStackTrace();

			}

		}

		

		//현재의 시간을 가져오는 함수

		public String getDate() { 

			String SQL = "SELECT NOW()";
//			String SQL = "SELECT SYSDATE()";
			
			String nowTime = null;

			try {

				PreparedStatement pstmt = conn.prepareStatement(SQL);

				rs = pstmt.executeQuery();

				if(rs.next()) {
					
					nowTime = rs.getString(1);
					MyUtil.logMessage(this, "글 작성시 반환되는 현재 시간 : " + nowTime);
//					return rs.getString(1);
					return nowTime;
					

				}

			} catch (Exception e) {

				e.printStackTrace();

			}

			return ""; //데이터베이스 오류

		}

		

		//bbsID 게시글 번호 가져오는 함수

			public int getNext() { 

				String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";

				try {

					PreparedStatement pstmt = conn.prepareStatement(SQL);

					rs = pstmt.executeQuery();

					if(rs.next()) {

						
						return rs.getInt(1) + 1;

					}

					System.out.println("getNext1");
					return 1;//첫 번째 게시물인 경우

				} catch (Exception e) {

					e.printStackTrace();

				}
				
				return -1; //데이터베이스 오류

			}

			

			//실제로 글을 작성하는 함수

			public int write(String bbsTitle, String userID, String bbsContent) { 

				String SQL = "INSERT INTO BBS VALUES(?, ?, ?, ?, ?, ?)";

				try {

					

					PreparedStatement pstmt = conn.prepareStatement(SQL);

					pstmt.setInt(1, getNext());

					pstmt.setString(2, bbsTitle);

					pstmt.setString(3, userID);

					pstmt.setString(4, getDate());

					pstmt.setString(5, bbsContent);

					pstmt.setInt(6,1);

					

					return pstmt.executeUpdate();

					

				} catch (Exception e) {

					e.printStackTrace();

				}

				return -1; //데이터베이스 오류

			}
			
			public ArrayList<Bbs> getList(int pageNumber){ 
				
				MyUtil.logMessage(this, "넘어온 페이지 넘버 = " + pageNumber);

//				String SQL = "SELECT * FROM BBS WHERE bbsID < ? bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
				String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
				
//				String SQL = "SELECT * FROM BBS WHERE bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";

				ArrayList<Bbs> list = new ArrayList<Bbs>();
				MyUtil.logMessage(this, "list담을 연결리스트 생성");

				try {

					PreparedStatement pstmt = conn.prepareStatement(SQL);
					MyUtil.logMessage(this, "pstmt 생성");

					pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
//					pstmt.setInt(1, getNext());
					
					MyUtil.logMessage(this, "setInt 실행");
					

					rs = pstmt.executeQuery();
					MyUtil.logMessage(this, "쿼리 실행");
					

					int tmp = 0;
					while (rs.next()) {

						MyUtil.logMessage(this, tmp++ + "번째 루프");

						Bbs bbs = new Bbs();

						bbs.setBbsID(rs.getInt(1));
						MyUtil.logMessage(this, bbs.getBbsID()+"");

						bbs.setBbsTitle(rs.getString(2));
						MyUtil.logMessage(this, bbs.getBbsTitle());

						bbs.setUserID(rs.getString(3));
						MyUtil.logMessage(this, bbs.getUserID());

						bbs.setBbsDate(rs.getString(4));
						MyUtil.logMessage(this, bbs.getBbsDate());

						bbs.setBbsContent(rs.getString(5));
						MyUtil.logMessage(this, bbs.getBbsContent());

						bbs.setBbsAvailable(rs.getInt(6));
						MyUtil.logMessage(this, bbs.getBbsAvailable()+"");

						list.add(bbs);
						

					}

				} catch (Exception e) {
					
					MyUtil.logMessage(this, "getList 쿼리 에러");
					e.printStackTrace();

				}

				MyUtil.printList(this, list);
				return list; 				

			}
			
			//10 단위 페이징 처리를 위한 함수

			public boolean nextPage (int pageNumber) {

//				String SQL = "SELECT * FROM BBS WHERE bbsID < ? bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
				String SQL = "SELECT * FROM BBS WHERE bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
				

				ArrayList<Bbs> list = new ArrayList<Bbs>();

				try {

					PreparedStatement pstmt = conn.prepareStatement(SQL);

					pstmt.setInt(1, getNext() - (pageNumber -1) * 10);

					rs = pstmt.executeQuery();

					if (rs.next()) {

						return true;

					}

				} catch (Exception e) {

//					e.printStackTrace();

				}

				return false; 		

			}
			
			public Bbs getBbs(int bbsID) {

				String SQL = "SELECT * FROM BBS WHERE bbsID = ?";

				try {

					PreparedStatement pstmt = conn.prepareStatement(SQL);

					pstmt.setInt(1, bbsID);

					rs = pstmt.executeQuery();

					if (rs.next()) {

						Bbs bbs = new Bbs();

						bbs.setBbsID(rs.getInt(1));

						bbs.setBbsTitle(rs.getString(2));

						bbs.setUserID(rs.getString(3));

						bbs.setBbsDate(rs.getString(4));

						bbs.setBbsContent(rs.getString(5));

						bbs.setBbsAvailable(rs.getInt(6));


						System.out.println("getBbs");
						return bbs;

					}

				} catch (Exception e) {
					System.out.println("getBbs err");
					e.printStackTrace();

				}

				return null;



			}
			
			//수정 함수

			public int update(int bbsID, String bbsTitle, String bbsContent) {

//				String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ?, WHERE bbsID = ?";
				String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";
				

					try {

						PreparedStatement pstmt = conn.prepareStatement(SQL);
						MyUtil.logMessage(this, "pstmt 생성");

						pstmt.setString(1, bbsTitle);

						pstmt.setString(2, bbsContent);

						pstmt.setInt(3, bbsID);

						return pstmt.executeUpdate();



					} catch (Exception e) {

						e.printStackTrace();

					}

					MyUtil.logMessage(this, "글 수정 DB 접근 오류");
					return -1; // 데이터베이스 오류

				}

			//삭제 함수

			public int delete(int bbsID) {

				String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?";

				try {

					PreparedStatement pstmt = conn.prepareStatement(SQL);   

					pstmt.setInt(1, bbsID);

					return pstmt.executeUpdate();



				} catch (Exception e) {

					e.printStackTrace();

				}

				return -1; // 데이터베이스 오류

			}

	}