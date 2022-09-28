package com.hwe.helloworldenterprise.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "enterprise")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Enterprise implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String document;
    private String phone;
    private String address;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    @OneToMany
    private List<Employee> employee;

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.updatedAt == null) this.updatedAt = new Date();
    }

    @PostUpdate
    public void postUpdate() {
        this.updatedAt = new Date();
    }

    public Enterprise(long id) {
        this.id = id;
    }
}
