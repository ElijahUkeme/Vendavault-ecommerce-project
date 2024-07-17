package com.vendavaultecommerceproject.cotroller.user;

import com.vendavaultecommerceproject.dto.user.UpdateUserInfoDto;
import com.vendavaultecommerceproject.dto.user.UserDto;
import com.vendavaultecommerceproject.dto.user.SignInDto;
import com.vendavaultecommerceproject.entities.attachment.AttachmentEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import com.vendavaultecommerceproject.service.main.attachment.AttachmentService;
import com.vendavaultecommerceproject.service.main.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;


@RestController
public class UserController {


    private UserService userService;
    private AttachmentService attachmentService;


    UserController(AttachmentService attachmentService,UserService userService){
        this.attachmentService = attachmentService;
        this.userService = userService;

    }

    @PostMapping("/user/create")
    public ServerResponse createUser(@RequestParam("attachment")MultipartFile file, @RequestPart("user")UserDto userDto, HttpServletRequest request) throws Exception {
        return userService.registerUser(file,userDto,request);
    }

    @GetMapping("/download/{attachmentId}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable("attachmentId") String attachmentId) throws Exception {
        AttachmentEntity attachment = attachmentService.getAttachment(attachmentId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+attachment.getFileName()
                        +"\"")
                .body(new ByteArrayResource(attachment.getData()));

    }

    @PostMapping("/user/login")
    public ServerResponse signInUser(@RequestBody SignInDto signInDto,HttpServletRequest request) throws NoSuchAlgorithmException {
        return userService.signInUser(signInDto,request);
    }


    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) throws DataNotFoundException {
        return userService.confirmEmail(confirmationToken);
    }

    @PostMapping("/user/info/update")
    public ServerResponse updateUserInfo(@RequestBody UpdateUserInfoDto userInfoDto,HttpServletRequest request){
        return userService.updateUserInfo(userInfoDto,request);
    }

    @GetMapping("/user/get-by-email")
    public UserEntity getUserByEmail(@RequestParam("email")String email){
        return userService.findUserByEmail(email);
    }
}
