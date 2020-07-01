package testEntities;

import dao.Entity1;

public class User implements Entity1<String> {

    public User() {
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getNaturalId() {
        return null;
    }
}
