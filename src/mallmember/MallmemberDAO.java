package mallmember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import utility.Utility;
import db.DBClose;
import db.DBOpen;
import mallmember.MallmemberDTO;


public class MallmemberDAO {
	
	public boolean updatePasswd(String email, String npasswd) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE mallmember ");
		sql.append(" SET	password=?");
		sql.append(" WHERE	email = ? ");

		try {
			pstmt = con.prepareStatement(sql.toString());
			int i=1;
			pstmt.setString(i++, npasswd);
			pstmt.setString(i++, email);

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
	
	public boolean checkPasswd(String email, String chkpasswd) {

		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String password = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT password ");
		sql.append(" FROM	mallmember ");
		sql.append(" WHERE email=? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, email);

			rs=pstmt.executeQuery();
			if(rs.next()) {
				password = rs.getString(1);
			}
			if (password.equals(chkpasswd))flag = true;
			
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
	
	public boolean update(MallmemberDTO dto) {
		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		String zipcode = Utility.checkNull(dto.getZipcode());
		String address1 = Utility.checkNull(dto.getAddress1());
		String address2 = Utility.checkNull(dto.getAddress2());
		
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE mallmember  ");
		sql.append(" SET phone=?, zipcode=?, address1=?, address2=?   ");
		sql.append(" WHERE email=?  ");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			

			pstmt.setString(1, dto.getPhone());
			pstmt.setString(2, zipcode);
			pstmt.setString(3, address1);
			pstmt.setString(4, address2);
			pstmt.setString(5, dto.getEmail());

			
			int cnt = pstmt.executeUpdate();
			
			
			
			if(cnt>0) {
				flag=true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt);
		}
		return flag;
	}

	
	public boolean delete(String email) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM mallmember   ");
		sql.append(" WHERE email = ?   ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			
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
	
	public MallmemberDTO read(String email) {
		MallmemberDTO dto = null;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM mallmember");
		sql.append(" WHERE email = ?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MallmemberDTO();

				dto.setName(rs.getString("name"));
				dto.setPhone(rs.getString("phone"));
				dto.setEmail(rs.getString("email"));
				dto.setZipcode(rs.getString("zipcode"));
				dto.setAddress1(rs.getString("address1"));
				dto.setAddress2(rs.getString("address2"));
				dto.setMdate(rs.getString("mdate"));
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
		sql.append(" SELECT count(*) FROM mallmember");
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
	
	public List<MallmemberDTO> list(Map map) {
		List<MallmemberDTO> list = new ArrayList<MallmemberDTO>();
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String col = (String)map.get("col");
		String word = (String)map.get("word");
		
		int sno = (Integer)map.get("sno");
		int eno = (Integer)map.get("eno");
		StringBuffer sql = new StringBuffer();
		
		sql.append("  SELECT name, email, phone, r  ");
		sql.append("  FROM(  ");
		sql.append("  	SELECT name, email, phone, rownum r  ");
		sql.append("  	FROM(  ");
		sql.append("  		SELECT name, email, phone  ");
		sql.append("  		FROM mallmember  ");
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
				MallmemberDTO dto = new MallmemberDTO();
				dto.setName(rs.getString("name"));
				dto.setPhone(rs.getString("phone"));
				dto.setEmail(rs.getString("email"));
				
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
	
	public boolean loginCheck(Map map) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String email = (String)map.get("email");
		String password = (String)map.get("password");
		
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) FROM mallmember ");
		sql.append(" WHERE email=? ");
		sql.append(" AND password=? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int cnt = rs.getInt(1);
				if(cnt>0) flag= true;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 finally {
		DBClose.close(con, pstmt, rs);
	}
	return flag;
	}
	
	public String getGrade(String email) {
		String grade = null;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT grade FROM mallmember ");
		sql.append(" WHERE email = ? ");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1,email);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				grade = rs.getString("grade");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt, rs);
		}
		return grade;
	}
	
	public boolean duplicateEmail(String email) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT COUNT(email)");
		sql.append(" FROM mallmember");
		sql.append(" WHERE email=?");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				int cnt = rs.getInt(1);
				if(cnt>0) {
					flag = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt, rs);
		}
		
		
		return flag;
	}
	
	public boolean create(MallmemberDTO dto) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" INSERT INTO 		mallmember(name,	email,		password, ");
		sql.append(" 					mdate,		phone,				grade)");
		sql.append(" VALUES			 			 (	?,		?,			?,		");
		sql.append(" 					sysdate,	?,					'H')");
		
	
		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getEmail());
			pstmt.setString(3, dto.getPassword());
			pstmt.setString(4, dto.getPhone());
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt>0) flag=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt);
		}
		return flag;
	}
	
}
