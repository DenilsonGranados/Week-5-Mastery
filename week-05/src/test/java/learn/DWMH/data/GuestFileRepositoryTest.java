package learn.DWMH.data;

import learn.DWMH.model.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {
    private static final String SEED_PATH="data/guest_seed.csv";
    private static final String TEST_PATH="data/guest_test.csv";
    private GuestFileRepository repository=new GuestFileRepository(TEST_PATH);

    @BeforeEach
    void setUp() throws IOException {
        Files.copy(Paths.get(SEED_PATH),Paths.get(TEST_PATH), StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Guest>guests= repository.findAll();
        assertEquals(20,guests.size());
    }


    @Test
    void findByState() {
        List<Guest> guests=repository.findByState("TX");
        assertEquals(3,guests.size());
    }

    @Test
    void findById() {
        Guest guest =repository.findById(8);
        assertNotNull(guest);
        assertEquals("ndodimead7@yellowbook.com",guest.getEmail());
    }
    @Test
    void findNotById() {
        Guest guest =repository.findById(40);
        assertNull(guest);
    }


    @Test
    void add() throws DataException {
        Guest guest= new Guest("Alex","Stevens","alexs414@fang.com","(555) 5423845","NJ");
        repository.add(guest);
        assertNotNull(repository.findById(21));
        Guest actual= repository.findById(21);
        assertEquals("Alex",actual.getFirstName());
    }
}