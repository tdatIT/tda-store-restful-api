package com.webapp.tdastore.services;

import com.webapp.tdastore.dto.UserDTO;
import com.webapp.tdastore.entities.ResetPassToken;
import com.webapp.tdastore.entities.User;
import com.webapp.tdastore.entities.VerificationToken;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User findById(long id);

    User findByEmail(String email);

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
}
