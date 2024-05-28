package com.vendavaultecommerceproject.service.impl.seller;


import com.vendavaultecommerceproject.dto.password.ResetPasswordDto;
import com.vendavaultecommerceproject.dto.seller.AdminApproveSellerDto;
import com.vendavaultecommerceproject.dto.seller.SellerDto;
import com.vendavaultecommerceproject.dto.seller.SellerUpdateDto;
import com.vendavaultecommerceproject.dto.user.SignInDto;
import com.vendavaultecommerceproject.entities.attachment.AttachmentEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.token.SellerAuthenticationTokenEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.model.email.EmailDetails;
import com.vendavaultecommerceproject.repository.seller.SellerRepository;
import com.vendavaultecommerceproject.response.seller.SellerResponse;
import com.vendavaultecommerceproject.response.seller.SellerServerResponse;
import com.vendavaultecommerceproject.response.user.ApiResponse;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import com.vendavaultecommerceproject.service.main.attachment.AttachmentService;
import com.vendavaultecommerceproject.service.main.email.EmailService;
import com.vendavaultecommerceproject.service.main.seller.SellerService;
import com.vendavaultecommerceproject.service.main.token.SellerAuthenticationTokenService;
import com.vendavaultecommerceproject.util.Utility;
import com.vendavaultecommerceproject.utils.SellerModelUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@Slf4j
public class SellerServiceImpl implements SellerService {

    @Value("${baseUrl}")
    private String baseUrl;
    private SellerRepository sellerRepository;
    private EmailService emailService;
    private AttachmentService attachmentService;
    private SellerAuthenticationTokenService authenticationTokenService;

    public SellerServiceImpl(SellerRepository sellerRepository, EmailService emailService,
                             AttachmentService attachmentService,
                             SellerAuthenticationTokenService authenticationTokenService) {
        this.sellerRepository = sellerRepository;
        this.emailService = emailService;
        this.attachmentService = attachmentService;
        this.authenticationTokenService = authenticationTokenService;
    }


    @Override
    public SellerServerResponse registerSeller(MultipartFile file, SellerDto sellerDto, HttpServletRequest request) throws Exception {

        if (Objects.isNull(sellerDto.getEmail())|| sellerDto.getEmail().trim().isEmpty()){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new SellerResponse(406,"Seller Authentication","Email field cannot be empty",null));
        }
        if (Objects.isNull(sellerDto.getUsername())|| sellerDto.getUsername().trim().isEmpty()){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new SellerResponse(406,"Seller Authentication","Username field cannot be empty",null));
        }
        if (Objects.isNull(sellerDto.getName())|| sellerDto.getName().isEmpty()){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new SellerResponse(406,"Seller Authentication","Name field cannot be empty",null));
        }
        if (Objects.isNull(sellerDto.getBusinessDescription())|| sellerDto.getBusinessDescription().isEmpty()){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new SellerResponse(406,"Seller Authentication","Please give a brief description about your business",null));
        }
        if (Objects.isNull(sellerDto.getPassword())|| sellerDto.getPassword().trim().isEmpty()){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new SellerResponse(406,"Seller Authentication","Password field cannot be empty",null));
        }
        if (Objects.isNull(sellerDto.getPhoneNumber())|| sellerDto.getPhoneNumber().trim().isEmpty()){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new SellerResponse(406,"Seller Authentication","Phone Number field cannot be empty",null));
        }
        if (Objects.isNull(sellerDto.getConfirmPassword())|| sellerDto.getConfirmPassword().trim().isEmpty()){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new SellerResponse(406,"Seller Authentication","Confirm Password is Required",null));
        }
        if (Objects.isNull(sellerDto.getBusinessName())|| sellerDto.getBusinessName().isEmpty()){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new SellerResponse(406,"Seller Authentication","Business Name is Required",null));
        }
        if (!(sellerDto.getPassword().equals(sellerDto.getConfirmPassword()))){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new SellerResponse(406,"Seller Authentication","Password and Confirm Password are not the same",null));
        }

        if (Objects.nonNull(sellerRepository.findByEmail(sellerDto.getEmail()))){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new SellerResponse(406,"Seller Authentication","Email Address Already taken by another Seller",null));
        }

        AttachmentEntity attachment = attachmentService.saveAttachment(file);
        if (Objects.isNull(attachment)){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new SellerResponse(406,"Seller Authentication","Invalid file format, please it's only pdf, docx and jpeg file format that's accepted",null));
        }
        String downloadUrl = "download/"+attachment.getId();
        String encryptedCustomerPassword = sellerDto.getPassword();

        try {
            encryptedCustomerPassword = Utility.hashPassword(sellerDto.getPassword());
        }catch (Exception e){
            log.info(e.getMessage());
        }
        SellerEntity seller = SellerEntity.builder()
                .name(sellerDto.getName())
                .businessDescription(sellerDto.getBusinessDescription())
                .businessName(sellerDto.getBusinessName())
                .username(sellerDto.getUsername())
                .email(sellerDto.getEmail())
                .isVerified(false)
                .phoneNumber(sellerDto.getPhoneNumber())
                .password(encryptedCustomerPassword)
                .identificationUrl(downloadUrl)
                .build();
        sellerRepository.save(seller);
        final SellerAuthenticationTokenEntity authenticationToken = new SellerAuthenticationTokenEntity(seller);
        authenticationTokenService.saveSellerAuthenticationToken(authenticationToken);

        EmailDetails emailDetails = EmailDetails.builder()
                .subject("ACCOUNT CREATION")
                .messageBody("To complete your account creation, please click here to verify your email : "
                        +baseUrl+"/verify-account?token="+authenticationToken.getToken()+"\n\nFrom Vendavault")
                .recipient(seller.getEmail())
                .build();
        emailService.sendEmailAlert(emailDetails);
        System.out.println("Authentication token is "+authenticationToken.getToken());

        return new SellerServerResponse(baseUrl+request.getRequestURI(),"OK",new SellerResponse(
                201,"Seller Registration","Your Registration was successful and an email confirmation link has been sent to your email address", SellerModelUtil.getReturnedSeller(seller)
        ));
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) throws DataNotFoundException {
        SellerEntity seller = authenticationTokenService.getSellerByToken(confirmationToken);
        if (seller !=null){
            seller.setVerified(true);
            sellerRepository.save(seller);
        return ResponseEntity.ok("Email verified successfully!");
    }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
}

    @Override
    public SellerServerResponse getSellerByEmail(String email, HttpServletRequest request) {
        SellerEntity seller = sellerRepository.findByEmail(email);
        if (Objects.isNull(seller)){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(
                    406,"Seller Authentication","There is no seller with the provided email address",null
            ));
        }
        return new SellerServerResponse(baseUrl+request.getRequestURI(),"OK",new SellerResponse(
                200,"Seller Authentication","Seller Found",SellerModelUtil.getReturnedSeller(seller)
        ));
    }

    @Override
    public SellerEntity findSellerByEmail(String email) {
        SellerEntity seller = sellerRepository.findByEmail(email);
        if (Objects.nonNull(seller)){
            return  seller;
        }
        return null;
    }


    @Override
    public SellerServerResponse resetPassword(ResetPasswordDto resetPasswordDto, HttpServletRequest request) throws NoSuchAlgorithmException {
        SellerEntity seller = sellerRepository.findByEmail(resetPasswordDto.getEmail());
        if (Objects.isNull(seller)) {

            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(
                    406,"Seller Authentication","Email address not found",null
            ));
        }
        if (!seller.isVerified()){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(
                    406,"Seller Authentication","Provided email address not verified",null
            ));
        }
        if (!(resetPasswordDto.getPassword().equalsIgnoreCase(resetPasswordDto.getConfirmPassword()))){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(
                    406,"Seller Authentication","Password and Confirm password must be the same",null
            ));
        }
        String newPassword = Utility.hashPassword(resetPasswordDto.getPassword());
        seller.setPassword(newPassword);
        sellerRepository.save(seller);
        return new SellerServerResponse(baseUrl+request.getRequestURI(),"OK",new SellerResponse(
                200,"Seller Authentication","Your password has been changed Successfully",SellerModelUtil.getReturnedSeller(seller)
        ));
    }

    @Override
    public SellerServerResponse signInSeller(SignInDto signInDto, HttpServletRequest request) throws NoSuchAlgorithmException {
        SellerEntity seller = sellerRepository.findByEmail(signInDto.getEmail());
        if (Objects.isNull(seller)){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(
                    406,"Seller Authentication","Invalid Email address or Password",null
            ));
        }else {
            if (!(seller.getPassword().equals(Utility.hashPassword(signInDto.getPassword())))){
                return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(
                        406,"Seller Authentication","Invalid Email address or Password",null
                ));
            }
            if (!seller.isVerified()){
                return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(
                        406,"Seller Authentication","This user has not been verified",null
                ));
            }
        }
        return new SellerServerResponse(baseUrl+request.getRequestURI(),"OK",new SellerResponse(
                200,"Seller Authentication","Seller logged In successfully",SellerModelUtil.getReturnedSeller(seller)
        ));
    }

    @Override
    public SellerServerResponse updateSellerInfo(SellerUpdateDto sellerUpdateDto, HttpServletRequest request) {
        SellerEntity seller = sellerRepository.findByEmail(sellerUpdateDto.getEmail());
        if (Objects.isNull(seller)){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(
                    406,"Seller Authentication","Provided email address not verified",null
            ));
        }
        if (Objects.nonNull(sellerUpdateDto.getName())&& !"".equalsIgnoreCase(sellerUpdateDto.getName())){
            seller.setName(sellerUpdateDto.getName());
        }
        if (Objects.nonNull(sellerUpdateDto.getUsername())&& !"".equalsIgnoreCase(sellerUpdateDto.getUsername())){
            seller.setUsername(sellerUpdateDto.getUsername());
        }
        if (Objects.nonNull(sellerUpdateDto.getBusinessName())&& !"".equalsIgnoreCase(sellerUpdateDto.getBusinessName())){
            seller.setBusinessName(sellerUpdateDto.getBusinessName());
        }
        if (Objects.nonNull(sellerUpdateDto.getPhoneNumber())&& !"".equalsIgnoreCase(sellerUpdateDto.getPhoneNumber())){
            seller.setPhoneNumber(sellerUpdateDto.getPhoneNumber());
        }
        if (Objects.nonNull(sellerUpdateDto.getBusinessDescription())&& !"".equalsIgnoreCase(sellerUpdateDto.getBusinessDescription())){
            seller.setBusinessDescription(sellerUpdateDto.getBusinessDescription());
        }
        return new SellerServerResponse(baseUrl+request.getRequestURI(),"OK",new SellerResponse(
                200,"Seller Authentication","Seller Info updated Successfully",SellerModelUtil.getReturnedSeller(seller)
        ));
    }
}
