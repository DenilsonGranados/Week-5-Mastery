package learn.DWMH.data;

import learn.DWMH.model.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll();

    List<Guest> findByState(String stateAbbr);

    Guest findById(int id);

     Guest add(Guest guest) throws DataException;
}
