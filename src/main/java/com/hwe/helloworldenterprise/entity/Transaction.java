package com.hwe.helloworldenterprise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String concept;
    private String type;
    private float amount;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date updateAt;

    @JsonIgnore
    @ManyToOne
    private Enterprise enterprise;

    @ManyToOne
    private Employee employee;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.updateAt == null) this.updateAt = new Date();
    }

    @PostUpdate
    public void postUpdate() {
        this.updateAt = new Date();

    }

}
