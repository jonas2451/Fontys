package heidenliquids.customer;

/**
 *
 * @author Jonas Terschlüsen - 3743918 - TIPB_jonasterschlüsen
 * {@code 423121@student.fontys.nl}
 */
public class Customer {
    
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
    private int     customerNumber;

    /**
     * 
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
     * @param customerNumber indentification number for a customer
     */
    public Customer(String companyName, String firstName, String lastName, String email, String taxNumber, String phoneNumber, String firstNameResp, String lastNameResp, String faxNumber, boolean blockStatus, int customerNumber) {
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
        this.customerNumber = customerNumber;
    }
    
    public void placeOrder(){
        //places an order???
    }

    @Override
    public String toString() {
        return "Customer: " + this.companyName + "First name: " + this.firstName + "Last name: " + this.lastName + "Email: " + this.email + "TaxNr: " + this.taxNumber + "Phone number: " + this.phoneNumber + "First name responsible: " + this.firstNameResp + "Last name responsible: " + this.lastNameResp + "Fax number: " + this.faxNumber + "Block status: " + this.blockStatus + "Customer number: " + this.customerNumber;
    }
    
    //getter and setter methods
    public String getCompanyName() {
        return companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public String getFirstNameResp() {
        return firstNameResp;
    }

    public String getLastNameResp() {
        return lastNameResp;
    }

    public void setFirstNameResp(String firstNameResp) {
        this.firstNameResp = firstNameResp;
    }

    public void setLastNameResp(String lastNameResp) {
        this.lastNameResp = lastNameResp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }
    
    public boolean getBlockStatus() {
        return this.blockStatus;
    }

    public void setBlockStatus(boolean blockStatus) {
        this.blockStatus = blockStatus;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }
}
