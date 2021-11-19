package learn.DWMH.domain;

import learn.DWMH.model.Guest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {
    public GuestService service= new GuestService(new GuestRepositoryDouble());

    @Test
    void findByLastName() {
        List<Guest> guests = service.findByLastName("Benitez");
        assertEquals("Galdis",guests.get(0).getFirstName());

    }
}