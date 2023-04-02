package com.webapp.tdastore.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "users")
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

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAddress> address;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ResetPassToken> resetTokens;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Wishlist> wishlists;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
