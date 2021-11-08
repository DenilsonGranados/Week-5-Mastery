package learn.DWMH.data;

import learn.DWMH.model.Host;
import learn.DWMH.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByHost(Host host);

    Reservation findById(int id, Host host);

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

    boolean delete(Reservation reservation) throws DataException;
}
