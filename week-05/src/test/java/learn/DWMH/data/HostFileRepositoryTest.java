package learn.DWMH.data;

import learn.DWMH.model.Host;
import learn.DWMH.model.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {
    private static final String SEED_PATH="data/hosts_seed.csv";
    private static final String TEST_PATH="data/hosts_test.csv";
    private HostFileRepository repository=new HostFileRepository(TEST_PATH);

    @BeforeEach
    void setUp() throws IOException {
        Files.copy(Paths.get(SEED_PATH),Paths.get(TEST_PATH), StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Host> hosts= repository.findAll();
        assertEquals(10,hosts.size());
    }

    @Test
    void findByState() {
        List<Host> hosts= repository.findByState("TX");
        assertEquals(6,hosts.size());
    }

    @Test
    void findById() {
        Host actual= repository.findById("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        assertNotNull(actual);
        assertEquals("eyearnes0@sfgate.com",actual.getEmail());
    }

    @Test
    void add() throws DataException {
        Host host= new Host("Stevens","alexs414@fang.com","(555) 5423845","123 Seaseme St","Trenton","NJ","14815",new BigDecimal(68.00),new BigDecimal(89.00));
        repository.add(host);
        assertNotNull(repository.findById(host.getId()));
        Host actual= repository.findById(host.getId());
        assertEquals("Stevens",actual.getLastName());
    }
}