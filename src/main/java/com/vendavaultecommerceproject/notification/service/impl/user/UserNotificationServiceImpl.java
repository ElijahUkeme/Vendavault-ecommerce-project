package com.vendavaultecommerceproject.notification.service.impl.user;


import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.notification.dto.SaveNotificationDto;
import com.vendavaultecommerceproject.notification.entity.user.UserNotificationEntity;
import com.vendavaultecommerceproject.notification.model.NotificationModel;
import com.vendavaultecommerceproject.notification.model.user.UserNotificationModel;
import com.vendavaultecommerceproject.notification.repository.user.UserNotificationRepository;
import com.vendavaultecommerceproject.notification.service.main.user.UserNotificationService;
import com.vendavaultecommerceproject.service.main.user.UserService;
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
public class UserNotificationServiceImpl implements UserNotificationService {

    private final UserNotificationRepository userNotificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserNotificationServiceImpl.class);


    @Override
    public void saveNotification(SaveNotificationDto saveNotificationDto) throws DataNotFoundException {
        UserEntity user = userService.findUserByEmail(saveNotificationDto.getEmail());
        if (Objects.isNull(user)) {
            throw new DataNotFoundException("User email not found");
        }

        UserNotificationEntity userNotificationEntity = UserNotificationEntity.builder()
                .title(saveNotificationDto.getTitle())
                .message(saveNotificationDto.getMessage())
                .isRead(false)
                .dateCreated(new Date())
                .dateRead(null)
                .user(user)
                .build();
        userNotificationRepository.save(userNotificationEntity);
        messagingTemplate.convertAndSendToUser(user.getEmail(), "/queue/notifications", userNotificationEntity.getMessage());
        logger.info("Notification sent via WebSocket to user " + userNotificationEntity.getUser().getEmail());
    }

    @Override
    public void readNotification(Long notificationId) throws DataNotFoundException {
        if (Objects.isNull(userNotificationRepository.findById(notificationId))) {
            throw new DataNotFoundException("Notification Id not found");
        }
        UserNotificationEntity userNotificationEntity = userNotificationRepository.findById(notificationId).get();
        userNotificationEntity.setRead(true);
        userNotificationEntity.setDateRead(new Date());
        userNotificationRepository.save(userNotificationEntity);
    }

    @Override
    public ResponseEntity<List<UserNotificationModel>> getAllUnreadNotification(RetrieveUserDto userDto) throws DataNotFoundException {
        UserEntity user = userService.findUserByEmail(userDto.getEmail());
        if (Objects.isNull(user)) {
            throw new DataNotFoundException("No notification for the provided email");
        }
        List<UserNotificationModel> notificationModels = new ArrayList<>();
        List<UserNotificationEntity> userNotificationEntities = userNotificationRepository.findByUser(user);
        for (UserNotificationEntity userNotification : userNotificationEntities) {
            if (!userNotification.isRead()) {
                notificationModels.add(UserNotificationModelUtil.getReturnedUserNotificationModel(userNotification));
            }
        }
        return new ResponseEntity<>(notificationModels, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<UserNotificationModel>> getAllUserNotification(RetrieveUserDto userDto) throws DataNotFoundException {
        UserEntity user = userService.findUserByEmail(userDto.getEmail());
        if (Objects.isNull(user)) {
            throw new DataNotFoundException("No notification for the provided email");
        }
        List<UserNotificationModel> notificationModels = new ArrayList<>();
        List<UserNotificationEntity> userNotificationEntities = userNotificationRepository.findByUser(user);
        for (UserNotificationEntity userNotification : userNotificationEntities) {
            notificationModels.add(UserNotificationModelUtil.getReturnedUserNotificationModel(userNotification));
        }
        return new ResponseEntity<>(notificationModels, HttpStatus.OK);

    }
}
