package learn.DWMH.domain;

import learn.DWMH.data.DataException;
import learn.DWMH.data.ReservationRepository;
import learn.DWMH.model.Guest;
import learn.DWMH.model.Host;
import learn.DWMH.model.Reservation;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository {
    private List<Reservation> reservations= new ArrayList<>();

    public ReservationRepositoryDouble() {
        Host host = new Host("Rodriguez","ar45@epsnj.net","(555) 1597364","123 Seaseme St","New York City", "NY","06801",new BigDecimal(111.00),new BigDecimal(145.00));
        host.setId("dhgsidrf-4660-fcdjsuid-465712-sdgfd");
        Guest guest= new Guest("Galdis","Benitez","gladisb@gmail.com","(555) 6548764","NJ");
        guest.setGuestId(1);
        Reservation reservation = new Reservation(host,guest, LocalDate.of(2022,06,17),LocalDate.of(2022,06,20));
        reservation.setId(1);
        reservations.add(reservation);
    }

    @Override
    public List<Reservation> findByHost(Host host) {
        return reservations;
    }

    @Override
    public Reservation findById(int id, Host host) {
        return null;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        return null;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        List<Reservation>all= findByHost(reservation.getHost());
        for (int r=0;r<all.size();r++){
            if (all.get(r).getId()== reservation.getId()){
                all.set(r,reservation);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException {
        List<Reservation>all= findByHost(reservation.getHost());
        for (int r=0;r<all.size();r++){
            if (all.get(r).getId()== reservation.getId()){
                all.remove(all.get(r));
                return true;
            }
        }
        return false;
    }
}
