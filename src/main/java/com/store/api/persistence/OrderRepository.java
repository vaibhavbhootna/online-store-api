package com.store.api.persistence;

import com.store.api.persistence.model.OrderDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderDO, Long> {

    public OrderDO save(OrderDO orderDO);
    public List<OrderDO> findAll();
}
