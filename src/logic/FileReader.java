/** 
 *  Group:       1
 *  Name: 		 Thomas Chilton, Ramon Deniz, Antonio Gomez, Maximus Kieu
 *  Class:		 CSE360
 *  Section: 	 85141
 *  Assignment:  Final Project
 */

/**
 * This class opens and reads files for handling test cases, primarily FormatterTest.java and turns
 * the files into String types. 
 * 
 *  @author Ramon Deniz
 *  @see FormatterTest
 */

package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

    /**
     * Default constructor that does nothing.
     */
    public FileReader() {
        // TODO
    }

    /**
     * Reads a file and its contents and returns a String type that stores all of
     * the file's contents.
     */
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
