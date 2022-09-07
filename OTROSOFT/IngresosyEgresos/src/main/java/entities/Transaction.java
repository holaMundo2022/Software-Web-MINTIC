package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table ( name = "Transactions")
public class Transaction {
    @Id
    private long ID;
    private Double amount;
    private String description;
    private Employees employee;
    private Date dateCreated;
    private Date update_Date;

    public Transaction() {

    }

    public Transaction(long ID, Double amount, String description, Employees employee, Date dateCreated, Date update_Date) {
        this.ID = ID;
        this.amount = amount;
        this.description = description;
        this.employee = employee;
        this.dateCreated = dateCreated;
        this.update_Date = update_Date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "ID=" + ID +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", employee=" + employee +
                ", dateCreated=" + dateCreated +
                ", update_Date=" + update_Date +
                '}';
    }
}
