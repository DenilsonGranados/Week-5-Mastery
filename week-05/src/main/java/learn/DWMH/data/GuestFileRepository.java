package learn.DWMH.data;

import learn.DWMH.model.Guest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuestFileRepository implements GuestRepository {
    private static final String HEADER = "guest_id,first_name,last_name,email,phone,state";
    private final String filePath;

    public GuestFileRepository(String filepath) {
        this.filePath = filepath;
    }

    @Override
    public List<Guest> findAll() {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 6) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    @Override
    public List<Guest> findByState(String stateAbbr) {
        return findAll().stream()
                .filter(i -> i.getState().equalsIgnoreCase(stateAbbr))
                .collect(Collectors.toList());
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
        List<Guest>all= findAll();
        all.add(guest);
        int nextId = all.stream()
                .mapToInt(Guest::getGuestId)
                .max()
                .orElse(0) + 1;
        guest.setGuestId(nextId);
        writeAll(all);
        return guest;
    }

    private void writeAll(List<Guest>guests) throws DataException {
        try (PrintWriter writer= new PrintWriter(filePath)){
            writer.println("id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate");
            for (Guest guest:guests){
                writer.println(serialize(guest));
            }
        }catch (IOException ex){
            throw new DataException(ex.getMessage(), ex);
        }
    }
    private String serialize(Guest guest){
        return  String.format("%s,%s,%s,%s,%s,%s",
                guest.getGuestId(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getEmail(),
                guest.getPhone(),
                guest.getState());
    }

    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setGuestId(Integer.parseInt( fields[0]));
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setEmail(fields[3]);
        result.setPhone(fields[4]);
        result.setState(fields[5]);
        return result;
    }
}
