package bookingsMenu;

import bookingsMenu.states.BookingState;
import bookingsMenu.states.ChooseSeasonState;
import utility.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class BookingsMenu implements menus.MenuScreen {
    private final Scanner scan;
    private BookingType bookingType;
    private BookingState currentState;
    private final Booking booking = new Booking();
    private ArrayList<String> pendingBookings = new ArrayList<>();
    private final ArrayList<String> availableSummerWeeks = new ArrayList<>();
    private final ArrayList<String> availableWinterWeeks = new ArrayList<>();

    public BookingsMenu(Scanner scan) {
        this.scan = scan;
        loadAvailableWeeks();
        currentState = new ChooseSeasonState(scan);
    }

    public void runMenu() {
        while (currentState != null) {
            currentState.handle(this);
        }
        pendingBookings = new ArrayList<>();
        currentState = new ChooseSeasonState(scan);
    }

    public void setCurrentState(BookingState chosenState) {
        this.currentState = chosenState;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }

    public ArrayList<String> getPendingBookings() {
        return pendingBookings;
    }

    public List<String> getAvailableSummerWeeks() {
        return availableSummerWeeks;
    }

    public List<String> getAvailableWinterWeeks() {
        return availableWinterWeeks;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public Booking getBooking() {
        return booking;
    }

    public void addBookingToPending(int chosenWeek) {
        String green = "\u001B[32m";
        String resetColor = "\u001B[0m";
        if (bookingType == BookingType.SUMMER) {
            System.out.printf("%s%s är tillagd i din bokning.%s%n",
                    green, availableSummerWeeks.get(chosenWeek), resetColor);
            pendingBookings.add(availableSummerWeeks.get(chosenWeek));
            availableSummerWeeks.remove(chosenWeek);
        } else {
            System.out.printf("%s%s är tillagd i din bokning.%s%n",
                    green, availableWinterWeeks.get(chosenWeek), resetColor);
            pendingBookings.add(availableWinterWeeks.get(chosenWeek));
            availableWinterWeeks.remove(chosenWeek);
        }
    }

    public boolean validWeek(int chosenWeek) {
        if (bookingType == BookingType.SUMMER) {
            return chosenWeek < availableSummerWeeks.size() && chosenWeek >= 0;
        } else {
            return chosenWeek < availableWinterWeeks.size() && chosenWeek >= 0;
        }
    }

    public boolean availableWeeks() {
        if (bookingType == BookingType.SUMMER) {
            return !availableSummerWeeks.isEmpty();
        } else {
            return !availableWinterWeeks.isEmpty();
        }
    }

    public void printAvailableWeeks() {
        if (bookingType == BookingType.SUMMER) {
            for (int i = 0; i < availableSummerWeeks.size(); i++) {
                System.out.printf("%d: %s%n", i + 1, availableSummerWeeks.get(i));
            }
        } else {
            for (int i = 0; i < availableWinterWeeks.size(); i++) {
                System.out.printf("%d: %s%n", i + 1, availableWinterWeeks.get(i));
            }
        }
    }

    private void loadAvailableWeeks() {
        String filePath;
        //Kommentera ut under demo
        filePath = "src/bookingsMenu/availableWeeks.properties";

        //Kommentera in vid demo av programmet. Detta möjliggör clear av konsolskärmen
      /*  if (Utility.macUser()) {
            filePath = System.getProperty("user.dir") + "/bookingsMenu/availableWeeks.properties";
        } else {
            filePath = System.getProperty("user.dir") + "\\bookingsMenu\\availableWeeks.properties";
        }*/

        Properties properties = new Properties();
        String summerWeeks, winterWeeks;
        String[] parts;
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
            summerWeeks = properties.getProperty("available_summer_weeks");
            winterWeeks = properties.getProperty("available_winter_weeks");
            if (summerWeeks != null) {
                parts = summerWeeks.split(",");
                for (String week : parts) {
                    availableSummerWeeks.add(week.trim());
                }
            }
            if (winterWeeks != null) {
                parts = winterWeeks.split(",");
                for (String week : parts) {
                    availableWinterWeeks.add(week.trim());
                }
            }
        } catch (IOException e) {
            System.err.printf("Unable to open file: %s%n", filePath);
        }
    }
}


