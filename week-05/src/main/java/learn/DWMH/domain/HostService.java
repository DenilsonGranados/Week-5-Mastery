package learn.DWMH.domain;

import learn.DWMH.data.HostRepository;
import learn.DWMH.model.Host;

import java.util.List;
import java.util.stream.Collectors;

public class HostService {
    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public List<Host> findByState(String StateAbbr){
        return repository.findByState(StateAbbr);
    }

    public List<Host> findByLastName(String abbr) {
        return repository.findAll().stream()
                .filter(i -> i.getLastName().startsWith(abbr))
                .collect(Collectors.toList());
    }

}
