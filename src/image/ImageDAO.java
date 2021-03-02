package image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.DBClose;
import db.DBOpen;
import image.ImageDTO;


public class ImageDAO {
	
	public boolean updateFile(Map map) {
		
		boolean flag = false;

		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		
		String fname = (String) map.get("fname");
		int imgno = (int) map.get("imgno");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE image  ");
		sql.append(" SET 	fname=?  ");
		sql.append(" WHERE 	imgno=?  ");


		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, fname);
			pstmt.setInt(2, imgno);
			
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
	
	public List imgRead(int imgno){
		List list = new ArrayList();
		Connection con = DBOpen.open();
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM  ");
		sql.append("   (  ");
		sql.append("      select    ");
		sql.append("          lag(imgno,2)     over (order by imgno) pre_imgno2,    ");
		sql.append("          lag(imgno,1)     over (order by imgno ) pre_imgno1,   ");
		sql.append("          imgno,  ");
		sql.append("          lead(imgno,1)    over (order by imgno) nex_imgno1,    ");
		sql.append("          lead(imgno,2)    over (order by imgno) nex_imgno2,    ");
		sql.append("          lag(fname,2)  over (order by imgno) pre_file2,     ");
		sql.append("          lag(fname,1)  over (order by imgno) pre_file1,  ");
		sql.append("          fname,   ");
		sql.append("          lead(fname,1) over (order by imgno) nex_file1,  ");
		sql.append("          lead(fname,2) over (order by imgno) nex_file2   ");
		sql.append("          from (  ");
		sql.append("               SELECT imgno, fname   ");
		sql.append("               FROM image ");
		sql.append("               ORDER BY imgno DESC  ");
		sql.append("          )  ");
		sql.append("   )  ");
		sql.append("   WHERE imgno = ? ");
		
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, imgno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				int[] noArr = 
					   {
						rs.getInt("pre_imgno2"),
						rs.getInt("pre_imgno1"),
						rs.getInt("imgno"),
						rs.getInt("nex_imgno1"),
						rs.getInt("nex_imgno2")
					    };
				String[] files = 
					    {
						rs.getString("pre_file2"),
						rs.getString("pre_file1"),
						rs.getString("fname"),
						rs.getString("nex_file1"),
						rs.getString("nex_file2")
						};
				
				list.add(files);
				list.add(noArr);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBClose.close(con, pstmt, rs);
		}
				
		return list;
	}
	
	public boolean update(ImageDTO dto) { // 데이터를 다 가져와서 한 건씩 바꾸다.
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE image ");
		sql.append(" SET 	name = ?	, ");
		sql.append(" 		title = ?	, ");
		sql.append("		contents = ?  ");
		sql.append(" WHERE	imgno = ? ");

		try {
			int i = 0;
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setString(++i, dto.getName());
			pstmt.setString(++i, dto.getTitle());
			pstmt.setString(++i, dto.getContents());

			pstmt.setInt(++i, dto.getImgno());

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
	
	public boolean checkPasswd(int imgno, String chkpasswd) {

		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String passwd = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT passwd ");
		sql.append(" FROM	image ");
		sql.append(" WHERE imgno=? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, imgno);

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
	
	public boolean delete(int imgno) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();

		sql.append(" DELETE FROM image");
		sql.append(" WHERE imgno = ?");

		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, imgno);

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
	
	public boolean checkRefnum(int imgno) {
		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		/*null이 아닌 refnum을 다 count 해 준다. 다 0이 들어가 있는데 하나만 REFNUM을 가져온다.
		비교할때 부모글인지 아닌지만 확인한다.
	
		*/
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT 	count(refnum)");
		sql.append(" FROM 		image");
		sql.append(" WHERE 		refnum = ? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, imgno);
			
			rs = pstmt.executeQuery(); // 쿼리문 전송을 함
			
			if(rs.next()) {
				int cnt = rs.getInt(1);
				if (cnt>0) flag = true; // 부모글이다. Deleteform에서 flag 값이 true 일 때 부모글이므로 삭제할 수 없다는 말이 뜬다.
 			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt, rs);
		}
		
		return flag;
	}
	
	public void upViewcnt(int imgno) {
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();

		sql.append(" UPDATE image		");
		sql.append(" SET				");
		sql.append(" viewcnt = viewcnt + 1");
		sql.append(" WHERE  imgno = ?");

		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, imgno);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt);
		}
	}
	
	public ImageDTO read(int imgno) {
		ImageDTO dto = null;

		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT 			");
		sql.append(" 		imgno, name, title, contents, passwd, viewcnt, fname, mdate ");
		sql.append(" FROM	image			");
		sql.append(" WHERE 	imgno = ?	");

		try {
			pstmt = con.prepareStatement(sql.toString());

			// 값을 연결하면 된다.
			pstmt.setInt(1, imgno);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ImageDTO();

				dto.setImgno(rs.getInt("imgno"));
				dto.setViewcnt(rs.getInt("viewcnt"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContents(rs.getString("contents"));
				dto.setMdate(rs.getString("mdate"));
				dto.setFname(rs.getString("fname"));
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(con, pstmt, rs);
		}

		return dto;
	}
	
	public boolean create(ImageDTO dto) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" INSERT INTO image	(imgno, 		name, 		title,			contents,		passwd, ");
		sql.append(" 					mdate,		fname)");
		sql.append(" VALUES( (SELECT NVL(MAX(imgno),0) + 1 as imgno FROM image),  ");
		sql.append(" 					      		?,		    ?,    			?,		    	?,		");
		sql.append(" 				    sysdate,    ?)");
		
	
		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContents());
			pstmt.setString(4, dto.getPasswd());
			pstmt.setString(5, dto.getFname());
			
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
	
	public int total(Map map) { // 맵 타입의 맵
		int total = 0;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 레코드 갯수를 가져온다.(생성, 수정, 삭제하는 것이 아니기 때문에)

		String col = (String) map.get("col");
		String word = (String) map.get("word");

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) FROM image");
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
	
	public List<ImageDTO> list(Map map) {
		List<ImageDTO> list = new ArrayList<ImageDTO>();
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String col = (String)map.get("col");
		String word = (String)map.get("word");
		
		int sno = (Integer)map.get("sno");
		int eno = (Integer)map.get("eno");
		StringBuffer sql = new StringBuffer();
		
		sql.append("  SELECT imgno, name, title, contents, passwd, viewcnt, fname, grpno, indent, ansnum, r  ");
		sql.append("  FROM(  ");
		sql.append("  	SELECT imgno, name, title, contents, passwd, viewcnt, fname, grpno, indent, ansnum, rownum r  ");
		sql.append("  	FROM(  ");
		sql.append("  		SELECT imgno, name, title, contents, passwd, viewcnt, fname, grpno, indent, ansnum  ");
		sql.append("  		FROM image  ");
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
				ImageDTO dto = new ImageDTO();
				
				dto.setImgno(rs.getInt("imgno"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContents(rs.getString("contents"));
				dto.setPasswd(rs.getString("passwd"));
				dto.setViewcnt(rs.getInt("viewcnt"));
				dto.setFname(rs.getString("Fname"));
				dto.setGrpno(rs.getInt("grpno"));
				dto.setIndent(rs.getInt("indent"));
				dto.setAnsnum(rs.getInt("ansnum"));
				
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
