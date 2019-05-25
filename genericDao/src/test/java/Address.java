import dao.Entity1;

public class Address implements Entity1<Integer> {

    int id;
    String country;
    String city;
    String street;
    int number;

    public Address(int id, String country, String city, String street, int number) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public String getNaturalId() {
        return null;
    }
}
