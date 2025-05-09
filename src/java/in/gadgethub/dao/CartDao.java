package in.gadgethub.dao;

import in.gadgethub.pojo.UsercartPojo;
import java.util.List;

public interface CartDao {
    String addProductToCart(UsercartPojo cart);
    String updateProductInCart(UsercartPojo cart);
    List<UsercartPojo>getAllCartItems(String userId);
    int getCartItemCount(String userId,String itemId);
    String removeProductFromCart(String userId, String prodId);
    Boolean removeAProduct(String userId,String prodId);
}
