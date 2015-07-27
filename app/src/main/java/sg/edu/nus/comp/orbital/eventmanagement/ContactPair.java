package sg.edu.nus.comp.orbital.eventmanagement;

/**
 * Created by Larry on 13/7/15.
 */
public class ContactPair {
    protected String username = null;
    protected String phoneNumber = null;

    public ContactPair(String name, String phone) throws IllegalArgumentException {
        if (name == null || phone == null) {
            throw new IllegalArgumentException("Invalid argument for contact pair!");
        }
        username = name;
        phoneNumber = phone;
    }

    public String getName() {
        return username;
    }

    public String getNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException(" Invalid argument for contact pair!");
        }
        username = name;
    }

    public void setNumber(String number) {
        if (number == null) {
            throw new IllegalArgumentException(" Invalid argument for contact pair!");
        }
        phoneNumber = number;
    }
}
