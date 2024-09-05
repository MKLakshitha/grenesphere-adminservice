package com.greensphereadminportalservice.model;


import com.greensphereadminportalservice.Entities.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "Users")
public class usersModel extends BaseEntity {

    @Id
    private String id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "first_name", length = 50)
    private String first_name;

    @Column(name = "last_name", length = 50)
    private String last_name;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "contact_info", length = 100)
    private String contact_info;

    @Column(name = "status")
    private boolean status;

    @Column(name = "role")
    private String role;

    @Override
    public String toString() {
        return "usersModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", status=' " + status + '\'' +
                '}';
    }
}
