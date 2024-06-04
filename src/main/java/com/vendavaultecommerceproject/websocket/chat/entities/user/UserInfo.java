package com.vendavaultecommerceproject.websocket.chat.entities.user;


import com.vendavaultecommerceproject.websocket.chat.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserInfo {

    @Id
    private String userName;
    private String fullName;
    private Status status;
}
