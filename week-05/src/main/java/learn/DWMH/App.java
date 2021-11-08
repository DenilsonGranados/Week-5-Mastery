package learn.DWMH;

import learn.DWMH.UI.ConsoleIO;
import learn.DWMH.UI.Controller;
import learn.DWMH.UI.View;
import learn.DWMH.data.GuestFileRepository;
import learn.DWMH.data.GuestRepository;
import learn.DWMH.data.HostFileRepository;
import learn.DWMH.data.ReservationFileRepository;
import learn.DWMH.domain.GuestService;
import learn.DWMH.domain.HostService;
import learn.DWMH.domain.ReservationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {

            ApplicationContext context = new ClassPathXmlApplicationContext("dependency-configuration.xml");

            Controller controller = context.getBean(Controller.class);
            // Run the app!
            controller.run();
//        ConsoleIO io = new ConsoleIO();
//        View view = new View(io);
//
//        HostFileRepository hostFileRepository= new HostFileRepository("data/hosts.csv");
//        GuestFileRepository guestFileRepository = new GuestFileRepository("data/guests.csv");
//        ReservationFileRepository reservationFileRepository= new ReservationFileRepository("data/reservations");
//
//        HostService hostService= new HostService(hostFileRepository);
//        GuestService guestService= new GuestService(guestFileRepository);
//        ReservationService reservationService= new ReservationService(reservationFileRepository,hostFileRepository,guestFileRepository);
//
//        Controller controller= new Controller(guestService,hostService,reservationService,view);
//        controller.run();
    }
}
