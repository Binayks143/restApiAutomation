package ecomSystemFlow.pojo;

import java.util.List;

public class OrdersList {
    //Here order contains list of orders as per json it is accepting list of orders
    private List<OrderDetails> orders;

    public List<OrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        this.orders = orders;
    }


}
