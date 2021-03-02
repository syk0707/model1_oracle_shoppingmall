package shoppingreview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingReviewTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ShoppingReviewDAO dao = new ShoppingReviewDAO();
		
		checkId(dao);

	}


	private static void checkId(ShoppingReviewDAO dao) {
		
		String email = "admin";
;		int reviewnum = 7;
		
		if(dao.checkId(email, reviewnum)) {
			p("성공");
		}else {
			p("실패");
		}
		

	}


	private static void p(String string) {
		// TODO Auto-generated method stub
		System.out.println(string);
	}

	private static void p(ShoppingReviewDTO dto) {
		// TODO Auto-generated method stub

	}
	
	

}
