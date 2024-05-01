package com.LMS.service;


import com.LMS.entity.Order;
import com.LMS.utils.ApiResponse;

import java.util.List;

public interface OrderService {
    ApiResponse saveOrder(Order order);
    List<Order> getOrdersByStatus(String status);
    ApiResponse updateOrderDetails(Long orderId, String status, Long driverId);


    ApiResponse getOrdersByDriverId(Long driverId);
}
