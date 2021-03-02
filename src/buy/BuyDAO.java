package buy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.DBClose;
import db.DBOpen;
import basket.BasketDTO;

public class BuyDAO {
	
	public int total(Map map) { // 맵 타입의 맵
		int total = 0;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 레코드 갯수를 가져온다.(생성, 수정, 삭제하는 것이 아니기 때문에)

		String col = (String) map.get("col");
		String word = (String) map.get("word");

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) FROM shoppingbuy");
		if (word.trim().length() > 0) {
			sql.append(" WHERE " + col + " like '%'||?||'%'  ");
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
	
	public boolean delete(String email) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();

		sql.append(" DELETE FROM shoppingbuy");
		sql.append(" WHERE email = ?");

		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, email);

			int cnt = pstmt.executeUpdate();
			if (cnt > 0)
				flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt);
		}

		return flag;
	}
	
	public boolean deleteProduct(Map map) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();

		String email = (String) map.get("email");
		String productname = (String) map.get("productname");
		
		sql.append(" DELETE FROM shoppingbuy    ");
		sql.append(" WHERE email = ? AND productname = ?  ");

		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			pstmt.setString(2, productname);
			
			int cnt = pstmt.executeUpdate();
			if (cnt > 0)
				flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt);
		}

		return flag;
	}
	
	public boolean readBasketemail(Map map) {
		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String email = (String) map.get("email");
		String productname = (String) map.get("productname");
		
		System.out.println("제품이름" + productname);
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("  SELECT count(*) AS cnt FROM shoppingbuy   ");
		sql.append("  WHERE email = ? AND productname = ? ");

		
		try {
			pstmt=con.prepareStatement(sql.toString());
			
			pstmt.setString(1, email);
			pstmt.setString(2, productname);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
			int check = rs.getInt(1);
			
			if(check>0)flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt, rs);
		}
		
		return flag;
	}
	
	public BasketDTO readProductsum(String email) {
		BasketDTO dto = null;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("  SELECT sum(productsum) AS sum FROM shoppingbuy   ");
		sql.append(" 	 WHERE email = ?  ");

		
		try {
			pstmt=con.prepareStatement(sql.toString());
			
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto = new BasketDTO();
				
				dto.setProductsum(rs.getInt("sum"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt, rs);
		}
		
		return dto;
	}
	
	public boolean create(Map map) {
		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 레코드 갯수를 가져온다.(생성, 수정, 삭제하는 것이 아니기 때문에)
		
		
		String email = (String) map.get("email");
		String productname = (String) map.get("productname");
		String name = (String) map.get("name");
		String price = (String) map.get("price");
		String totalprice = (String) map.get("totalprice");
		String numbers = (String) map.get("numbers");
		String phone = (String)	map.get("phone");
		String zipcode = (String) map.get("zipcode");
		String address1 = (String) map.get("address1");
		String address2 = (String) map.get("address2");
		String payment = (String) map.get("payment");

		StringBuffer sql = new StringBuffer();
		
		sql.append(" INSERT INTO shoppingbuy (buyID,	email,		productname, name,  ");
		sql.append(" 						  price,	totalprice, numbers,	 phone, ");
		sql.append(" 						  zipcode,	address1, 	address2,	 payment,	mdate) ");
		sql.append(" VALUES 					((SELECT NVL(MAX(buyID),0) + 1 as buyID FROM shoppingbuy),	 ?,			?,			  ?, ");
		sql.append("		 					?,				?,			  ?, 		?, 						 ");
		sql.append("		 					?,				?,			  ?, 		?,				sysdate) ");

		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			pstmt.setString(2, productname);
			pstmt.setString(3, name);
			pstmt.setString(4, price);
			pstmt.setString(5, totalprice);
			pstmt.setString(6, numbers);
			pstmt.setString(7, phone);
			pstmt.setString(8, zipcode);
			pstmt.setString(9, address1);
			pstmt.setString(10, address2);
			pstmt.setString(11, payment);
			
			int cnt = pstmt.executeUpdate();
			if(cnt>0) flag=true;
			}
		  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt);
		}

		return flag;
	}
	
	public BasketDTO read(String email) {
		BasketDTO dto = null;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("  SELECT * FROM shoppingbuy  ");
		sql.append(" 	 WHERE email = ?  ");

		
		try {
			pstmt=con.prepareStatement(sql.toString());
			
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto = new BasketDTO();
				
				dto.setEmail(rs.getString("email"));
				dto.setProductname(rs.getString("productname"));
				dto.setProductaddress(rs.getString("productaddress"));
				dto.setNumbers(rs.getInt("numbers"));
				dto.setPrice(rs.getInt("price"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt, rs);
		}
		
		return dto;
	}
	
	public List<BuyDTO> list(Map map) {
		List<BuyDTO> list = new ArrayList<BuyDTO>();
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String col = (String)map.get("col");
		String word = (String)map.get("word");
		
		int sno = (Integer)map.get("sno");
		int eno = (Integer)map.get("eno");
		StringBuffer sql = new StringBuffer();
		
		sql.append("  SELECT buyid, email, productname, name, price, totalprice, numbers, phone, zipcode, address1, address2, payment, mdate, r  ");
		sql.append("  FROM(  ");
		sql.append("  	SELECT buyid, email, productname, name, price, totalprice, numbers, phone, zipcode, address1, address2, payment, mdate, rownum r  ");
		sql.append("  	FROM(  ");
		sql.append("  		SELECT buyid, email, productname, name, price, totalprice, numbers, phone, zipcode, address1, address2, payment, mdate  ");
		sql.append("  		FROM shoppingbuy  ");
		if(word.trim().length()>0) {
		    sql.append("  		WHERE "+col+" LIKE '%'||?||'%'  ");}
		sql.append("  		ORDER BY mdate DESC  ");
		sql.append("  		)  ");
		sql.append("  	)  ");
		sql.append("  WHERE r >= ? and r <= ?  ");

		
		try {
			pstmt=con.prepareStatement(sql.toString());
			int i = 1;
			if(word.trim().length()>0)pstmt.setString(i++, word);
			
			pstmt.setInt(i++, sno);
			pstmt.setInt(i++, eno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BuyDTO dto = new BuyDTO();
				dto.setBuyID(rs.getInt("buyid"));
				dto.setEmail(rs.getString("email"));
				dto.setProductname(rs.getString("productname"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getString("price"));
				dto.setTotalprice(rs.getString("totalprice"));
				dto.setNumbers(rs.getString("numbers"));
				dto.setPhone(rs.getString("phone"));
				dto.setZipcode(rs.getString("zipcode"));
				dto.setAddress1(rs.getString("address1"));
				dto.setAddress2(rs.getString("address2"));
				dto.setPayment(rs.getString("payment"));
				dto.setMdate(rs.getString("mdate"));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt, rs);
		}
		
		return list;
	}
}
