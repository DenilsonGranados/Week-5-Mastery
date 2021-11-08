package learn.DWMH.UI;

import learn.DWMH.model.Guest;
import learn.DWMH.model.Host;
import learn.DWMH.model.Reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class View {
    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MenuOptions selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MenuOptions option : MenuOptions.values()) {
            io.printf("%s. %s%n", option.getValue(), option.getMessage());
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MenuOptions.fromValue(io.readInt(message, min, max));
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }
    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }
    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }
    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }

    public void displayHostReservation(List<Reservation> reservations){
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }
        int index=1;
        io.printf("%-10s%-20s%-20s%-10s%-10s%n","Index","Start","End","Guest Id","Total");
        for (Reservation res : reservations.stream().limit(25).collect(Collectors.toList())) {
            io.printf("%-10s%-20s%-20s%-10s%-10s%n", index++, res.getStartDate(), res.getEndDate(),res.getGuest().getGuestId(),res.getTotal());
        }
    }
    public String getState(){
        return io.readRequiredString("Abbreviation of desired State: ");
    }


    public String getGuestNamePrefix() {
        return io.readRequiredString("Guest last name starts with: ");
    }
    public String getHostNamePrefix() {
        return io.readRequiredString("Host last name starts with: ");
    }

    public Reservation makeReservation(Host host, Guest guest){
        Reservation reservation= new Reservation();
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setStartDate(io.readLocalDate("Start date in the form of yyyy-MM-dd: "));
        reservation.setEndDate(io.readLocalDate("End date in the form of yyyy-MM-dd: "));
        return reservation;
    }

    public Reservation updateReservation(Reservation reservation){
        reservation.setStartDate(io.readLocalDate("Start date in the form of yyyy-MM-dd: "));
        reservation.setEndDate(io.readLocalDate("End date in the form of yyyy-MM-dd: "));
        return reservation;
    }

    public Guest chooseGuest(List<Guest> guests) {
        if (guests.size() == 0) {
            io.println("No guests found");
            return null;
        }
        int index = 1;
        io.printf("%-10s%-35s%-18s%-10s%n","Index","Email","First Name","Last Name");
        for (Guest guest : guests.stream().limit(25).collect(Collectors.toList())) {
            io.printf("%-10s%-35s%-18s%-10s%n", index++, guest.getEmail(),guest.getFirstName(), guest.getLastName());
        }
        index--;

        if (guests.size() > 25) {
            io.println("More than 25 guests found. Showing first 25. Please refine your search.");
        }
        io.println("0: Exit");
        String message = String.format("Select a guest by their index [0-%s]: ", index);

        index = io.readInt(message, 0, index);
        if (index <= 0) {
            return null;
        }
        return guests.get(index - 1);
    }

    public Host chooseHost(List<Host> hosts) {

        if (hosts.size() == 0) {
            io.println("No hosts found");
            return null;
        }
        int index = 1;
        io.printf("%-10s%-35s%-15s%-10s%-10s%n","Index","Email","Last Name","Week Rate","Weekend Rate");
        for (Host host : hosts.stream().limit(25).collect(Collectors.toList())) {
            io.printf("%-10s%-35s%-15s%-10s%-10s%n", index++, host.getEmail(), host.getLastName(),host.getStandardRate(),host.getWeekendRate());
        }
        index--;

        if (hosts.size() > 25) {
            io.println("More than 25 hosts found. Showing first 25. Please refine your search.");
        }
        io.println("0: Exit");
        String message = String.format("Select a host by their index [0-%s]: ", index);

        index = io.readInt(message, 0, index);
        if (index <= 0) {
            return null;
        }
        return hosts.get(index - 1);
    }
    public Reservation chooseReservation(List<Reservation> reservations) {
        if (reservations.size() == 0) {
            io.println("No reservations found");
            return null;
        }
        int index = 1;
        io.printf("%-10s%-10s%-10s%-10s%-10s%n","Index","Start","End","Guest Id","Total");
        for (Reservation res : reservations.stream().limit(25).collect(Collectors.toList())) {
            io.printf("%-10s%-10s%-10s%-10s%-10s%n", index++, res.getStartDate(), res.getEndDate(),res.getGuest().getGuestId(),res.getTotal());
        }
        index--;

        if (reservations.size() > 25) {
            io.println("More than 25 reservation found. Showing first 25. Please refine your search.");
        }
        io.println("0: Exit");
        String message = String.format("Select a reservation by their index [0-%s]: ", index);

        index = io.readInt(message, 0, index);
        if (index <= 0) {
            return null;
        }
        reservations.get(index - 1).setId(index);
        return reservations.get(index - 1);
    }


}
