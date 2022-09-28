package com.hwe.helloworldenterprise.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String email;
    private String name;
    private String lastname;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @ManyToOne
    private Enterprise enterprise;
    @OneToMany(mappedBy = "employee")
    private List<Transaction> transactions;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee") //Si no existe el usuario no existe el empleado
    private User user;

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

