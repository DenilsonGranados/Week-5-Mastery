package learn.DWMH.model;

import java.math.BigDecimal;

public class Host {
    private String id;
    private String last_name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String postal_code;
    private BigDecimal standard_rate;
    private BigDecimal weekend_rate;

    public Host() {
    }

    public Host(String last_name, String email, String phone, String address, String city, String state, String postal_code, BigDecimal standard_rate, BigDecimal weekend_rate) {
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
        this.standard_rate = standard_rate;
        this.weekend_rate = weekend_rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postal_code;
    }

    public void setPostalCode(String postal_code) {
        this.postal_code = postal_code;
    }

    public BigDecimal getStandardRate() {
        return standard_rate;
    }

    public void setStandardRate(BigDecimal standard_rate) {
        this.standard_rate = standard_rate;
    }

    public BigDecimal getWeekendRate() {
        return weekend_rate;
    }

    public void setWeekendRate(BigDecimal weekend_rate) {
        this.weekend_rate = weekend_rate;
    }
}
