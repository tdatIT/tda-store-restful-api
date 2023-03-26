package com.webapp.tdastore.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "user")
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String email;

    @Column
    private String hashPassword;

    @Column
    private String phone;

    @Column
    private String avatar;

    @Column
    private boolean status;

    @Column
    private Timestamp createDate;
    @Column
    private Timestamp updateDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAddress> address;

    @OneToMany(mappedBy = "user")
    private List<ResetPassToken> resetTokens;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Favourite> favourites;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
