package com.vendavaultecommerceproject.service.impl.delivery;


import com.vendavaultecommerceproject.dto.delivery.DeleteDeliveryPersonDto;
import com.vendavaultecommerceproject.dto.delivery.DeliveryPersonDto;
import com.vendavaultecommerceproject.dto.delivery.ViewDeliveryPersonDto;
import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.entities.delivery.DeliveryPersonEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.exception.exeception.DataAlreadyExistException;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.model.delivery.DeliveryPersonModel;
import com.vendavaultecommerceproject.repository.delivery.DeliveryPersonRepository;
import com.vendavaultecommerceproject.service.main.delivery.DeliveryPersonService;
import com.vendavaultecommerceproject.service.main.seller.SellerService;
import com.vendavaultecommerceproject.utils.DeliveryPersonModelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DeliveryPersonServiceImpl implements DeliveryPersonService {
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final SellerService service;
    @Override
    public ResponseEntity<String> addDeliveryPerson(DeliveryPersonDto deliveryPersonDto) throws DataNotFoundException, DataAlreadyExistException, DataNotAcceptableException {
        SellerEntity seller = service.findSellerByEmail(deliveryPersonDto.getSellerEmail());
        if (Objects.isNull(seller)){
            throw new DataNotFoundException("No seller with the provided email");
        }
        if (deliveryPersonDto.getPhoneNumber().isEmpty() || deliveryPersonDto.getPhoneNumber().isBlank()){
            throw new DataNotAcceptableException("Phone Number must be provided");
        }
        if (deliveryPersonDto.getName().isEmpty() || deliveryPersonDto.getName().isBlank()){
            throw new DataNotAcceptableException("Name must be provided");
        }
        if (deliveryPersonDto.getAddress().isEmpty() || deliveryPersonDto.getAddress().isBlank()){
            throw new DataNotAcceptableException("Address must be provided");
        }
        if (deliveryPersonDto.getVehicleType().isEmpty() || deliveryPersonDto.getVehicleType().isBlank()){
            throw new DataNotAcceptableException("Vehicle type must be provided");
        }
        if (deliveryPersonDto.getVehicleNumber().isEmpty() || deliveryPersonDto.getVehicleNumber().isBlank()){
            throw new DataNotAcceptableException("Vehicle Number must be provided");
        }
        if (Objects.nonNull(deliveryPersonRepository.findByPhoneNumber(deliveryPersonDto.getPhoneNumber()))){
            throw new DataAlreadyExistException("Phone Number Already used by another delivery person");
        }
        if (Objects.nonNull(deliveryPersonRepository.findByVehicleNumber(deliveryPersonDto.getVehicleNumber()))){
            throw new DataAlreadyExistException("Vehicle Number Already used by another delivery person");
        }

        DeliveryPersonEntity deliveryPersonEntity = DeliveryPersonEntity.builder()
                .seller(seller)
                .vehicleNumber(deliveryPersonDto.getVehicleNumber())
                .address(deliveryPersonDto.getAddress())
                .name(deliveryPersonDto.getName())
                .phoneNumber(deliveryPersonDto.getPhoneNumber())
                .vehicleType(deliveryPersonDto.getVehicleType())
                .build();
        deliveryPersonRepository.save(deliveryPersonEntity);
        return new ResponseEntity<>("Delivery Person saved Successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<DeliveryPersonModel>> allDeliverPersonForSeller(RetrieveUserDto retrieveUserDto) throws DataNotFoundException {
        SellerEntity seller = service.findSellerByEmail(retrieveUserDto.getEmail());
        if (Objects.isNull(seller)){
            throw new DataNotFoundException("Seller Email not found");
        }
        List<DeliveryPersonModel> deliveryPersonModels = new ArrayList<>();
        List<DeliveryPersonEntity> deliveryPersonEntities = deliveryPersonRepository.findBySeller(seller);
        if (deliveryPersonEntities.size()==0){
            throw new DataNotFoundException("You are yet to add a delivery person");
        }
        for (DeliveryPersonEntity deliveryPerson:deliveryPersonEntities){
            deliveryPersonModels.add(DeliveryPersonModelUtil.getReturnedDeliveryPerson(deliveryPerson));
        }
        return new ResponseEntity<>(deliveryPersonModels,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<DeliveryPersonModel>> allDeliverPersons() {
        List<DeliveryPersonModel> deliveryPersonModels = new ArrayList<>();
        List<DeliveryPersonEntity>deliveryPersonEntities = deliveryPersonRepository.findAll();
        for (DeliveryPersonEntity deliveryPerson:deliveryPersonEntities){
            deliveryPersonModels.add(DeliveryPersonModelUtil.getReturnedDeliveryPerson(deliveryPerson));
        }
        return new ResponseEntity<>(deliveryPersonModels,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DeliveryPersonModel> viewDeliverPersonByPhoneNumber(ViewDeliveryPersonDto viewDeliveryPersonDto) throws DataNotFoundException, DataNotAcceptableException {
        DeliveryPersonEntity deliveryPerson = deliveryPersonRepository.findByPhoneNumber(viewDeliveryPersonDto.getDeliverPersonPhoneNumber());
        if (Objects.isNull(deliveryPerson)){
            throw new DataNotFoundException("There is no delivery person with the provided phone Number");
        }
        SellerEntity seller = service.findSellerByEmail(viewDeliveryPersonDto.getSellerEmail());
        if (Objects.isNull(seller)){
            throw new DataNotFoundException("There is no seller with the provided email address");
        }
        if (!deliveryPerson.getSeller().equals(seller)){
           throw new DataNotAcceptableException("You don't have the permission to view this delivery person");
        }
        return new ResponseEntity<>(DeliveryPersonModelUtil.getReturnedDeliveryPerson(deliveryPerson),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DeliveryPersonModel> viewDeliverPersonByVehicleNumber(ViewDeliveryPersonDto viewDeliveryPersonDto) throws DataNotFoundException, DataNotAcceptableException {
        DeliveryPersonEntity deliveryPerson = deliveryPersonRepository.findByVehicleNumber(viewDeliveryPersonDto.getDeliverPersonVehicleNumber());
        if (Objects.isNull(deliveryPerson)){
            throw new DataNotFoundException("There is no delivery person with the provided vehicle Number");
        }
        SellerEntity seller = service.findSellerByEmail(viewDeliveryPersonDto.getSellerEmail());
        if (Objects.isNull(seller)){
            throw new DataNotFoundException("There is no seller with the provided email address");
        }
        if (!deliveryPerson.getSeller().equals(seller)){
            throw new DataNotAcceptableException("You don't have the permission to view this delivery person");
        }
        return new ResponseEntity<>(DeliveryPersonModelUtil.getReturnedDeliveryPerson(deliveryPerson),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteDeliveryPerson(DeleteDeliveryPersonDto deleteDeliveryPersonDto) throws DataNotFoundException, DataNotAcceptableException {
        DeliveryPersonEntity deliveryPerson = deliveryPersonRepository.findByPhoneNumber(deleteDeliveryPersonDto.getDeliveryPersonPhoneNumber());
        if (Objects.isNull(deliveryPerson)){
            throw new DataNotFoundException("There is no delivery person with the provided phone Number");
        }
        SellerEntity seller = service.findSellerByEmail(deleteDeliveryPersonDto.getSellerEmail());
        if (Objects.isNull(seller)){
            throw new DataNotFoundException("There is no seller with the provided email address");
        }
        if (!deliveryPerson.getSeller().equals(seller)){
            throw new DataNotAcceptableException("You don't have the permission to delete this delivery person");
        }
        deliveryPersonRepository.delete(deliveryPerson);
        return new ResponseEntity<>("Delivery person deleted Successfully",HttpStatus.OK);
    }
}
