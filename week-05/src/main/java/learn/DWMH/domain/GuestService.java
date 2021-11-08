package learn.DWMH.domain;

import learn.DWMH.data.GuestRepository;
import learn.DWMH.model.Guest;

import java.util.List;
import java.util.stream.Collectors;

public class GuestService {
    private final GuestRepository repository;
    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public List<Guest> findByLastName(String prefix) {
        return repository.findAll().stream()
                .filter(i -> i.getLastName().startsWith(prefix))
                .collect(Collectors.toList());
    }

}
