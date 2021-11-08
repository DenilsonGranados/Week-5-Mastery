package learn.DWMH.data;

import learn.DWMH.model.Host;

import java.util.List;

public interface HostRepository {
    List<Host> findAll();

    List<Host> findByState(String stateAbbr);

    Host findById(String id);

    Host add(Host host) throws DataException;

    List<Host> findByLastName(String prefix);
}
