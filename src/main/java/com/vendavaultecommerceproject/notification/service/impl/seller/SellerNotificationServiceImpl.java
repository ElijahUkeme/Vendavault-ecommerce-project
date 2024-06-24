package com.vendavaultecommerceproject.notification.service.impl.seller;


import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.notification.dto.SaveNotificationDto;
import com.vendavaultecommerceproject.notification.entity.seller.SellerNotificationEntity;
import com.vendavaultecommerceproject.notification.entity.user.UserNotificationEntity;
import com.vendavaultecommerceproject.notification.model.seller.SellerNotificationModel;
import com.vendavaultecommerceproject.notification.model.user.UserNotificationModel;
import com.vendavaultecommerceproject.notification.repository.seller.SellerNotificationRepository;
import com.vendavaultecommerceproject.notification.service.impl.user.UserNotificationServiceImpl;
import com.vendavaultecommerceproject.notification.service.main.seller.SellerNotificationService;
import com.vendavaultecommerceproject.service.main.seller.SellerService;
import com.vendavaultecommerceproject.utils.SellerNotificationModelUtil;
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
public class SellerNotificationServiceImpl implements SellerNotificationService {

    private final SellerNotificationRepository sellerNotificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final SellerService sellerService;
    private static final Logger logger = LoggerFactory.getLogger(SellerNotificationServiceImpl.class);

    @Override
    public void saveNotification(SaveNotificationDto saveNotificationDto) throws DataNotFoundException {
        SellerEntity seller = sellerService.findSellerByEmail(saveNotificationDto.getEmail());
        if (Objects.isNull(seller)) {
            throw new DataNotFoundException("Seller email address not found");
        }

        SellerNotificationEntity sellerNotificationEntity = SellerNotificationEntity.builder()
                .title(saveNotificationDto.getTitle())
                .message(saveNotificationDto.getMessage())
                .isRead(false)
                .dateCreated(new Date())
                .dateRead(null)
                .seller(seller)
                .build();
        sellerNotificationRepository.save(sellerNotificationEntity);

        messagingTemplate.convertAndSendToUser(seller.getEmail(), "/queue/notifications", sellerNotificationEntity.getMessage());
        logger.info("Notification sent via WebSocket to user " + sellerNotificationEntity.getSeller().getEmail());
    }

    @Override
    public void readNotification(Long notificationId) throws DataNotFoundException {

        if (Objects.isNull(sellerNotificationRepository.findById(notificationId))) {
            throw new DataNotFoundException("Notification Id not found");
        }
        SellerNotificationEntity sellerNotificationEntity = sellerNotificationRepository.findById(notificationId).get();
        sellerNotificationEntity.setRead(true);
        sellerNotificationEntity.setDateRead(new Date());
        sellerNotificationRepository.save(sellerNotificationEntity);
    }

    @Override
    public ResponseEntity<List<SellerNotificationModel>> getAllUnreadNotification(RetrieveUserDto userDto) throws DataNotFoundException {

        SellerEntity seller = sellerService.findSellerByEmail(userDto.getEmail());
        if (Objects.isNull(seller)) {
            throw new DataNotFoundException("No notification for the provided email");
        }
        List<SellerNotificationModel> notificationModels = new ArrayList<>();
        List<SellerNotificationEntity> sellerNotificationEntities = sellerNotificationRepository.findBySeller(seller);
        for (SellerNotificationEntity sellerNotification : sellerNotificationEntities) {
            if (!sellerNotification.isRead()) {
                notificationModels.add(SellerNotificationModelUtil.getReturnedSellerNotificationModel(sellerNotification));
            }
        }
        return new ResponseEntity<>(notificationModels, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SellerNotificationModel>> getAllSellerNotification(RetrieveUserDto userDto) throws DataNotFoundException {
        SellerEntity seller = sellerService.findSellerByEmail(userDto.getEmail());
        if (Objects.isNull(seller)) {
            throw new DataNotFoundException("No notification for the provided email");
        }
        List<SellerNotificationModel> notificationModels = new ArrayList<>();
        List<SellerNotificationEntity> sellerNotificationEntities = sellerNotificationRepository.findBySeller(seller);
        for (SellerNotificationEntity sellerNotification : sellerNotificationEntities) {
            notificationModels.add(SellerNotificationModelUtil.getReturnedSellerNotificationModel(sellerNotification));

        }
        return new ResponseEntity<>(notificationModels, HttpStatus.OK);
    }
}
