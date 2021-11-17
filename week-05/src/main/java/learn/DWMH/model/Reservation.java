package learn.DWMH.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

public class Reservation {
    private int id;
    private Host host;
    private Guest guest;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal total;

    public Reservation() {
    }

    public Reservation(Host host, Guest guest, LocalDate startDate, LocalDate endDate) {
        this.host = host;
        this.guest = guest;
        this.total = total;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }



//        public void setTotal() {
//        BigDecimal days= new BigDecimal(period.getDays());
//        // Mayve have a loop that starts on the starting date, and iterate day by day, and each day check if its a weekend or weekday, apply set date.
//        this.total = days.multiply(host.getStandardRate());// For now only  calculate via standard rate
//    }
}
