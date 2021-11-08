package learn.DWMH.data;

import learn.DWMH.model.Guest;
import learn.DWMH.model.Host;
import learn.DWMH.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {
    private static final String SEED_PATH="data/reservation_test/Seed.csv";
    private static final String TEST_PATH="data/reservation_test";
    private static final String TEST_PATH_H="data/hosts_test.csv";
    private HostFileRepository hostRepo=new HostFileRepository(TEST_PATH_H);
    private static final String TEST_PATH_G="data/guest_test.csv";
    private GuestFileRepository guestRepo=new GuestFileRepository(TEST_PATH_G);
    private ReservationFileRepository repository=new ReservationFileRepository(TEST_PATH,guestRepo,hostRepo);
    @BeforeEach
    void setUp() throws IOException {
        Files.copy(Paths.get(SEED_PATH),Paths.get("data/reservation_test/4c7f5ee4-a822-46c4-95d4-8dba7635922c.csv"), StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findByHost() {
        Host host= new Host();
        host.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        List<Reservation> reservations = repository.findByHost(host);
        assertEquals(13,reservations.size());
    }

    @Test
    void findById() {
        Host host= new Host();
        host.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        Reservation reservation= repository.findById(2,host);
        assertEquals(35,reservation.getGuest().getGuestId());

    }

    @Test
    void add() throws DataException, IOException {

        Host host= new Host();
        host.setId("4c7f5ee4-a822-46c4-95d4-8dba7635922c");
        host.setStandardRate(new BigDecimal(95.00));
        host.setWeekendRate(new BigDecimal(110.00));
        Guest guest= new Guest();
        guest.setGuestId(1001);
        Reservation reservation = new Reservation(host,guest, LocalDate.of(2020,06,17),LocalDate.of(2020,06,20));
        Reservation actual= repository.add(reservation);
        List<Reservation> reservations = repository.findByHost(host);
        assertNotNull(actual);
        assertEquals(11,reservations.size());

    }

    @Test
    void update() throws DataException {
        Host host= new Host();
        host.setId("4c7f5ee4-a822-46c4-95d4-8dba7635922c");
        host.setStandardRate(new BigDecimal(95.00));
        host.setWeekendRate(new BigDecimal(110.00));
        Guest guest= new Guest();
        guest.setGuestId(1001);
        Reservation reservation = new Reservation(host,guest, LocalDate.of(2020,06,17),LocalDate.of(2020,06,20));
        reservation.setId(1);
        boolean actual= repository.update(reservation);
        assertTrue(actual);
        assertEquals(1001,repository.findById(1,host).getGuest().getGuestId());

    }

    @Test
    void deleteById() throws DataException {
        Host host= new Host();
        host.setId("4c7f5ee4-a822-46c4-95d4-8dba7635922c");
        host.setStandardRate(new BigDecimal(95.00));
        host.setWeekendRate(new BigDecimal(110.00));
        Guest guest= new Guest();
        guest.setGuestId(1001);
        Reservation reservation = new Reservation(host,guest, LocalDate.of(2020,06,17),LocalDate.of(2020,06,20));
        reservation.setId(1);
        boolean actual= repository.delete(reservation);
        assertTrue(actual);
        assertNull(repository.findById(1,host));
    }
}