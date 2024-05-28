package com.vendavaultecommerceproject.response.pagination;

import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.model.product.ProductModel;

import java.util.List;

public record ProductPageResponse(List<ProductModel>ProductModel,
                                  Integer pageNumber, Integer pageSize,
                                  int totalPages,
                                  int totalElements,
                                  boolean isLast) {
}
