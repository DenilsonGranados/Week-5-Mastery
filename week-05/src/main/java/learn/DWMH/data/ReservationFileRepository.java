package learn.DWMH.data;

import learn.DWMH.model.Guest;
import learn.DWMH.model.Host;
import learn.DWMH.model.Reservation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {
    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;
    private final GuestRepository guestRepo;
    private final HostRepository hostRepo;


    public ReservationFileRepository(String directory, GuestRepository guestRepo, HostRepository hostRepo) {
        this.directory = directory;
        this.guestRepo = guestRepo;
        this.hostRepo = hostRepo;
    }

    @Override
    public List<Reservation> findByHost(Host host) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(host.getId())))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields,host.getId()));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }


    @Override
    public Reservation findById(int id, Host host) {
        return findByHost(host).stream()
                .filter(i -> i.getId()==id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        reservation.setTotal(calcTotal(reservation));
        List<Reservation>all= findByHost(reservation.getHost());
        int nextId = all.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;
        reservation.setId(nextId);
        all.add(reservation);
        writeAll(all,reservation.getHost());
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        reservation.setTotal(calcTotal(reservation));
        List<Reservation>all= findByHost(reservation.getHost());
        for (int r=0;r<all.size();r++){
            if (all.get(r).getId()== reservation.getId()){
                all.set(r,reservation);
                writeAll(all,reservation.getHost());
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
                writeAll(all, reservation.getHost());
                return true;
            }
        }
        return false;
    }

    private BigDecimal calcTotal(Reservation reservation){
        LocalDate startDate= reservation.getStartDate();
        Host host= reservation.getHost();
        LocalDate date=startDate;
        BigDecimal tot= new BigDecimal(0);
        while (!date.isEqual(reservation.getEndDate().plusDays(1))){
            if (date.getDayOfWeek()== DayOfWeek.MONDAY ||
                    date.getDayOfWeek()== DayOfWeek.TUESDAY ||
                    date.getDayOfWeek()== DayOfWeek.WEDNESDAY ||
                    date.getDayOfWeek()== DayOfWeek.THURSDAY ||
                    date.getDayOfWeek()== DayOfWeek.FRIDAY){
                tot= tot.add(host.getStandardRate().setScale(2, RoundingMode.HALF_EVEN));
            }else {
                tot=tot.add(host.getWeekendRate().setScale(2,RoundingMode.HALF_EVEN));
            }
            date= date.plusDays(1);
        }
        return tot;
    }
    private String getFilePath(String hostID) {
        return Paths.get(directory, hostID + ".csv").toString();
    }

    private void writeAll(List<Reservation>reservations,Host host) throws DataException {
        try (PrintWriter writer= new PrintWriter(getFilePath(host.getId()))){
            writer.println(HEADER);
            for (Reservation reservation:reservations){
                writer.println(serialize(reservation));
            }
        }catch (IOException ex){
            throw new DataException(ex.getMessage(), ex);
        }
    }
    private String serialize(Reservation reservation){
        return  String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuest().getGuestId(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields,String hostId) {
        Guest guest= guestRepo.findById(Integer.parseInt(fields[3]));
        Host host= hostRepo.findById(hostId);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1],dateFormat));
        result.setEndDate(LocalDate.parse(fields[2],dateFormat));
        result.setGuest(guest);
        result.setTotal(new BigDecimal(fields[4]));
        result.setHost(host);
        return result;
    }
}
