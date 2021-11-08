package learn.DWMH.domain;

import learn.DWMH.data.*;
import learn.DWMH.model.Host;
import learn.DWMH.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public class ReservationService {

    private final ReservationRepository reservationRepo;
    private final HostRepository hostRepo;
    private final GuestRepository guestRepo;

    public ReservationService(ReservationRepository reservationRepo, HostRepository hostRepo, GuestRepository guestRepo) {
        this.reservationRepo = reservationRepo;
        this.hostRepo = hostRepo;
        this.guestRepo = guestRepo;
    }

    public List<Reservation> findByHost(Host host){
        return reservationRepo.findByHost(host);
    }

    public  Reservation findById(int id, Host host){
        return reservationRepo.findById(id,host);
    }

    public Result <Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(reservationRepo.add(reservation));
        return result;
    }

    public Result<Reservation> update(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        if (reservationRepo.update(reservation)){
            result.setPayload(reservation);
        }

        return result;
    }

    public Result<Reservation> delete(Reservation reservation) throws DataException {
        Result<Reservation> result = validateDelete(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        if (reservationRepo.delete(reservation)){
            result.setPayload(reservation);
        }

        return result;
    }

    private Result<Reservation> validate(Reservation reservation){
        Result<Reservation> result= validateNull(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        validateFields(reservation,result);
        if (!result.isSuccess()) {
            return result;
        }
        validateChildrenExist(reservation,result);
        if (!result.isSuccess()) {
            return result;
        }
        validateOverlap(reservation,result);

        return result;
    }
    private Result<Reservation> validateDelete(Reservation reservation){
        Result<Reservation> result= validateNull(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        validateChildrenExist(reservation,result);
        if (!result.isSuccess()) {
            return result;
        }
        validatePast(reservation,result);

        return result;
    }

    private Result<Reservation> validateNull(Reservation reservation){
        Result<Reservation> result = new Result<>();

        if (reservation == null) {
            result.addErrorMessage("Nothing to save.");
            return result;
        }

        if (reservation.getStartDate() == null) {
            result.addErrorMessage("Reservation start date is required.");
        }
        if (reservation.getEndDate() == null) {
            result.addErrorMessage("Reservation end date is required.");
        }
        if (reservation.getHost() == null) {
            result.addErrorMessage("Host is required.");
        }
        if (reservation.getGuest() == null) {
            result.addErrorMessage("Guest is required.");
        }

        return result;
    }

    private void validateFields(Reservation reservation, Result<Reservation> result) {
        // No bad dates.
        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            result.addErrorMessage("Reservation start date cannot be after end date.");
        }
        if (reservation.getStartDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Reservation start date cannot be in the past.");
        }
    }

    private void validateChildrenExist(Reservation reservation, Result<Reservation> result) {
        if (reservation.getHost().getId() == null
                || hostRepo.findById(reservation.getHost().getId()) == null) {
            result.addErrorMessage("Host does not exist.");
        }

        if (guestRepo.findById(reservation.getGuest().getGuestId()) == null) {
            result.addErrorMessage("Guest does not exist.");
        }
    }

    private void validateOverlap(Reservation reservation, Result<Reservation> result){
        //https://stackoverflow.com/questions/17106670/how-to-check-a-timeperiod-is-overlapping-another-time-period-in-java
        List<Reservation> all= reservationRepo.findByHost(reservation.getHost());
        for (Reservation res: all){
            if (((reservation.getStartDate().isAfter(res.getStartDate()) || reservation.getStartDate().isEqual(res.getStartDate())) &&
                    (reservation.getStartDate().isBefore(res.getEndDate()))|| reservation.getStartDate().isEqual(res.getEndDate())) ||
                    ((res.getStartDate().isAfter(reservation.getStartDate()) || res.getStartDate().isEqual(reservation.getStartDate())) &&
                    ((res.getStartDate().isBefore(reservation.getEndDate())) || res.getStartDate().isEqual(reservation.getEndDate())))){
                result.addErrorMessage("This reservation and reservation "+Integer.toString(res.getId())+" dates overlap");
            }
        }
    }

    private void validatePast(Reservation reservation, Result<Reservation> result){
        if (reservation.getStartDate().isBefore(LocalDate.now())){
            result.addErrorMessage("Can not cancel reservation that has already passed,");
        }
    }
}
