package graphics;

import javax.swing.*;
import java.util.Scanner;

public class OpenFile {

    //Declare Variable
    JFileChooser filechooser = new JFileChooser();
    StringBuilder sb = new StringBuilder();

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
