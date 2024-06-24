package com.vendavaultecommerceproject.notification.service.impl.admin;


import com.vendavaultecommerceproject.admin.entity.AdminRegistrationEntity;
import com.vendavaultecommerceproject.admin.service.main.AdminRegistrationService;
import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.notification.dto.SaveNotificationDto;
import com.vendavaultecommerceproject.notification.entity.admin.AdminNotificationEntity;
import com.vendavaultecommerceproject.notification.entity.user.UserNotificationEntity;
import com.vendavaultecommerceproject.notification.model.user.UserNotificationModel;
import com.vendavaultecommerceproject.notification.repository.admin.AdminNotificationRepository;
import com.vendavaultecommerceproject.notification.service.main.admin.AdminNotificationService;
import com.vendavaultecommerceproject.utils.UserNotificationModelUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminNotificationServiceImpl implements AdminNotificationService {

    private final AdminNotificationRepository adminNotificationRepository;
    private final AdminRegistrationService adminRegistrationService;
    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(AdminNotificationServiceImpl.class);
    @Override
    public void saveNotification(SaveNotificationDto saveNotificationDto) throws DataNotFoundException {

        AdminRegistrationEntity admin = adminRegistrationService.findAdminByEmail(saveNotificationDto.getEmail());
        AdminNotificationEntity adminNotificationEntity = AdminNotificationEntity.builder()
                .title(saveNotificationDto.getTitle())
                .message(saveNotificationDto.getMessage())
                .isRead(false)
                .dateCreated(new Date())
                .dateRead(null)
                .admin(admin)
                .build();
        adminNotificationRepository.save(adminNotificationEntity);
        messagingTemplate.convertAndSendToUser(admin.getEmail(), "/queue/notifications", adminNotificationEntity.getMessage());
        logger.info("Notification sent via WebSocket to user " + adminNotificationEntity.getAdmin().getEmail());
    }

    @Override
    public void readNotification(Long notificationId) throws DataNotFoundException {

        if (Objects.isNull(adminNotificationRepository.findById(notificationId))) {
            throw new DataNotFoundException("Notification Id not found");
        }
        AdminNotificationEntity adminNotificationEntity = adminNotificationRepository.findById(notificationId).get();
        adminNotificationEntity.setRead(true);
        adminNotificationEntity.setDateRead(new Date());
        adminNotificationRepository.save(adminNotificationEntity);
    }

    @Override
    public ResponseEntity<List<AdminNotificationEntity>> getAllUnreadNotification(RetrieveUserDto userDto) throws DataNotFoundException {
        AdminRegistrationEntity admin = adminRegistrationService.findAdminByEmail(userDto.getEmail());
        if (Objects.isNull(admin)) {
            throw new DataNotFoundException("No notification for the provided email");
        }
        List<AdminNotificationEntity> notificationModels = new ArrayList<>();
        List<AdminNotificationEntity> notificationEntityList = adminNotificationRepository.findByAdmin(admin);
        for (AdminNotificationEntity adminNotificationEntity : notificationEntityList) {
            if (!adminNotificationEntity.isRead()) {
                notificationModels.add(adminNotificationEntity);
            }
        }
        return new ResponseEntity<>(notificationModels, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AdminNotificationEntity>> getAllAdminNotification(RetrieveUserDto userDto) throws DataNotFoundException {

        AdminRegistrationEntity admin = adminRegistrationService.findAdminByEmail(userDto.getEmail());
        if (Objects.isNull(admin)) {
            throw new DataNotFoundException("No notification for the provided email");
        }
        List<AdminNotificationEntity> notificationEntityList = adminNotificationRepository.findByAdmin(admin);

        return new ResponseEntity<>(notificationEntityList, HttpStatus.OK);

    }
}
