package shoppingproduct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class ShoppingProductService {
    
    private ShoppingProductDAO productDataAccess;
    
    public ShoppingProductService() {
        productDataAccess = new ShoppingProductDAO();
    }
    
    public List<ShoppingProductDTO> list(Map map) {
        List<ShoppingProductDTO> products = null;
        try {
            products = productDataAccess.list(map);
        } catch (Exception e) {
            products = null;
        }
        return products;
    }
    
    public ArrayList<Product> getProduct(String productname) {
        ArrayList<Product> products = null;
        try {
            products = productDataAccess.productRetrieve(productname);
        } catch (Exception e) {
            products = null;
        }
        return products;
    }
    
    public void insertProduct(String producttype, String productname, String explanation, int price, int inventory) {
        productDataAccess.productInsert(producttype, productname, explanation, price, inventory);
    }
    
    public void updateProduct(int productid, String producttype, String productname, String explanation, int price, int inventory) {
        productDataAccess.productUpdate(productid, producttype, productname, explanation, price, inventory);
    }
}