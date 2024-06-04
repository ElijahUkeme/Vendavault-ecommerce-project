package com.vendavaultecommerceproject.security.controller;


import com.vendavaultecommerceproject.exception.exeception.DataAlreadyExistException;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.security.dto.AuthenticationRequest;
import com.vendavaultecommerceproject.security.dto.RegistrationRequest;
import com.vendavaultecommerceproject.security.dto.RoleDto;
import com.vendavaultecommerceproject.security.dto.RoleToUserDto;
import com.vendavaultecommerceproject.security.response.AuthenticationResponse;
import com.vendavaultecommerceproject.security.service.main.role.RoleService;
import com.vendavaultecommerceproject.security.service.main.user.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RoleService roleService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) throws MessagingException, DataAlreadyExistException, DataNotFoundException, DataNotAcceptableException {
        return new ResponseEntity<>(authenticationService.saveUser(request),HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return new ResponseEntity<>(authenticationService.authenticate(request),HttpStatus.OK);
    }

    @GetMapping("/activate-account/{token}")
    public ResponseEntity<String> validateToken(@RequestParam("token")String token) throws DataNotFoundException, MessagingException, DataNotAcceptableException {
        return new ResponseEntity<>(authenticationService.activateAccount(token),HttpStatus.OK);
    }

    @PostMapping("/role/add")
    public ResponseEntity<String> saveRole(@RequestBody RoleDto roleDto) throws DataAlreadyExistException {
        return new ResponseEntity<>(roleService.saveRole(roleDto),HttpStatus.OK);
    }

    @PostMapping("/add/role/to/user")
    public ResponseEntity<String> addRoleToUser(@RequestBody RoleToUserDto roleToUserDto) throws DataNotFoundException, DataAlreadyExistException {
        return new ResponseEntity<>(roleService.addRoleToUser(roleToUserDto),HttpStatus.OK);
    }
}
