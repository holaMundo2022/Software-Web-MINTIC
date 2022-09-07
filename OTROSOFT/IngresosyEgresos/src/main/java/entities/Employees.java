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
@Table(name="Employees")
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;
    @Column(name= "Perfil")
    private Profile profile;
    private Enterprises enterprises_employee;
    private ArrayList<Transaction> transactions_Enterprises;
    @Temporal(TemporalType.DATE)
    private Date created_Date;
    @Temporal(TemporalType.DATE)
    private Date update_Date;

    public Employees() {

    }

    public Employees(long ID, Profile profile, Enterprises enterprises_employee, ArrayList<Transaction> transactions_Enterprises, Date created_Date, Date update_Date) {
        this.ID = ID;
        this.profile = profile;
        this.enterprises_employee = enterprises_employee;
        this.transactions_Enterprises = transactions_Enterprises;
        this.created_Date = created_Date;
        this.update_Date = update_Date;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "ID=" + ID +
                ", profile=" + profile +
                ", enterprises_employee=" + enterprises_employee +
                ", transactions_Enterprises=" + transactions_Enterprises +
                ", created_Date=" + created_Date +
                ", update_Date=" + update_Date +
                '}';
    }
}
