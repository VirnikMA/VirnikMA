package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.OrderRepository;
import com.example.springsecurityapplication.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Данный метод позволяет получить все ордера
    public List<Order> getAllOrder(){return orderRepository.findAll();}

    //Данный метод позволяет получить шапки всех ордеров
    public List<String> getNumberOrder(){return orderRepository.GroupByOrderNumber();}

//без выпендрежа, все скопом
//    public Order getOrderFindByTitle(Order order){
//        Optional<Order> order_db = orderRepository.findByTitle(product.getTitle());
//        return order_db.orElse(null);
//    }

    // Данный метод позволяет получить строку ордера по id
    public Order getOrderById(int id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    public List<Order> findByFourChar(String str){return orderRepository.findByFourChar(str);}

    // Данный метод позволяет обновить статус строки ордера
    @Transactional
    public void updateStatus(Status status, Order order){
        order.setStatus(status);
        orderRepository.save(order);
    }
}