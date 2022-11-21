package Ontdekstation013.ClimateChecker.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long UserID;

    @OneToMany(
            mappedBy = "owner",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Station> stations = new LinkedList<>();

    @NotBlank
    private String userName;

    @NotBlank
    @Email
    private String mailAddress;
    private byte Admin;




    public User(long id, List<Station> stations, String userName, String mailAddress, byte Admin) {
        this.UserID = id;
        this.stations = stations;
        this.userName = userName;
        this.mailAddress = mailAddress;
        this.Admin = Admin;
    }

    public User(String userName, String mailAddress) {
        this.userName = userName;
        this.mailAddress = mailAddress;
    }

    public User(String mailAddress, Long id) {
        this.UserID = id;
        this.mailAddress = mailAddress;
    }

}
