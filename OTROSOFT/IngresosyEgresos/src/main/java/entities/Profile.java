package entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Data
@Entity
@Table(name= "Profile")
public class Profile {
    @Id
    private long ID;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String picture;
    @Column(unique = true)
    private String phone;
    private Enum_role role;
    @Temporal(TemporalType.DATE)
    private Date created_Date;
    @Temporal(TemporalType.DATE)
    private Date update_Date;

    public Profile() {
    }

    public Profile(long ID, String name, String email, String picture, String phone, Enum_role role, Date created_Date, Date update_Date) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.phone = phone;
        this.role = role;
        this.created_Date = created_Date;
        this.update_Date = update_Date;
    }
    @Override
    public String toString() {
        return "Profile{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", picture='" + picture + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", created_Date=" + created_Date +
                ", update_Date=" + update_Date +
                '}';
    }
}
