package entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@Data
@Entity
@Table(name = "Enterprises")
public class Enterprises {
    @Id
    private long ID;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String nit;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String address;
    private ArrayList<Employees> employees_Enterprises;
    @Temporal(TemporalType.DATE)
    private Date created_Date;
    @Temporal(TemporalType.DATE)
    private Date update_Date;

    public Enterprises() {

    }

    public Enterprises(long ID, String name, String nit, String phone, String address, ArrayList<Employees> employees_Enterprises, Date created_Date, Date update_Date) {
        this.ID = ID;
        this.name = name;
        this.nit = nit;
        this.phone = phone;
        this.address = address;
        this.employees_Enterprises = employees_Enterprises;
        this.created_Date = created_Date;
        this.update_Date = update_Date;
    }

    @Override
    public String toString() {
        return "Enterprises{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", nit='" + nit + '\'' +
                ", phone=" + phone +
                ", address='" + address + '\'' +
                ", employees_Enterprises=" + employees_Enterprises +
                ", created_Date=" + created_Date +
                ", Update_Date=" + update_Date +
                '}';
    }
}
