package learn.DWMH.domain;

import learn.DWMH.data.DataException;
import learn.DWMH.data.ReservationRepository;
import learn.DWMH.model.Guest;
import learn.DWMH.model.Host;
import learn.DWMH.model.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    public ReservationService service= new ReservationService(new ReservationRepositoryDouble(), new HostRepositoryDouble(), new GuestRepositoryDouble());

    @Test
    void shouldNotAddDueToNull() throws DataException {
        Host host = new Host("Grace","abg32@epsnj.net","(486) 1234567","101 Crime Ave","Gotham", "NY","08067",new BigDecimal(40.00),new BigDecimal(55.00));
        Guest guest= new Guest("Demmy","Silent","gladisb@gmail.com","(555) 6548764","NJ");

        Reservation reservation = null;
        Result<Reservation> result= service.add(reservation);
        assertEquals("Nothing to save.",result.getErrorMessages().get(0));

        reservation = new Reservation(host,guest, LocalDate.of(2020,06,17),LocalDate.of(2020,06,20));
        reservation.setStartDate(null);
        result= service.add(reservation);
        assertEquals("Reservation start date is required.",result.getErrorMessages().get(0));

        reservation = new Reservation(host,guest, LocalDate.of(2020,06,17),LocalDate.of(2020,06,20));
        reservation.setEndDate(null);
        result= service.add(reservation);
        assertEquals("Reservation end date is required.",result.getErrorMessages().get(0));

        reservation = new Reservation(host,guest, LocalDate.of(2020,06,17),LocalDate.of(2020,06,20));
        reservation.setHost(null);
        result= service.add(reservation);
        assertEquals("Host is required.",result.getErrorMessages().get(0));

        reservation = new Reservation(host,guest, LocalDate.of(2020,06,17),LocalDate.of(2020,06,20));
        reservation.setGuest(null);
        result= service.add(reservation);
        assertEquals("Guest is required.",result.getErrorMessages().get(0));

    }
    @Test
    void shouldNotAddDueToFields() throws DataException {
        Host host = new Host("Grace","abg32@epsnj.net","(486) 1234567","101 Crime Ave","Gotham", "NY","08067",new BigDecimal(40.00),new BigDecimal(55.00));
        Guest guest= new Guest("Demmy","Silent","gladisb@gmail.com","(555) 6548764","NJ");

        Reservation reservation = new Reservation(host,guest, LocalDate.of(2020,06,20),LocalDate.of(2020,06,17));
        Result<Reservation> result= service.add(reservation);
        assertEquals("Reservation start date cannot be after end date.",result.getErrorMessages().get(0));

        reservation = new Reservation(host,guest, LocalDate.of(2020,06,17),LocalDate.of(2020,06,20));
        result= service.add(reservation);
        assertEquals("Reservation start date cannot be in the past.",result.getErrorMessages().get(0));
    }
    @Test
    void shouldNotAddDueToChildren() throws DataException {
        Host host = new Host("Grace","abg32@epsnj.net","(486) 1234567","101 Crime Ave","Gotham", "NY","08067",new BigDecimal(40.00),new BigDecimal(55.00));
        host.setId("asdqa-asda-1516-asdad-16185");
        Guest guest= new Guest("Demmy","Silent","gladisb@gmail.com","(555) 6548764","NJ");
        guest.setGuestId(2);

        Reservation reservation = new Reservation(host,guest, LocalDate.of(2022,06,20),LocalDate.of(2022,06,25));
        Result<Reservation> result= service.add(reservation);
        assertEquals("Host does not exist.",result.getErrorMessages().get(0));

        reservation = new Reservation(host,guest, LocalDate.of(2022,06,20),LocalDate.of(2022,06,25));
        reservation.getHost().setId("dhgsidrf-4660-fcdjsuid-465712-sdgfd");
        result= service.add(reservation);
        assertEquals("Guest does not exist.",result.getErrorMessages().get(0));

    }
    @Test
    void shouldNotAddDueToOverlap() throws DataException {
        Host host = new Host("Grace","abg32@epsnj.net","(486) 1234567","101 Crime Ave","Gotham", "NY","08067",new BigDecimal(40.00),new BigDecimal(55.00));
        host.setId("dhgsidrf-4660-fcdjsuid-465712-sdgfd");
        Guest guest= new Guest("Demmy","Silent","gladisb@gmail.com","(555) 6548764","NJ");
        guest.setGuestId(1);

        Reservation reservation = new Reservation(host,guest, LocalDate.of(2022,06,19),LocalDate.of(2022,06,25));
        Result<Reservation> result= service.add(reservation);
        assertEquals("This reservation and reservation 1 dates overlap",result.getErrorMessages().get(0));

    }
    @Test
    void shouldAdd() throws DataException {
        Host host = new Host("Grace","abg32@epsnj.net","(486) 1234567","101 Crime Ave","Gotham", "NY","08067",new BigDecimal(40.00),new BigDecimal(55.00));
        host.setId("dhgsidrf-4660-fcdjsuid-465712-sdgfd");
        Guest guest= new Guest("Demmy","Silent","gladisb@gmail.com","(555) 6548764","NJ");
        guest.setGuestId(1);

        Reservation reservation = new Reservation(host,guest, LocalDate.of(2022,07,19),LocalDate.of(2022,07,25));
        Result<Reservation> result= service.add(reservation);
        assertTrue(result.isSuccess());

    }

    @Test
    void update() throws DataException {
        Host host = new Host("Rodriguez","ar45@epsnj.net","(555) 1597364","123 Seaseme St","New York City", "NY","06801",new BigDecimal(111.00),new BigDecimal(145.00));
        host.setId("dhgsidrf-4660-fcdjsuid-465712-sdgfd");
        Guest guest= new Guest("Demmy","Silent","gladisb@gmail.com","(555) 6548764","NJ");
        guest.setGuestId(1);
        LocalDate old= service.findByHost(host).get(0).getStartDate();
        Reservation reservation = new Reservation(host,guest, LocalDate.of(2022,07,19),LocalDate.of(2022,07,25));
        reservation.setId(1);
        Result<Reservation> result= service.update(reservation);
        assertTrue(result.isSuccess());

        LocalDate newdate = service.findByHost(host).get(0).getStartDate();
        assertNotEquals(old,newdate);

    }

    @Test
    void delete() throws DataException {
        Host host = new Host("Rodriguez","ar45@epsnj.net","(555) 1597364","123 Seaseme St","New York City", "NY","06801",new BigDecimal(111.00),new BigDecimal(145.00));
        host.setId("dhgsidrf-4660-fcdjsuid-465712-sdgfd");
        Guest guest= new Guest("Demmy","Silent","gladisb@gmail.com","(555) 6548764","NJ");
        guest.setGuestId(1);
        Reservation reservation= service.findByHost(host).get(0);

        Result<Reservation> result= service.delete(reservation);
        assertTrue(result.isSuccess());

        assertEquals(0,service.findByHost(host).size());
    }
}