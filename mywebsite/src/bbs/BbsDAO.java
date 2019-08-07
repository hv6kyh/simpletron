package bbs;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.MyUtil;



public class BbsDAO {

		// dao : �����ͺ��̽� ���� ��ü�� ����



		private Connection conn; // connection:db�������ϰ� ���ִ� ��ü

		//private PreparedStatement pstmt;

		private ResultSet rs;



		// mysql ó���κ�

		public BbsDAO() {

			// �����ڸ� �������ش�.

			try {

				String dbURL = "jdbc:mysql://localhost:3306/BBS?&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

				String dbID = "root";
				// DB 비밀번호 암호화
				String dbPassword = "*****";

				Class.forName("com.mysql.jdbc.Driver");

				conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
				MyUtil.logMessage(this, "bbsDAO DB ���� ����");

			} catch (Exception e) {

				MyUtil.logMessage(this, "DB ���� �Ұ�");
				e.printStackTrace();

			}

		}



		//������ �ð��� �������� �Լ�

		public String getDate() {

			String SQL = "SELECT NOW()";
//			String SQL = "SELECT SYSDATE()";

			String nowTime = null;

			try {

				PreparedStatement pstmt = conn.prepareStatement(SQL);

				rs = pstmt.executeQuery();

				if(rs.next()) {

					nowTime = rs.getString(1);
					MyUtil.logMessage(this, "�� �ۼ��� ��ȯ�Ǵ� ���� �ð� : " + nowTime);
//					return rs.getString(1);
					return nowTime;


				}

			} catch (Exception e) {

				e.printStackTrace();

			}

			return ""; //�����ͺ��̽� ����

		}



		//bbsID �Խñ� ��ȣ �������� �Լ�

			public int getNext() {

				String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";

				try {

					PreparedStatement pstmt = conn.prepareStatement(SQL);

					rs = pstmt.executeQuery();

					if(rs.next()) {


						return rs.getInt(1) + 1;

					}

					System.out.println("getNext1");
					return 1;//ù ��° �Խù��� ����

				} catch (Exception e) {

					e.printStackTrace();

				}

				return -1; //�����ͺ��̽� ����

			}



			//������ ���� �ۼ��ϴ� �Լ�

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

				return -1; //�����ͺ��̽� ����

			}

			public ArrayList<Bbs> getList(int pageNumber){

				MyUtil.logMessage(this, "�Ѿ��� ������ �ѹ� = " + pageNumber);

//				String SQL = "SELECT * FROM BBS WHERE bbsID < ? bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
				String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";

//				String SQL = "SELECT * FROM BBS WHERE bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";

				ArrayList<Bbs> list = new ArrayList<Bbs>();
				MyUtil.logMessage(this, "list���� ���Ḯ��Ʈ ����");

				try {

					PreparedStatement pstmt = conn.prepareStatement(SQL);
					MyUtil.logMessage(this, "pstmt ����");

					pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
//					pstmt.setInt(1, getNext());

					MyUtil.logMessage(this, "setInt ����");


					rs = pstmt.executeQuery();
					MyUtil.logMessage(this, "���� ����");


					int tmp = 0;
					while (rs.next()) {

						MyUtil.logMessage(this, tmp++ + "��° ����");

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

					MyUtil.logMessage(this, "getList ���� ����");
					e.printStackTrace();

				}

				MyUtil.printList(this, list);
				return list;

			}

			//10 ���� ����¡ ó���� ���� �Լ�

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

			//���� �Լ�

			public int update(int bbsID, String bbsTitle, String bbsContent) {

//				String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ?, WHERE bbsID = ?";
				String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";


					try {

						PreparedStatement pstmt = conn.prepareStatement(SQL);
						MyUtil.logMessage(this, "pstmt ����");

						pstmt.setString(1, bbsTitle);

						pstmt.setString(2, bbsContent);

						pstmt.setInt(3, bbsID);

						return pstmt.executeUpdate();



					} catch (Exception e) {

						e.printStackTrace();

					}

					MyUtil.logMessage(this, "�� ���� DB ���� ����");
					return -1; // �����ͺ��̽� ����

				}

			//���� �Լ�

			public int delete(int bbsID) {

				String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?";

				try {

					PreparedStatement pstmt = conn.prepareStatement(SQL);

					pstmt.setInt(1, bbsID);

					return pstmt.executeUpdate();



				} catch (Exception e) {

					e.printStackTrace();

				}

				return -1; // �����ͺ��̽� ����

			}

	}
