package com.vendavaultecommerceproject.cotroller.delivery;


import com.vendavaultecommerceproject.dto.delivery.DeleteDeliveryPersonDto;
import com.vendavaultecommerceproject.dto.delivery.DeliveryPersonDto;
import com.vendavaultecommerceproject.dto.delivery.ViewDeliveryPersonDto;
import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.exception.exeception.DataAlreadyExistException;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.model.delivery.DeliveryPersonModel;
import com.vendavaultecommerceproject.service.main.delivery.DeliveryPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeliveryPersonController {

    private final DeliveryPersonService deliveryPersonService;


    @PostMapping("/delivery/person/add")
    public ResponseEntity<String> addDeliverPerson(@RequestBody DeliveryPersonDto deliveryPersonDto) throws DataNotFoundException, DataNotAcceptableException, DataAlreadyExistException {
        return deliveryPersonService.addDeliveryPerson(deliveryPersonDto);
    }

    @PostMapping("/deliver/person/for/seller")
    public ResponseEntity<List<DeliveryPersonModel>> getAllDeliveryPersonForSeller(@RequestBody RetrieveUserDto retrieveUserDto) throws DataNotFoundException {
        return deliveryPersonService.allDeliverPersonForSeller(retrieveUserDto);
    }

    @GetMapping("/delivery/person/all")
    public ResponseEntity<List<DeliveryPersonModel>> allDeliveryPersons(){
        return deliveryPersonService.allDeliverPersons();
    }

    @PostMapping("/delivery/person/by/phone")
    public ResponseEntity<DeliveryPersonModel> viewDeliveryPersonByPhoneNumber(@RequestBody ViewDeliveryPersonDto viewDeliveryPersonDto) throws DataNotFoundException, DataNotAcceptableException {
        return deliveryPersonService.viewDeliverPersonByPhoneNumber(viewDeliveryPersonDto);
    }

    @PostMapping("/delivery/person/by/vehicle/number")
    public ResponseEntity<DeliveryPersonModel> viewDeliveryPersonByVehicleNumber(@RequestBody ViewDeliveryPersonDto viewDeliveryPersonDto) throws DataNotFoundException, DataNotAcceptableException {
        return deliveryPersonService.viewDeliverPersonByVehicleNumber(viewDeliveryPersonDto);
    }

    @DeleteMapping("/delivery/person/delete")
    public ResponseEntity<String> deleteDeliverPerson(@RequestBody DeleteDeliveryPersonDto deleteDeliveryPersonDto) throws DataNotFoundException, DataNotAcceptableException {
        return deliveryPersonService.deleteDeliveryPerson(deleteDeliveryPersonDto);
    }
}
