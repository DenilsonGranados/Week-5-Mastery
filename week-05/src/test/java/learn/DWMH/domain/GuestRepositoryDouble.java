package learn.DWMH.domain;

import learn.DWMH.data.DataException;
import learn.DWMH.data.GuestRepository;
import learn.DWMH.model.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {
    private List<Guest>guests= new ArrayList<>();

    public GuestRepositoryDouble() {
        Guest guest= new Guest("Galdis","Benitez","gladisb@gmail.com","(555) 6548764","NJ");
        guest.setGuestId(1);
        guests.add(guest);
    }

    @Override
    public List<Guest> findAll() {
        return guests;
    }

    @Override
    public List<Guest> findByState(String stateAbbr) {
        return null;
    }

    @Override
    public Guest findById(int id) {
        return findAll().stream()
                .filter(i -> i.getGuestId()==(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest add(Guest guest) throws DataException {
        return null;
    }
}
