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

        String message = String.format("Select [%s-%s]: ", min, max );
        return MenuOptions.fromValue(io.readInt(message, min, max));
    }

    public void printExit(){
        io.print("Returning to Main Menu");
    }

    public void noReservations(){
        io.print("No reservations for this host.Returning to Main Menu");
    }
    public void noCancelation(){
        io.print("Cancelling aborted.Returning to Main Menu");
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
    public int confirmReservation(Reservation reservation){
        io.printf("%s,%s,%s,%s,%s%n",
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuest().getGuestId(),
                reservation.getTotal());
        return io.readInt("Do you confirm the above reservation? yes[1] or no[0]? ",0,1);
    }


    public void cancelReservation(){
        io.print("Reservation is cancel.");
    }

    public void displayHostReservation(List<Reservation> reservations){
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }
        for (Reservation reservation : reservations) {
            io.printf("%s,%s,%s,%s,%s%n",
                    reservation.getId(),
                    reservation.getStartDate(),
                    reservation.getEndDate(),
                    reservation.getGuest().getGuestId(),
                    reservation.getTotal());
        }
    }
    public String getState(){
        return io.readRequiredString("Abbreviation of desired State: ");
    }

    public Host getHost(List<Host> hosts) {
        Stream<Host> hostStream = hosts.stream();
        String email;
        boolean looper=false;
        do {
            email = io.readRequiredString("Choose an Host email: ");
            String finalEmail = email;
            if (!hostStream.anyMatch(h -> h.getEmail().equalsIgnoreCase(finalEmail))) {
                System.out.println("That email doesn't match any of the list. Please choose another.");
                looper=true;
            }
        } while (looper);
        HashMap<String, Host> hostEmailId= new HashMap<>();
        for(Host host : hosts){
            hostEmailId.put(host.getEmail(),host);
        }
        Host host= hostEmailId.get(email);
        return host;
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
        for (Guest guest : guests.stream().limit(25).collect(Collectors.toList())) {
            io.printf("%s: %s %s%n", index++, guest.getFirstName(), guest.getLastName());
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
        for (Host host : hosts.stream().limit(25).collect(Collectors.toList())) {
            io.printf("%s: %s, %s%n", index++, host.getEmail(), host.getLastName());
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
        for (Reservation res : reservations.stream().limit(25).collect(Collectors.toList())) {
            io.printf("%s: %s, %s%n", index++, res.getStartDate(), res.getEndDate());
        }
        index--;

        if (reservations.size() > 25) {
            io.println("More than 25 hosts found. Showing first 25. Please refine your search.");
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
