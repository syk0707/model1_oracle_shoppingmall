package shoppingproduct;

public class ShoppingProductDTO {

    String producttype;
    String productname;
    String explanation;
    String uploadbtn;
	String productaddress;
	String detailupload;
	int price;
	int inventory;
	int productid;
	
    public String getDetailupload() {
		return detailupload;
	}
	public void setDetailupload(String detailupload) {
		this.detailupload = detailupload;
	}
	public String getProductaddress() {
		return productaddress;
	}
	public void setProductaddress(String productaddress) {
		this.productaddress = productaddress;
	}

    public String getUploadbtn() {
		return uploadbtn;
	}
	public void setUploadbtn(String uploadbtn) {
		this.uploadbtn = uploadbtn;
	}

    public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public int getProductid() {
        return productid;
    }

}
