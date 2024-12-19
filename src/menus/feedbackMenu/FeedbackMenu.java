package menus.feedbackMenu;

import menus.MenuScreen;
import utility.Utility;

import java.io.*;
import java.util.Scanner;

public class FeedbackMenu implements MenuScreen {
private final Scanner scan;

    public FeedbackMenu(Scanner scan) {
        this.scan = scan;
    }

    @Override
    public void runMenu() {
        Utility.clearScreen();
        System.out.println("""
                == Omdömen ==
                (1) Lämna ett omdöme
                (2) Visa tidigare omdömen
                (3) Tillbaka till huvudmenyn
                """);
        readInput();
    }
    private void readInput() {
        while(true) {
            int feedbackInput = Utility.parseIfAble(scan.nextLine()).orElse(-1);
            switch (feedbackInput) {
                case 1 -> writeFeedback();
                case 2 -> readFeedback();
                case 3 -> {
                    Utility.clearScreen();
                    return;
                }
                default -> System.out.println("Fel på inmatning, försök igen");
            }
        }
    }
    private void writeFeedback() {
        System.out.println("Skriv in ditt omdöme och tryck på Enter för att spara ");
        String feedback = scan.nextLine();
        try(BufferedWriter bf = new BufferedWriter(new FileWriter("src/menus/feedbackMenu/feedback.txt", true))) {
            bf.write(feedback);
            bf.newLine();
            System.out.println("Ditt omdöme är sparat. Tack! ");
        } catch (IOException e) {
            System.out.println("Fel vid sparande av omdömet " + e.getMessage());
        }
    }
    private void readFeedback() {
        System.out.println(" = Omdömen av tidigare stughyrare =");
        try (BufferedReader br = new BufferedReader(new FileReader("src/menus/feedbackMenu/feedback.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("- " + line);
                }
            } catch (IOException e){
                System.err.println("Fel vid läsning av omdömen " + e.getMessage());
            }
        }
    }

