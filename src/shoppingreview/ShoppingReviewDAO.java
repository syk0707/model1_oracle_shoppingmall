package shoppingreview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import shoppingreview.ShoppingReviewDTO;
import db.DBClose;
import db.DBOpen;

public class ShoppingReviewDAO {
	public boolean update(ShoppingReviewDTO dto) {
		boolean flag = false;

		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();

		sql.append(" UPDATE	shoppingreview  ");
		sql.append(" SET    ");
		sql.append(" 	   	content = ?, ");
		sql.append(" 	   	mdate = sysdate ");
		sql.append(" WHERE ");
		sql.append("        reviewnum = ? ");

		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getContent());
			pstmt.setInt(2, dto.getReviewnum());

			int cnt = pstmt.executeUpdate();
			
			if(cnt>0)flag=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt);
		}

		return flag;
	}
	
	public ShoppingReviewDTO read(int num) {
		ShoppingReviewDTO dto = null;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT productid, reviewnum, email, name, content, mdate");
		sql.append(" FROM shoppingreview ");
		sql.append(" WHERE productID = ?");

		try {
			pstmt = con.prepareStatement(sql.toString());
			// 전송 객체 생성
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();
			// 쿼리문을 전송한다.
			String content = null;
			if (rs.next()) {
				dto = new ShoppingReviewDTO();
				dto.setProductID(rs.getInt("productid"));
				dto.setContent(rs.getString("content"));
				dto.setEmail(rs.getString("email"));
				dto.setName(rs.getString("name"));
				dto.setMdate(rs.getString("mdate"));
				dto.setReviewnum(rs.getInt("reviewnum"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt, rs);
		}

		return dto;
	}
	
	public boolean checkPasswd(String email, String chkpasswd) {

		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String passwd = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT password ");
		sql.append(" FROM	mallmember ");
		sql.append(" WHERE email=? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, email);

			rs=pstmt.executeQuery();
			if(rs.next()) {
				passwd = rs.getString(1);
			}
			if (passwd.equals(chkpasswd))flag = true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			flag = false;
		} catch (NullPointerException e){
			// TODO Auto-generated catch block
			flag = false;
		}
		finally {
			DBClose.close(con, pstmt, rs);
		}

		return flag;
	}
	
	public boolean checkId(String email, int reviewnum) {

		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT email ");
		sql.append(" FROM	shoppingreview ");
		sql.append(" WHERE  reviewnum=? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, reviewnum);
			
			String chkid = " ";
			rs=pstmt.executeQuery();
			if(rs.next()) {
				chkid = rs.getString(1);
			}

			if (email.equals(chkid))flag = true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			flag = false;
		} catch (NullPointerException e){
			// TODO Auto-generated catch block
			flag = false;
		}
		finally {
			DBClose.close(con, pstmt, rs);
		}

		return flag;
	}
	
	
	public boolean create(String email, String name, String content, int productid) {
	boolean flag = false;
		
	Connection con = DBOpen.open();
	PreparedStatement pstmt = null;
	
	StringBuffer sql = new StringBuffer();
	sql.append(" INSERT INTO shoppingreview(productid, reviewnum, email, name, content, mdate)   ");
	sql.append(" VALUES			  (?,   (SELECT NVL(MAX(reviewnum),0) + 1 as reviewnum FROM shoppingreview),    ?,       ?,    ?, sysdate     )     ");
	
	
	try {
		pstmt = con.prepareStatement(sql.toString());
		
		pstmt.setInt(1,  productid);

		pstmt.setString(2,  email);
		pstmt.setString(3,  name);
		pstmt.setString(4,  content);
		
		int cnt = pstmt.executeUpdate();
		
		if(cnt>0) flag = true;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		DBClose.close(con, pstmt);
	}
	
	
	return flag;
}
	
	public int getMaxNum() {
		int result = 0;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT NVL(MAX(reviewnum), 0) FROM shoppingreview   ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(con, pstmt, rs);
		}
			
		return result;
	}
	
	public int total(Map map) { // 맵 타입의 맵
		int total = 0;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 레코드 갯수를 가져온다.(생성, 수정, 삭제하는 것이 아니기 때문에)

		String col = (String) map.get("col");
		String word = (String) map.get("word");
		int productid = (int) map.get("productid");
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) FROM shoppingreview WHERE productid = " + productid);
		if (word.trim().length() > 0) {
			sql.append(" AND " + col + " like '%'||?||'%'  ");
		}

		try {
			pstmt = con.prepareStatement(sql.toString());
			if (word.trim().length() > 0) {
				pstmt.setString(1, word);
			}
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt, rs);
		}

		return total;
	}
	
	public List<ShoppingReviewDTO> list(Map map) {
        List<ShoppingReviewDTO> list = new ArrayList<ShoppingReviewDTO>();
        Connection con = DBOpen.open();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
		int sno = (Integer)map.get("sno");
		int eno = (Integer)map.get("eno");
		int productid = (Integer)map.get("productid");
        
		StringBuffer sql = new StringBuffer();
		
		sql.append("  SELECT productid, reviewnum, email, name, content, mdate, r ");
		sql.append("  FROM(  ");
		sql.append("  	SELECT productid, reviewnum, email, name, content, mdate, rownum r  ");
		sql.append("  	FROM(  ");
		sql.append("	    SELECT productid, reviewnum, email, name, content, mdate    ");
		sql.append("	 	FROM shoppingreview");
		sql.append("	 	WHERE productid=?");
		sql.append("  		ORDER BY reviewnum DESC  ");
		sql.append("  		)  ");
		sql.append("  	)  ");
		sql.append("  WHERE r >= ? and r <= ?  ");
		
        try {
            pstmt = con.prepareStatement(sql.toString());
			int i = 1;
			pstmt.setInt(i++, productid);
			pstmt.setInt(i++, sno);
			pstmt.setInt(i++, eno);
			
			rs = pstmt.executeQuery();
			
            while(rs.next())
            {
                ShoppingReviewDTO dto = new ShoppingReviewDTO();
                dto.setReviewnum(rs.getInt("reviewnum"));
                dto.setProductID(rs.getInt("productid"));
                dto.setEmail(rs.getString("email"));
                dto.setName(rs.getString("name"));
                dto.setContent(rs.getString("content"));
                dto.setMdate(rs.getString("mdate"));

                
                list.add(dto);
            }
        } catch(Exception e) {
            System.out.println(e.toString());
        } finally {
        	DBClose.close(con, pstmt, rs);
        }        
        return list;
    }
	
	 public boolean delete(int reviewnum) {
		 	boolean flag = false;
	        Connection con = DBOpen.open();
	        PreparedStatement pstmt = null;
	        
			StringBuffer sql = new StringBuffer();
			sql.append(" DELETE FROM shoppingreview  ");
			sql.append(" WHERE reviewnum = ?");
	        
	        try {
	            pstmt = con.prepareStatement(sql.toString());
	            pstmt.setInt(1, reviewnum);
	            int cnt = pstmt.executeUpdate();
	            
	            if(cnt > 0) {
	            	flag = true;
	            }
	            
	        } catch(Exception e) {
	            System.out.println(e.toString());
	        }
	        
	        return flag;
	    }
}


