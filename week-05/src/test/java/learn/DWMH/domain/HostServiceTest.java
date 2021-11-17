package learn.DWMH.domain;

import learn.DWMH.model.Host;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {
    public HostService service = new HostService(new HostRepositoryDouble());
    @Test
    void findByLastName() {
        List<Host> hosts= service.findByLastName("Grace");
        assertEquals("abg32@epsnj.net",hosts.get(0).getEmail());
    }
}
