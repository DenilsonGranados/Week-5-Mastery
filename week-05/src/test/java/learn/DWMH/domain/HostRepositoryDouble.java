package learn.DWMH.domain;

import learn.DWMH.data.DataException;
import learn.DWMH.data.HostRepository;
import learn.DWMH.model.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository {
    private List<Host> hosts= new ArrayList<>();

    public HostRepositoryDouble() {
        Host host = new Host("Grace","abg32@epsnj.net","(486) 1234567","101 Crime Ave","Gotham", "NY","08067",new BigDecimal(40.00),new BigDecimal(55.00));
        host.setId("dhgsidrf-4660-fcdjsuid-465712-sdgfd");
        hosts.add(host);
    }

    @Override
    public List<Host> findAll() {
        return hosts;
    }

    @Override
    public List<Host> findByState(String stateAbbr) {
        return null;
    }

    @Override
    public Host findById(String id) {
        return hosts.stream().filter(i->i.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    @Override
    public Host add(Host host) throws DataException {
        return null;
    }

}
