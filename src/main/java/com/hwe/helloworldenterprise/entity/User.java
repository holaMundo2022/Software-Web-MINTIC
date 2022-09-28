package com.hwe.helloworldenterprise.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String image;
    private String phone;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    private String password;
    @Column(unique = true)
    private String userName;

    @OneToOne
    private Employee employee;

    @ManyToOne
    private Role rol;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.updatedAt == null) this.updatedAt = new Date();
    }

    @PostUpdate
    public void postUpdate() {
        this.updatedAt = new Date();

    }

}


