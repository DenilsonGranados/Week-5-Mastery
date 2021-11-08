package learn.DWMH.UI;

import learn.DWMH.data.DataException;
import learn.DWMH.domain.GuestService;
import learn.DWMH.domain.HostService;
import learn.DWMH.domain.ReservationService;
import learn.DWMH.domain.Result;
import learn.DWMH.model.Guest;
import learn.DWMH.model.Host;
import learn.DWMH.model.Reservation;

import java.util.List;

public class Controller {
    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }
    public void run() {
        view.displayHeader("Welcome to Sustainable Foraging");
        try {
            runAppLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() throws DataException {
        MenuOptions option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case View_Reservations:
                    ViewReservations();
                    break;
                case Make_Reservation:
                    MakeReservations();
                    break;
                case Edit_Reservation:
                    EditReservation();
                    break;
                case Cancel_Reservation:
                    CancelReservation();
                    break;
            }
        } while (option != MenuOptions.Exit);
    }
    private void ViewReservations(){
        view.displayHeader(MenuOptions.View_Reservations.getMessage());
        Host host= getHost();
        if (host == null) {
            return;
        }
        List<Reservation> reservations= reservationService.findByHost(host);
        view.displayHostReservation(reservations);

    }

    private void MakeReservations() throws DataException {
        view.displayHeader(MenuOptions.Make_Reservation.getMessage());
        Guest guest= getGuest();
        if (guest == null) {
            return;
        }
        Host host= getHost();
        if (host == null) {
            return;
        }
        List<Reservation> reservations= reservationService.findByHost(host);
        view.displayHostReservation(reservations);
        Reservation reservation= view.makeReservation(host,guest);
        Result<Reservation> result = reservationService.add(reservation);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Forage %s created.", result.getPayload().getId());
            view.displayStatus(true, successMessage);
        }
    }

    private void EditReservation() throws DataException {
        view.displayHeader(MenuOptions.Edit_Reservation.getMessage());
        List<Host> hosts= hostService.findByState(view.getState());
        Host host= view.chooseHost(hosts);
        if (host == null) {
            return;
        }
        List<Reservation> reservations= reservationService.findByHost(host);
        Reservation original= view.chooseReservation(reservations);
        Reservation updated= view.updateReservation(original);
        Result<Reservation> result = reservationService.update(updated);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Forage %s updated.", result.getPayload().getId());
            view.displayStatus(true, successMessage);
        }
    }

    private void CancelReservation() throws DataException {
        view.displayHeader(MenuOptions.Cancel_Reservation.getMessage());
        List<Host> hosts= hostService.findByState(view.getState());
        Host host= view.chooseHost(hosts);
        if (host == null) {
            return;
        }
        List<Reservation> reservations= reservationService.findByHost(host);
        Reservation original= view.chooseReservation(reservations);
        Result<Reservation> result = reservationService.delete(original);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Forage %s deleted.", result.getPayload().getId());
            view.displayStatus(true, successMessage);
        }
    }

    // support methods
    private Guest getGuest() {
        String lastNamePrefix = view.getGuestNamePrefix();
        List<Guest> guests = guestService.findByLastName(lastNamePrefix);
        return view.chooseGuest(guests);
    }
    private Host getHost() {
        String lastNamePrefix = view.getHostNamePrefix();
        List<Host> hosts = hostService.findByLastName(lastNamePrefix);
        return view.chooseHost(hosts);
    }


}
