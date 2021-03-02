package shoppingreview;

public class ShoppingReviewDTO {

	    int productID;
	    int reviewnum;
	    String email;
	    String name;
	    String content;
	   	String mdate;
	   	
		public int getProductID() {
			return productID;
		}
		public void setProductID(int productID) {
			this.productID = productID;
		}
		public int getReviewnum() {
			return reviewnum;
		}
		public void setReviewnum(int reviewnum) {
			this.reviewnum = reviewnum;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getMdate() {
			return mdate;
		}
		public void setMdate(String mdate) {
			this.mdate = mdate;
		}
	
}
