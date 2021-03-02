package basket;

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

public class BasketDAO {
	
	public boolean delete(String email) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();

		sql.append(" DELETE FROM shoppingbasket");
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
		
		sql.append(" DELETE FROM shoppingbasket    ");
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
		
		sql.append("  SELECT count(*) AS cnt FROM shoppingbasket   ");
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
		
		sql.append("  SELECT sum(productsum) AS sum FROM shoppingbasket   ");
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
		String productaddress = (String) map.get("productaddress");
		String uploadbtn = (String) map.get("uploadbtn");

		int numbers = (int) map.get("numbers");
		int price = (int) map.get("price");
		int productsum = (int) map.get("productsum");

		StringBuffer sql = new StringBuffer();
		
		sql.append(" INSERT INTO shoppingbasket (basketID, email, productname, productaddress, ");
		sql.append(" 							 numbers, price,	productsum, uploadbtn) ");
		sql.append(" VALUES 					((SELECT NVL(MAX(basketID),0) + 1 as basketID FROM shoppingbasket), 		?,				?,			  ?, ");
		sql.append("		 					?,			?,			  ?, ?) ");

		try {
			pstmt=con.prepareStatement(sql.toString());

			pstmt.setString(1, email);
			pstmt.setString(2, productname);
			pstmt.setString(3, productaddress);
			pstmt.setInt(4, numbers);
			pstmt.setInt(5, price);
			pstmt.setInt(6, productsum);
			pstmt.setString(7, uploadbtn);
			
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
		
		sql.append("  SELECT * FROM shoppingbasket  ");
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
	
	public List<BasketDTO> list(String email) {
		List<BasketDTO> list = new ArrayList<BasketDTO>();
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("  SELECT * FROM shoppingbasket  ");
		sql.append("  WHERE email = ?  ");

		
		try {
			pstmt=con.prepareStatement(sql.toString());
			
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BasketDTO dto = new BasketDTO();
				
				dto.setEmail(rs.getString("email"));
				dto.setProductname(rs.getString("productname"));
				dto.setProductaddress(rs.getString("productaddress"));
				dto.setNumbers(rs.getInt("numbers"));
				dto.setPrice(rs.getInt("price"));
				dto.setUploadbtn(rs.getString("uploadbtn"));
				
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
