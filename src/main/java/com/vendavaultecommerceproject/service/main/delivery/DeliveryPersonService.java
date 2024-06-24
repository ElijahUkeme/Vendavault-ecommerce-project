package com.vendavaultecommerceproject.service.main.delivery;

import com.vendavaultecommerceproject.dto.delivery.DeleteDeliveryPersonDto;
import com.vendavaultecommerceproject.dto.delivery.DeliveryPersonDto;
import com.vendavaultecommerceproject.dto.delivery.ViewDeliveryPersonDto;
import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.exception.exeception.DataAlreadyExistException;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.model.delivery.DeliveryPersonModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DeliveryPersonService {

    public ResponseEntity<String> addDeliveryPerson(DeliveryPersonDto deliveryPersonDto) throws DataNotFoundException, DataAlreadyExistException, DataNotAcceptableException;
    public ResponseEntity<List<DeliveryPersonModel>> allDeliverPersonForSeller(RetrieveUserDto retrieveUserDto) throws DataNotFoundException;
    public ResponseEntity<List<DeliveryPersonModel>> allDeliverPersons();
    public ResponseEntity<DeliveryPersonModel> viewDeliverPersonByPhoneNumber(ViewDeliveryPersonDto viewDeliveryPersonDto) throws DataNotFoundException, DataNotAcceptableException;
    public ResponseEntity<DeliveryPersonModel> viewDeliverPersonByVehicleNumber(ViewDeliveryPersonDto viewDeliveryPersonDto) throws DataNotFoundException, DataNotAcceptableException;
    public ResponseEntity<String> deleteDeliveryPerson(DeleteDeliveryPersonDto deleteDeliveryPersonDto) throws DataNotFoundException, DataNotAcceptableException;

}
