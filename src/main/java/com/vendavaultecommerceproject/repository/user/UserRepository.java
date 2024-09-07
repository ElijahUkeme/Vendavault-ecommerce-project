package com.vendavaultecommerceproject.repository.user;

import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.entities.video.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByEmail(String email);

    List<UserEntity> findByCreatedDateBetween(LocalDate from, LocalDate to);

    @Query("SELECT s FROM UserEntity s WHERE YEAR(s.createdDate) = :year AND MONTH(s.createdDate) = :month")
    List<UserEntity> findUsersByMonthAndYearCreated(@Param("year") int year, @Param("month") int month);

}
