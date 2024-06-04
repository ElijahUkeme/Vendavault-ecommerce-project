package com.vendavaultecommerceproject.cotroller.seller;


import com.vendavaultecommerceproject.dto.seller.SellerDto;
import com.vendavaultecommerceproject.dto.seller.SellerUpdateDto;
import com.vendavaultecommerceproject.dto.user.SignInDto;
import com.vendavaultecommerceproject.entities.attachment.AttachmentEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.response.seller.SellerServerResponse;
import com.vendavaultecommerceproject.service.main.attachment.AttachmentService;
import com.vendavaultecommerceproject.service.main.seller.SellerService;
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
public class SellerController {

    private SellerService service;
    private AttachmentService attachmentService;

    public SellerController(SellerService service, AttachmentService attachmentService) {
        this.service = service;
        this.attachmentService = attachmentService;
    }
    @PostMapping("/seller/create")
    public SellerServerResponse createSeller(@RequestParam("attachment") MultipartFile file, @RequestPart("seller") SellerDto sellerDto, HttpServletRequest request) throws Exception {
        return service.registerSeller(file,sellerDto,request);
    }
    @GetMapping("/return/{attachmentId}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable("attachmentId") String attachmentId) throws Exception {
        AttachmentEntity attachment = attachmentService.getAttachment(attachmentId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+attachment.getFileName()
                        +"\"")
                .body(new ByteArrayResource(attachment.getData()));

    }
    @PostMapping("/seller/login")
    public SellerServerResponse signInSeller(@RequestBody SignInDto signInDto, HttpServletRequest request) throws NoSuchAlgorithmException {
        return service.signInSeller(signInDto,request);
    }
    @RequestMapping(value="/verify-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmSellerAccount(@RequestParam("token")String confirmationToken) throws DataNotFoundException {
        return service.confirmEmail(confirmationToken);
    }

    @PostMapping("/seller/info/update")
    public SellerServerResponse updateSellerInfo(@RequestBody SellerUpdateDto sellerUpdateDto, HttpServletRequest request){
        return service.updateSellerInfo(sellerUpdateDto,request);
    }

}
