package in.gadgethub.dao;

import in.gadgethub.pojo.OrderDetailsPojo;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.TransactionPojo;
import java.util.List;

public interface OrderDao {
    boolean addOrders(OrderPojo order);
    boolean addTransaction(TransactionPojo transaction);
    List<OrderPojo>getAllOrders();
    List<OrderDetailsPojo> getAllOrderDetails(String userEmailId);
    String shipNow(String orderId,String prodId);
    String paymentSuccess(String username,double paidAmount);
    public int getSoldQuantity(String prodId);
}
