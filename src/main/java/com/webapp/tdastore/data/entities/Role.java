package com.webapp.tdastore.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "role")
@Entity
@Getter
@Setter
public class Role {
    public final static int ADMIN = 1;
    public final static int USER = 2;
    public final static int GUEST = 3;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}
