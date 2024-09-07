package com.vendavaultecommerceproject.repository.sale;

import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface SaleRepository extends JpaRepository<SaleEntity,Long> {

    public List<SaleEntity> findByDatePurchasedBetween(LocalDate from,LocalDate to);

    @Query("SELECT s FROM SaleEntity s WHERE YEAR(s.datePurchased) = :year AND MONTH(s.datePurchased) = :month")
    List<SaleEntity> findSalesByMonthAndYear(@Param("year") int year, @Param("month") int month);


}
