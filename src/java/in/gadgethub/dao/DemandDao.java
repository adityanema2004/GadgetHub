package in.gadgethub.dao;

import in.gadgethub.pojo.DemandPojo;
import java.util.List;

public interface DemandDao {
    boolean addProduct(DemandPojo demandPojo);
    boolean removeProduct(String userId,String prodId);
    List<DemandPojo> haveDemanded(String prodId);
    
}
