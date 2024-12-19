package menus;

import menus.CottageDetails.CottageDetails;
import bookingsMenu.BookingsMenu;
import utility.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    public MainMenu() {
        Utility.clearScreen();
        try (Scanner scan = new Scanner(System.in)) {
            List<Command> commands = createCommands(scan);
            mainMenuOptions();
            String line;
            while ((line = scan.nextLine()) !=null) {
                try {
                    int menuOption = Integer.parseInt(line)-1;
                    if(menuOption >= 0 && menuOption < commands.size()) {
                        commands.get(menuOption).execute();
                        mainMenuOptions();
                    }else{
                        throw new IndexOutOfBoundsException();
                    }

                }catch (Exception e) {
                    Utility.clearScreen();
                    mainMenuOptions();
                    System.err.println("Felaktig inmatning, försök igen.");
                }
            }
        }
    }

    public void mainMenuOptions(){
        System.out.print("""
                Välkommen till Stugan på Rödmyravägen 5 i Järvsö
                [1] Information om aktivitetsförråd
                [2] Stuginformation
                [3] Kontaktuppgifter
                [4] Aktiviteter i området
                [5] Boka stugan
                [6] Avsluta
                """);
        System.out.println("Skriv in ditt val: ");
    }

    public List<Command> createCommands(Scanner scan) {

        List<Command> commands = new ArrayList<>();
        commands.add(new MenuCommand(new ActivityStorage(scan)));
        commands.add(new MenuCommand(new CottageDetails(scan)));
        commands.add(new MenuCommand(new ContactDetails(scan)));
        commands.add(new MenuCommand(new LocalActivities(scan)));
        commands.add(new MenuCommand(new BookingsMenu(scan)));
       // commands.add(new MenuCommand(new FeedbackMenu(scan)));
        commands.add(() -> System.exit(0));
        return commands;
    }
}

