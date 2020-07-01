package entities;



import dao.Entity1;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

/**
 *
 * @author Jonas Terschlüsen - 3743918 - TIPB_jonasterschlüsen
 * {@code 423121@student.fontys.nl}
 */
@Getter
@Setter
@NoArgsConstructor
public class Customer implements Entity1<String> {

    private String id;
    private String companyName;
    private String firstName;
    private String lastName;
    private String email;
    private String taxNumber;
    private String phoneNumber;
    private String firstNameResp;
    private String lastNameResp;
    private String faxNumber;
    private boolean blockStatus;
    private String  addressId;


    /**
     *
     * @param id the natural id of a customer object (primary key)
     * @param companyName the companies Name
     * @param firstName first name of the employee who made the order
     * @param lastName last name of the employee who made the order
     * @param email e-mail address of the company
     * @param taxNumber tax number of the company
     * @param phoneNumber phone number of the Company
     * @param firstNameResp first name of the responsible person
     * @param lastNameResp last name of the responsible person
     * @param faxNumber fax number of the responsible person
     * @param blockStatus indicates whether the customer is blocked or not
     * @param addressId the id of the referenced address in the "address" relation
     */
    public Customer(String id, String companyName, String firstName, String lastName, 
            String email, String taxNumber, String phoneNumber, String firstNameResp, 
            String lastNameResp, String faxNumber, boolean blockStatus, String addressId) {
        this.id = id;
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.taxNumber = taxNumber;
        this.phoneNumber = phoneNumber;
        this.firstNameResp = firstNameResp;
        this.lastNameResp = lastNameResp;
        this.faxNumber = faxNumber;
        this.blockStatus = blockStatus;
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return this.companyName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getNaturalId() {
        return "id";
    }

}
