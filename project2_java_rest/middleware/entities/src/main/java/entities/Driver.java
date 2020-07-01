package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Driver {

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String accountNumber;
    private String license;
    private LocalDate dateOfBirth;
    private LocalDate dateOfJoining;

    public Driver(String firstName, String lastName, String email, LocalDate DoB, String Address, LocalDate DoJ,
                  String accountNumber, String license){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = DoB;
        this.address = Address;
        this.dateOfJoining = DoJ;
        this.accountNumber = accountNumber;
        this.license = license;


    }
}
