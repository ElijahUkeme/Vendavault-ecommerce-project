package com.vendavaultecommerceproject.service.impl.order;

import com.vendavaultecommerceproject.entities.order.OrderTokenEntity;
import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.repository.order.OrderTokenRepository;
import com.vendavaultecommerceproject.service.main.order.OrderTokenService;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class OrderTokenServiceImpl implements OrderTokenService {

    private final OrderTokenRepository orderTokenRepository;

    public OrderTokenServiceImpl(OrderTokenRepository orderTokenRepository) {
        this.orderTokenRepository = orderTokenRepository;
    }

    @Override
    public void saveOrderToken(OrderTokenEntity orderTokenEntity) {
        orderTokenRepository.save(orderTokenEntity);
    }

    @Override
    public SaleEntity getOrderToken(String token) throws DataNotFoundException {

        OrderTokenEntity orderToken = orderTokenRepository.findByToken(token);
        if (Objects.nonNull(orderToken)){
            return orderToken.getOrder();
        }
        return null;
    }
}
