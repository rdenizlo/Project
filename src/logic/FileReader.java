package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

    public FileReader() {
        //TODO
    }

    public String getText(String fileName) {
        StringBuilder text = new StringBuilder();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine());
                text.append("\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND FOR: " + fileName);
            e.printStackTrace();
            return null;
        }

        return text.toString();
    }

}
