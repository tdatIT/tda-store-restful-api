package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.data.dto.UserDTO;
import com.webapp.tdastore.data.entities.*;
import com.webapp.tdastore.data.repositories.ResetPassTokenRepos;
import com.webapp.tdastore.data.repositories.UserRepos;
import com.webapp.tdastore.data.repositories.VerificationTokenRepos;
import com.webapp.tdastore.services.EmailService;
import com.webapp.tdastore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepos userRepos;

    @Autowired
    private VerificationTokenRepos tokenRepos;

    @Autowired
    private ResetPassTokenRepos resetPassTokenRepos;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private EmailService emailService;


    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public User findById(long id) {
        return userRepos.findById(id).orElseThrow();
    }

    @Override
    public User findByEmail(String email) {
        return userRepos.findUserByEmail(email);
    }

    @Override
    public List<User> findUserHasRoleUser(Pageable pageable, int noUser) {
        return null;
    }

    @Override
    public void registerAccount(User user) {
        VerificationToken token = tokenRepos.findVerificationTokenByUser(user);
        token.setUsed(true);
        tokenRepos.save(token);
        userRepos.save(user);
    }

    @Override
    public User registerNewUserAccount(UserDTO dto) {
        if (existEmail(dto.getEmail()))
            return null;
        User user = mapper.map(dto, User.class);
        //hash password
        String hashPassword = encoder.encode(user.getHashPassword());
        user.setHashPassword(hashPassword);
        //set date
        user.setCreateDate(new Timestamp(new Date().getTime()));
        //setRole
        Role role = new Role();
        role.setRoleId(2);
        user.setRole(role);
        //set default address
        UserAddress default_add = dto.getDefaultAddress();
        default_add.setPhone(user.getPhone());
        default_add.setUser(user);
        default_add.setDetail(dto.getAddress_detail());
        user.setAddress(new ArrayList<>());
        user.getAddress().add(default_add);
        return userRepos.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setUsed(false);
        tokenRepos.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepos.findVerificationTokenByToken(VerificationToken);
    }

    @Override
    public void createResetToken(String email) {
        User user = userRepos.findUserByEmail(email);
        if (user != null) {
            int value = new Random().nextInt(999999) + 100000;
            ResetPassToken resetToken = new ResetPassToken();
            resetToken.setUser(user);
            resetToken.setToken(value + "");
            resetPassTokenRepos.save(resetToken);
            emailService.sendResetPassword(user.getEmail(), resetToken.getToken());
        }
    }

    @Override
    public ResetPassToken getResetToken(String token) {
        return resetPassTokenRepos.findResetPassTokenReposByToken(token);
    }

    @Override
    public void resetPassword(User user) {
        String encode_pass = encoder.encode(user.getHashPassword());
        user.setHashPassword(encode_pass);
        user.setUpdateDate(new Timestamp(new Date().getTime()));
        userRepos.save(user);
    }

    @Override
    public void updateAccount(User user) {
        user.setUpdateDate(new Timestamp(new Date().getTime()));
        userRepos.save(user);
    }

    @Override
    public void disableAccount(User user) {

    }

    private boolean existEmail(String email) {
        return userRepos.findUserByEmail(email) != null;
    }

    @Transactional
    @Override
    public void updatePassword(User us, String password) {
        us.setHashPassword(encoder.encode(password));
        updateAccount(us);
    }
}
