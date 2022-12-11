package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByPerson(Person person);

    //номера заказов
    @Query(value = "select number from orders group by number", nativeQuery = true)
    List<String> GroupByOrderNumber();

    @Query(value = "select * from orders where (lower(number)) LIKE %?1 order by id;"  , nativeQuery = true)
    List<Order> findByFourChar(String str);

//    // Поиск по части номера заказа
//    @Query(value = "select number from orders where ((lower(number) LIKE %?1%) or (lower(number) LIKE '?1%'))  order by number",
//            nativeQuery = true)
//    List<String> FindByStringOrder(String title);

}
