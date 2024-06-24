package com.vendavaultecommerceproject.service.impl.user;

import com.vendavaultecommerceproject.dto.user.UpdateUserInfoDto;
import com.vendavaultecommerceproject.dto.user.UserDto;
import com.vendavaultecommerceproject.dto.password.ResetPasswordDto;
import com.vendavaultecommerceproject.dto.user.SignInDto;
import com.vendavaultecommerceproject.entities.attachment.AttachmentEntity;
import com.vendavaultecommerceproject.entities.token.UserAuthenticationTokenEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.model.email.EmailDetails;
import com.vendavaultecommerceproject.repository.user.UserRepository;
import com.vendavaultecommerceproject.response.user.ApiResponse;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import com.vendavaultecommerceproject.service.main.attachment.AttachmentService;
import com.vendavaultecommerceproject.service.main.email.EmailService;
import com.vendavaultecommerceproject.service.main.token.UserAuthenticationTokenService;
import com.vendavaultecommerceproject.service.main.user.UserService;
import com.vendavaultecommerceproject.util.Utility;
import com.vendavaultecommerceproject.util.enums.AccountStatus;
import com.vendavaultecommerceproject.utils.UserModelUtil;
import com.vendavaultecommerceproject.utils.UserVerificationUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {


    @Value("${baseUrl}")
    private String baseUrl;
    private UserRepository userRepository;

    private EmailService emailService;
    private AttachmentService attachmentService;

    private UserAuthenticationTokenService authenticationTokenService;

    UserServiceImpl(UserRepository userRepository,EmailService emailService,UserAuthenticationTokenService authenticationTokenService,AttachmentService attachmentService){
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.authenticationTokenService = authenticationTokenService;
        this.attachmentService = attachmentService;
    }
    @Override
    public ServerResponse registerUser(MultipartFile file, UserDto userDto,HttpServletRequest request) throws Exception {

        if (Objects.isNull(userDto.getEmail())|| userDto.getEmail().trim().isEmpty()){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Registration Information",
                    "Email Field Cannot Be Empty",
                    null));
        }
        if (Objects.isNull(userDto.getUsername())|| userDto.getUsername().trim().isEmpty()){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Registration Information",
                    "Username Field Cannot Be Empty",
                    null));
        }
        if (Objects.isNull(userDto.getName())|| userDto.getName().isEmpty()){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Registration Information",
                    "Name Field Cannot Be Empty",
                    null));
        }
        if (Objects.isNull(userDto.getPassword())|| userDto.getPassword().trim().isEmpty()){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Registration Information",
                    "Password Field Cannot Be Empty",
                    null));
        }
        if (Objects.isNull(userDto.getPhoneNumber())|| userDto.getPhoneNumber().trim().isEmpty()){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Registration Information",
                    "Phone Number is Compulsory",
                    null));
        }
        if (Objects.isNull(userDto.getConfirmPassword())|| userDto.getConfirmPassword().trim().isEmpty()){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Registration Information",
                    "Confirm Password is Required",
                    null));
        }
        if (!(userDto.getPassword().equals(userDto.getConfirmPassword()))){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Registration Information",
                    "Password and Confirm Password must be the same",
                    null));
        }

        if (Objects.nonNull(userRepository.findByEmail(userDto.getEmail()))){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(409,"Registration Information",
                    "Email Address Already taken by another User",
                    null));
        }
        AttachmentEntity attachment = attachmentService.saveAttachment(file);
        if (Objects.isNull(attachment)){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Registration Information",
                    "Invalid file format, please it's only pdf, docx and jpeg file format that's accepted",
                    null));
        }
        String downloadUrl = "download/"+attachment.getId();
        String encryptedCustomerPassword = userDto.getPassword();
        try {
            encryptedCustomerPassword = Utility.hashPassword(userDto.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

            UserEntity user = UserEntity.builder()
                    .name(userDto.getName())
                    .email(userDto.getEmail())
                    .username(userDto.getUsername())
                    .password(encryptedCustomerPassword)
                    .isVerified(false)
                    .identificationUrl(downloadUrl)
                    .accountStatus(AccountStatus.Active.name())
                    .phoneNumber(userDto.getPhoneNumber())
                    .identificationUrl(downloadUrl)
                    .fcmToken(userDto.getFcmToken())
                    .build();

            userRepository.save(user);
            final UserAuthenticationTokenEntity authenticationToken = new UserAuthenticationTokenEntity(user);
            authenticationTokenService.saveUserAuthenticationToken(authenticationToken);

            EmailDetails emailDetails = EmailDetails.builder()
                    .subject("ACCOUNT CREATION")
                    .messageBody("To complete your account creation, please click here to verify your email : "
                            +baseUrl+"/confirm-account?token="+authenticationToken.getToken()+"\n\nFrom Vendavault")
                    .recipient(user.getEmail())
                    .build();
            emailService.sendEmailAlert(emailDetails);
            System.out.println("Authentication token is "+authenticationToken.getToken());

        return new ServerResponse(baseUrl+request.getRequestURI(),"OK",new ApiResponse(201,"Registration Information",
                "Your Registration was successful and an email confirmation link has been sent to your email address",
                UserModelUtil.getReturnedUserModel(user)));
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) throws DataNotFoundException {
        UserEntity user = authenticationTokenService.getUserByToken(confirmationToken);

        if (user !=null){
            user.setVerified(true);
            userRepository.save(user);

        return ResponseEntity.ok("Email verified successfully!");
    }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }


    @Override
    public ServerResponse getUserByEmail(String email,HttpServletRequest request) {

        UserEntity user = userRepository.findByEmail(email);
        if (Objects.isNull(user)){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Forgot Password Information",
                    "The provided email address not found",
                    null));
        }
        return new ServerResponse(baseUrl+request.getRequestURI(),"OK",new ApiResponse(200,"Authentication Information",
                "User found",
                UserModelUtil.getReturnedUserModel(user)));
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (Objects.nonNull(user)){
            return user;
        }else {
            return null;
        }
    }

    @Override
    public ServerResponse resetPassword(ResetPasswordDto resetPasswordDto, HttpServletRequest request) throws NoSuchAlgorithmException {

        UserEntity user = userRepository.findByEmail(resetPasswordDto.getEmail());
        if (Objects.isNull(user)){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Forgot Password Information",
                    "The provided email address not found",
                    null));
        }
        if (!user.isVerified()){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(409,"Forgot Password Information",
                    "The provided email address has not been verified, please verify it before making any request",
                    null));
        }
        if (!(resetPasswordDto.getPassword().equalsIgnoreCase(resetPasswordDto.getConfirmPassword()))){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(409,"Reset Password Information",
                    "Password and ConfirmPassword must be the same",
                    null));
        }

        String newPassword = Utility.hashPassword(resetPasswordDto.getPassword());
        user.setPassword(newPassword);
        userRepository.save(user);
        return new ServerResponse(baseUrl+request.getRequestURI(),"OK",new ApiResponse(200,"Password Reset Information",
                "Your password has been changed successfully",
                UserModelUtil.getReturnedUserModel(user)));
    }

    @Override
    public ServerResponse signInUser(SignInDto signInDto,HttpServletRequest request) throws NoSuchAlgorithmException {
        UserEntity user = userRepository.findByEmail(signInDto.getEmail());
        if (Objects.isNull(user)){

            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(404,"Login Information",
                    "Incorrect Email address or Password",
                    null));
        }else {
            if (!(user.getPassword().equals(Utility.hashPassword(signInDto.getPassword())))){


                return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(404,"Login Information",
                        "Incorrect Email address or Password",
                        null));
            }
            if (!(user.isVerified())){
                return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(409,"Login Information",
                        "This user has not been verified, please go to your email and click on the verified link to verify your account",
                        null));
            }
            if (user.getAccountStatus().equalsIgnoreCase(AccountStatus.Suspended.name())){
                return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(409,"Login Information",
                        "This user account has been suspended, please contact the admin for more clarification",
                        null));
            }
        }

        return new ServerResponse(baseUrl+request.getRequestURI(),"OK",new ApiResponse(200,"Login Information",
                "User Logged In Successful",
                UserModelUtil.getReturnedUserModel(user)));
    }

    @Override
    public ServerResponse updateUserInfo(UpdateUserInfoDto userInfoDto, HttpServletRequest request) {
        if (Objects.isNull(UserVerificationUtil.isUserValid(userInfoDto.getEmail()))){
            return new ServerResponse(baseUrl + request.getRequestURI(), "NOT OK", new ApiResponse(406, "User Authentication",
                    "User Email Address not found",
                    null));
        }
        UserEntity user = UserVerificationUtil.isUserValid(userInfoDto.getEmail());
        if (Objects.nonNull(userInfoDto.getName())&& !"".equalsIgnoreCase(userInfoDto.getName())){
            user.setName(userInfoDto.getName());
        }
        if (Objects.nonNull(userInfoDto.getUsername())&& !"".equalsIgnoreCase(userInfoDto.getUsername())){
            user.setUsername(userInfoDto.getUsername());
        }
        if (Objects.nonNull(userInfoDto.getPhoneNumber())&& !"".equalsIgnoreCase(userInfoDto.getPhoneNumber())){
            user.setPhoneNumber(userInfoDto.getPhoneNumber());
        }
        userRepository.save(user);

        return new ServerResponse(baseUrl + request.getRequestURI(), "OK", new ApiResponse(406, "User Updating Info",
                "User Info updated Successfully",
                UserModelUtil.getReturnedUserModel(user)));
    }

}

