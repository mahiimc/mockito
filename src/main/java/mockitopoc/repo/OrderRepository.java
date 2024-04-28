package mockitopoc.repo;

import java.util.List;

import mockitopoc.dto.Order;

public class OrderRepository {

    public List<Order> getOrders(String productCode) {
        return List.of();
    }

    public List<Order> getAllOrders() {
        return List.of();
    }
}