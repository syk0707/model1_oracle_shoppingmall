package basket;

public class BasketDTO {
	String email;
	String productname;
	String productaddress;
	String uploadbtn;
	int price;
	int numbers;
	int productsum;
	
	
	public String getUploadbtn() {
		return uploadbtn;
	}
	public void setUploadbtn(String uploadbtn) {
		this.uploadbtn = uploadbtn;
	}
	public int getProductsum() {
		return productsum;
	}
	public void setProductsum(int productsum) {
		this.productsum = productsum;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getNumbers() {
		return numbers;
	}
	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}
	
	
	
}
