package Ontdekstation013.ClimateChecker.features.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
<<<<<<< HEAD:ClimateChecker/src/main/java/Ontdekstation013/ClimateChecker/models/User.java
=======

>>>>>>> s3nj23:ClimateChecker/src/main/java/Ontdekstation013/ClimateChecker/features/user/User.java
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;
<<<<<<< HEAD:ClimateChecker/src/main/java/Ontdekstation013/ClimateChecker/models/User.java

    @OneToMany(
            mappedBy = "owner",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Station> stations = new LinkedList<>();
=======
>>>>>>> s3nj23:ClimateChecker/src/main/java/Ontdekstation013/ClimateChecker/features/user/User.java

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String userName;

    @NotBlank
    @Email
    private String mailAddress;

    private boolean Admin;

<<<<<<< HEAD:ClimateChecker/src/main/java/Ontdekstation013/ClimateChecker/models/User.java

    public User(long id, List<Station> stations, String firstName, String lastName, String mailAddress, String userName, boolean Admin) {
        this.userID = id;
        this.stations = stations;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.mailAddress = mailAddress;
        this.Admin = Admin;
    }

=======
>>>>>>> s3nj23:ClimateChecker/src/main/java/Ontdekstation013/ClimateChecker/features/user/User.java
    public User(long id, String firstName, String lastName, String mailAddress, String userName, boolean Admin) {
        this.userID = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.mailAddress = mailAddress;
        this.Admin = Admin;
    }

    //register
    public User(String mailAddress, String firstName, String lastName, String userName) {
        this.mailAddress = mailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }


    public User(String mailAddress, Long id) {
        this.userID = id;
        this.mailAddress = mailAddress;
    }
}
