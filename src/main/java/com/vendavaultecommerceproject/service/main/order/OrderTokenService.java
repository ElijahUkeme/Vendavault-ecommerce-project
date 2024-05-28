package com.vendavaultecommerceproject.service.main.order;

import com.vendavaultecommerceproject.entities.order.OrderTokenEntity;
import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.entities.token.UserAuthenticationTokenEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;

public interface OrderTokenService {

    public void saveOrderToken(OrderTokenEntity orderTokenEntity);
    public SaleEntity getOrderToken(String token) throws DataNotFoundException;
}
