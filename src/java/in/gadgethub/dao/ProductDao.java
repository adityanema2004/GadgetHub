
package in.gadgethub.dao;

import in.gadgethub.pojo.ProductPojo;
import java.util.List;

public interface ProductDao {
    String addProduct(ProductPojo product);
    String updateProduct(ProductPojo preProduct , ProductPojo updateProduct);
    String updateProductPrice(String prodId,double updatePrice);
    List<ProductPojo> getAllProducts();
    List<ProductPojo> getAllProductByType(String type);
    List<ProductPojo>searchAllProducts(String search);
    ProductPojo getProductDetails(String prodId);
    int getProductQuantity(String prodId);
    String updateProductWithoutImage(String preProductId,ProductPojo updateProduct);
    double getProductPrice(String prodId);
    Boolean sellNProduct(String prodId,int n);
    List<String>getAllProductsType();
    byte[]getImage(String prodId);
    String removeProduct(String prodId);
    
}
