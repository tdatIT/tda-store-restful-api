package com.webapp.tdastore.controller.web;

import com.webapp.tdastore.config.ValidatorUtils;
import com.webapp.tdastore.data.entities.User;
import com.webapp.tdastore.data.entities.UserAddress;
import com.webapp.tdastore.data.exception.CustomExceptionRuntime;
import com.webapp.tdastore.data.payload.AddressRequest;
import com.webapp.tdastore.data.payload.ProfileInfo;
import com.webapp.tdastore.data.payload.response.AddressResponse;
import com.webapp.tdastore.data.payload.response.ChangePassword;
import com.webapp.tdastore.data.payload.response.UserResponse;
import com.webapp.tdastore.security.CustomUserDetails;
import com.webapp.tdastore.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/users")
@SecurityRequirement(name = "Bearer Authentication")
public class AccountRestController {
    @Autowired
    private UserService userService;
    @Autowired
    ModelMapper modelMapper;

    private User getUserFromAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((CustomUserDetails) auth.getPrincipal()).getUser();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserResponse> getUserInfo(@PathVariable long id) {
        User us = userService.findById(id);

        if (us == null)
            throw new CustomExceptionRuntime(400, "User with id is not found.");
        UserResponse response = modelMapper.map(us, UserResponse.class);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.PATCH)
    public ResponseEntity changePassword(@Valid @RequestBody ChangePassword body,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new CustomExceptionRuntime(400, "Your password does not meet our security requirements." +
                    " Please ensure that your password is at least 8 characters long, " +
                    "including at least one numeric character, one uppercase letter, " +
                    "one lowercase letter, and at least one special character");

        userService.changePassword(getUserFromAuthentication().getUserId(), body);
        return ResponseEntity.ok("Change password was successful");
    }

    @RequestMapping(value = "/change-info", method = RequestMethod.PATCH)
    public ResponseEntity changeProfileInfo(@Valid @RequestBody ProfileInfo body,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new CustomExceptionRuntime(400, "Request was failed. Validate data again");
        userService.changeInfo(getUserFromAuthentication().getUserId(), body);
        return ResponseEntity.ok("Change info was successful");
    }

    @RequestMapping(value = "/change-avatar", method = RequestMethod.PATCH)
    public ResponseEntity changeAvatarUser(@RequestParam MultipartFile image_file) {
        if (!ValidatorUtils.validateMineFile(image_file))
            throw new CustomExceptionRuntime(400, "Request failed. This file must be png, jpg, jpeg." +
                    " Please validate file again");
        User us = getUserFromAuthentication();
        userService.changeAvatar(us, image_file);
        return ResponseEntity.ok("Change avatar was successful");

    }

    @RequestMapping(value = "/addresses", method = RequestMethod.GET)
    public List<AddressResponse> getAllAddressByUser() {
        List<UserAddress> addresses = userService.getAddressesByUser(getUserFromAuthentication().getUserId());
        if (addresses.size() < 1) {
            throw new CustomExceptionRuntime(200, "Addresses is empty");
        }
        return addresses.stream().map(t -> {
            AddressResponse item = new AddressResponse();
            item.setAddressId(t.getAddressId());
            item.setPhone(t.getPhone());
            item.setAddressDetail(t.getAPIName());

            return item;
        }).collect(Collectors.toList());
    }

    @RequestMapping(value = "/addresses", method = RequestMethod.POST)
    public ResponseEntity addNewAddressForUser(@Valid @RequestBody AddressRequest body,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new CustomExceptionRuntime(400, "Request failed. Please check input data.");
        User us = getUserFromAuthentication();
        userService.addNewUserAddress(us, body);
        return ResponseEntity.ok("Create and add new address for user was successful");
    }

    @RequestMapping(value = "/addresses/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeAddressByIdFromUser(@PathVariable long id) {
        userService.removeUserAddress(getUserFromAuthentication().getUserId(), id);
        return ResponseEntity.ok("Remove address from user was successful");
    }
}
