/** 
 *  Group:       1
 *  Name: 		 Thomas Chilton, Ramon Deniz, Antonio Gomez, Maximus Kieu
 *  Class:		 CSE360
 *  Section: 	 85141
 *  Assignment:  Final Project
 */
 package graphics;

import javax.swing.*;
import java.util.Scanner;

/**
 * Our OpenFile class handles when
 * files are opened.
 * 
 * @author: Antonio Gomez
 */
public class OpenFile {

    //Declare Variable
    JFileChooser filechooser = new JFileChooser();
    StringBuilder sb = new StringBuilder();

    /**
     * PickMe handles file exceptions
     * 
     * @throws Exception
     */
    public void PickMe() throws Exception {

        if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            //get the file
            java.io.File file = filechooser.getSelectedFile();

            //create a scanner for the file
            Scanner input = new Scanner(file);

            //read text from file
            while (input.hasNext()) {

                sb.append(input.nextLine());
                sb.append("\n");

            }

            input.close();

        } else {

            sb.append("No file was selected");

        }

    }

}
