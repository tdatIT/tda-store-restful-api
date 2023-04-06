package com.webapp.tdastore.services;

import com.webapp.tdastore.data.dto.UserDTO;
import com.webapp.tdastore.data.entities.ResetPassToken;
import com.webapp.tdastore.data.entities.User;
import com.webapp.tdastore.data.entities.UserAddress;
import com.webapp.tdastore.data.entities.VerificationToken;
import com.webapp.tdastore.data.payload.AddressRequest;
import com.webapp.tdastore.data.payload.ProfileInfo;
import com.webapp.tdastore.data.payload.response.ChangePassword;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User findById(long id);

    User findByEmail(String email);

    List<UserAddress> getAddressesByUser(long userId);

    void addNewUserAddress(User us, AddressRequest body);

    void removeUserAddress(long userId, long addressId);

    List<User> findUserHasRoleUser(Pageable pageable, int noUser);

    User registerNewUserAccount(UserDTO dto);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    void createResetToken(String email);

    ResetPassToken getResetToken(String token);

    void registerAccount(User user);

    void resetPassword(User user);

    void updateAccount(User user);

    void disableAccount(User user);


    void updatePassword(User us, String password);

    void changePassword(long userId, ChangePassword body);

    void changeInfo(long userId, ProfileInfo body);

    void changeAvatar(User us, MultipartFile imageFile);
}
