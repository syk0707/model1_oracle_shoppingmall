package directpay;

public class DirectpayDTO {
	
	int directID; 
	String email;
	String productname;
	String productaddress;
	int numbers; 
	int price; 
	int productsum;
	
	public int getDirectID() {
		return directID;
	}
	public void setDirectID(int directID) {
		this.directID = directID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProductaddress() {
		return productaddress;
	}
	public void setProductaddress(String productaddress) {
		this.productaddress = productaddress;
	}
	public int getNumbers() {
		return numbers;
	}
	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getProductsum() {
		return productsum;
	}
	public void setProductsum(int productsum) {
		this.productsum = productsum;
	} 
}
