package menus.CottageDetails;

import menus.MenuScreen;
import utility.Utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CottageDetails implements MenuScreen {
    private final Scanner scan;

    public CottageDetails(Scanner scan) {
        this.scan = scan;
    }

    public void readFromFile() {
        String filePath = "src/Menus/CottageDetails/cottage_description.txt";
        //Detta är bara vid demo. Kommentera in vid tillfälle
      /*  if (Utility.macUser()) {
            filePath = System.getProperty("user.dir") + "/menus/CottageDetails/cottage_description.txt";
        } else {
            filePath = System.getProperty("user.dir") + "\\Menus\\CottageDetails\\cottage_description.txt";

        }*/
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) !=null) {
                System.out.println(line);
            }
        }catch (IOException e){
            System.err.println("Error reading file " + e.getMessage());
        }
    }

    @Override
    public void runMenu() {
        readFromFile();
        System.out.println("\n\"Skriv 'Exit' eller 'E' för att återgå till huvudmenyn:\" ");

        while(true) {
            String input = scan.nextLine();
            if(input.trim().equalsIgnoreCase("e") || input.trim().equalsIgnoreCase("exit")) {
                Utility.clearScreen();
                break;
            }else{
                System.out.println("Felaktig inmatning, skriv 'exit' eller 'e' för att återgå till huvudmenyn  ");
            }
        }
    }
}
