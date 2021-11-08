package learn.DWMH.model;

import java.math.BigDecimal;

public class Guest {
    private int guest_id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String state;

    public Guest() {
    }

    public Guest(String first_name, String last_name, String email, String phone, String state) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.state = state;
    }

    public int getGuestId() {
        return guest_id;
    }

    public void setGuestId(int guest_id) {
        this.guest_id = guest_id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
