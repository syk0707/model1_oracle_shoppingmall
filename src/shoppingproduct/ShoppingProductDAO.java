package shoppingproduct;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.DBClose;
import db.DBOpen;
import shoppingproduct.ShoppingProductDTO;
import utility.DBConnectionPool;




public class ShoppingProductDAO {
	
	private DBConnectionPool connPool;
	private static final String ALLRETRIEVE_STMT = "SELECT * FROM shoppingproduct";
	private static final String INSERT_STMT = "INSERT INTO shoppingproduct VALUES(?,?,?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE shoppingproduct SET ProductType = ? ProductName = ? Explanation = ? Price = ? Inventory = ? Uploadbtn = ? WHERE ProductID = ?";
	private static final String GETID_STMT = "SELECT COUNT(ProductID) FROM shoppingproduct";
	
	public int totalProductType(Map map) { // 맵 타입의 맵
		int total = 0;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 레코드 갯수를 가져온다.(생성, 수정, 삭제하는 것이 아니기 때문에)

		String col = (String) map.get("col");
		String word = (String) map.get("word");
		String producttype = (String) map.get("producttype");

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) FROM shoppingproduct WHERE producttype='"+producttype+"'");
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
	
	public List<ShoppingProductDTO> listProducttype(Map map) {
		List<ShoppingProductDTO> list = new ArrayList<ShoppingProductDTO>();
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String col = (String)map.get("col");
		String word = (String)map.get("word");
		String producttype = (String)map.get("producttype");
		
		int sno = (Integer)map.get("sno");
		int eno = (Integer)map.get("eno");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("  SELECT productid, producttype, productname, productaddress, explanation, price, inventory, uploadbtn, r  ");
		sql.append("  FROM(  ");
		sql.append("  	SELECT productid, producttype, productname, productaddress, explanation, price, inventory, uploadbtn, rownum r  ");
		sql.append("  	FROM(  ");
		sql.append("  		SELECT productid, producttype, productname,  productaddress, explanation, price, inventory, uploadbtn  ");
		sql.append("  		FROM shoppingproduct  ");
		sql.append("  		WHERE producttype='"+producttype+"' ");
		
		
		if(word.trim().length()>0) {
		    sql.append("  		AND "+col+" LIKE '%'||?||'%'   ");}
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
				ShoppingProductDTO dto = new ShoppingProductDTO();
				
				dto.setProductid(rs.getInt("productid"));
				dto.setProducttype(rs.getString("producttype"));
				dto.setProductname(rs.getString("productname"));
				dto.setProductaddress(rs.getString("productaddress"));
				dto.setExplanation(rs.getString("explanation"));
				dto.setUploadbtn(rs.getString("uploadbtn"));
				dto.setPrice(rs.getInt("price"));
				dto.setInventory(rs.getInt("inventory"));
				
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
	
    public ShoppingProductDTO readproduct(String productname) {
    	ShoppingProductDTO dto = null;
    	
    	Connection con = DBOpen.open();
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT * FROM shoppingproduct ");
    	sql.append(" WHERE productid = ? ");
    	
    	try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, productname);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ShoppingProductDTO();
				dto.setProductid(rs.getInt("productid"));
				dto.setProducttype(rs.getString("producttype"));
				dto.setProductname(rs.getString("productname"));
				dto.setProductaddress(rs.getString("productaddress"));
				dto.setExplanation(rs.getString("explanation"));
				dto.setPrice(rs.getInt("price"));
				dto.setInventory(rs.getInt("inventory"));
				dto.setUploadbtn(rs.getString("uploadbtn"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt, rs);
		}
    	
    	return dto;
    }
	
	public int total(Map map) { // 맵 타입의 맵
		int total = 0;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 레코드 갯수를 가져온다.(생성, 수정, 삭제하는 것이 아니기 때문에)

		String col = (String) map.get("col");
		String word = (String) map.get("word");

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) FROM shoppingproduct");
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
	
	public boolean create(ShoppingProductDTO dto) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" INSERT INTO shoppingproduct (productid, productname, price, inventory, ");
		sql.append(" 							 producttype, uploadbtn, detailupload, productaddress, explanation, mdate) ");
		sql.append(" VALUES 					((SELECT NVL(MAX(productid),0) + 1 as productid FROM shoppingproduct),			?,			  ?,  ?,  ");
		sql.append("			 					?,			?,			  ?, 	?,	 ?, sysdate ) ");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getProductname());
			pstmt.setInt(2, dto.getPrice());
			pstmt.setInt(3, dto.getInventory());
			pstmt.setString(4, dto.getProducttype());
			pstmt.setString(5, dto.getUploadbtn());
			pstmt.setString(6, dto.getDetailupload());
			pstmt.setString(7, dto.getProductaddress());
			pstmt.setString(8, dto.getExplanation());
			
			int cnt = pstmt.executeUpdate();
			if(cnt>0) flag=true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(con, pstmt, rs);
		}
		
		return flag;
	}
	
	public List<ShoppingProductDTO> randomlist(){
		List<ShoppingProductDTO> list = new ArrayList<ShoppingProductDTO>();

		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT S.*   ");
		sql.append(" FROM(   ");
		sql.append(" 	SELECT *   ");
		sql.append(" 	FROM shoppingproduct   ");
		sql.append(" 	ORDER BY DBMS_RANDOM.VALUE   ");
		sql.append(" )S   ");
		sql.append(" WHERE ROWNUM<=12   ");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ShoppingProductDTO dto = new ShoppingProductDTO();
				
				dto.setProductid(rs.getInt("productid"));
				dto.setProducttype(rs.getString("producttype"));
				dto.setProductname(rs.getString("productname"));
				dto.setProductaddress(rs.getString("productaddress"));
				dto.setExplanation(rs.getString("explanation"));
				dto.setUploadbtn(rs.getString("uploadbtn"));
				dto.setPrice(rs.getInt("price"));
				dto.setInventory(rs.getInt("inventory"));
				
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
	
	
	public List<ShoppingProductDTO> list(Map map) {
		List<ShoppingProductDTO> list = new ArrayList<ShoppingProductDTO>();
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String col = (String)map.get("col");
		String word = (String)map.get("word");
		
		int sno = (Integer)map.get("sno");
		int eno = (Integer)map.get("eno");
		StringBuffer sql = new StringBuffer();
		
		sql.append("  SELECT productid, producttype, productname, productaddress, explanation, price, inventory, uploadbtn, r  ");
		sql.append("  FROM(  ");
		sql.append("  	SELECT productid, producttype, productname, productaddress, explanation, price, inventory, uploadbtn, rownum r  ");
		sql.append("  	FROM(  ");
		sql.append("  		SELECT productid, producttype, productname,  productaddress, explanation, price, inventory, uploadbtn  ");
		sql.append("  		FROM shoppingproduct  ");
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
				ShoppingProductDTO dto = new ShoppingProductDTO();
				
				dto.setProductid(rs.getInt("productid"));
				dto.setProducttype(rs.getString("producttype"));
				dto.setProductname(rs.getString("productname"));
				dto.setProductaddress(rs.getString("productaddress"));
				dto.setExplanation(rs.getString("explanation"));
				dto.setUploadbtn(rs.getString("uploadbtn"));
				dto.setPrice(rs.getInt("price"));
				dto.setInventory(rs.getInt("inventory"));
				
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
	
	public boolean update(ShoppingProductDTO dto) {
		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE shoppingproduct  ");
		sql.append(" SET producttype=?, productname=?, explanation=?, price=?, inventory =?, uploadbtn=?   ");
		sql.append(" WHERE productid=?  ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getProducttype());
			pstmt.setString(2, dto.getProductname());
			pstmt.setString(3, dto.getExplanation());
			pstmt.setInt(4, dto.getPrice());
			pstmt.setInt(5, dto.getInventory());
			pstmt.setString(6, dto.getUploadbtn());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(con, pstmt);
		}
		
		
		return flag;
	}

    public ShoppingProductDTO read(int productid) {
    	ShoppingProductDTO dto = null;
    	
    	Connection con = DBOpen.open();
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT * FROM shoppingproduct ");
    	sql.append(" WHERE productid = ? ");
    	
    	try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, productid);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ShoppingProductDTO();
				dto.setProductid(rs.getInt("productid"));
				dto.setProducttype(rs.getString("producttype"));
				dto.setProductname(rs.getString("productname"));
				dto.setProductaddress(rs.getString("productaddress"));
				dto.setExplanation(rs.getString("explanation"));
				dto.setPrice(rs.getInt("price"));
				dto.setInventory(rs.getInt("inventory"));
				dto.setUploadbtn(rs.getString("uploadbtn"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt, rs);
		}
    	
    	return dto;
    }
	
	
}