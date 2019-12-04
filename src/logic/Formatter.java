/** 
 *  Group:       1
 *  Name: 		 Thomas Chilton, Ramon Deniz, Antonio Gomez, Maximus Kieu
 *  Class:		 CSE360
 *  Section: 	 85141
 *  Assignment:  Final Project
 */

package logic;

import javax.lang.model.util.ElementScanner6;
import java.lang.String;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The class Formatter takes an input from our GUI and creates the formatted output.
 * It works in conjunction with Command.java to parse commands found in input and
 * to change the format of the output based on those commands. 
 * 
 *  @author Thomas Chilton, Ramon Deniz
 *  @see Command
 *  @see TextPanel
 */
public class Formatter {

    private String input, errors;
    private int lineSize;
    private boolean wrap, isSingleSpaced, isSingleColumn;

    private enum Justified {
        Left, Right, Center, Equal;
    }

    private Justified justType;

    /**
     *  Our default constructor creates a format of left justified,
     *  80 characters per line, non-wrap, single space, and single column.
     */
    public Formatter() {
        input = "No Input Exists";
        errors = "";
        lineSize = 80;
        wrap = false;
        isSingleSpaced = true;
        isSingleColumn = true;
        justType = Justified.Left;
    }

    /**
     * This is the main entry method to our class. It takes our input
     * from our GUI and inserts it into an array for each inputed line.
     * From there we use Command.java to check the validity of perceived
     * commands, and our commandHandler method to execute them. If the line
     * of text is not a command we pass it to formatHandler or doubleColumnHandler
     * 
     * @param unParsed
     * @return output
     * @see Command
     */
    public String getOutput(String unParsed) {
        input = unParsed;
        String linecheck;
        String output = "";
        int i = 0;
        int j = 0;
        int arraySize;
        String[] splitDoc = new String[1000];

        // This while loop takes our inputed document and breaks it up line by line
        // into our splitDoc array.
        while (i < input.length() && j < 1000) 
        {
            splitDoc[j] = "";
            while (i < input.length() && input.charAt(i) != '\n') {
                splitDoc[j] = splitDoc[j] + input.charAt(i);
                ++i;
            }
            ++i;
            ++j;
            if (splitDoc[j - 1].isEmpty())
                --j;
        }
        arraySize = j;

        // This for loop goes through the document line by line and 
        // formats the text and handles commands using our various methods.
        for (i = 0; i < arraySize; ++i) {
            if (splitDoc[i].charAt(0) == '-') {
                linecheck = splitDoc[i];
                Command check = new Command(linecheck);
                output = commandHandler(check, output, splitDoc[i + 1]);
                if (check.commandTypeToString().equals("TITLE"))
                    i++;
            } else {
                if (isSingleColumn)
                    output = formatHandler(output, splitDoc[i]);
                else
                    output = doubleColumnHandler(output, splitDoc, i, arraySize);
            }
        }
        return output;
    }

    /**
     * The commandHandler method takes a command that has passed the initial
     * confirmation of our Command.java class and works on executing the command.
     * It uses the Command.java class to further check validity and parse the parameters
     * of the command, and after that it changes our format variables based on the
     * command.
     * 
     * @param foundCommand
     * @param input
     * @param next
     * @return output
     * @see Command
     */
    private String commandHandler(Command foundCommand, String input, String next) {
        String output = input;
        Command check = foundCommand;
        switch (check.commandTypeToString()) {
        // "TOGGLE" COMMANDS (affect all future lines) //
        case "CHARACTER":
            if (check.validCharactersPerLine())
                lineSize = Integer.parseInt(check.getParameter());
            else {
                output = formatHandler(output, check.getCommand());
                errors = errors + check.getErrorMessage() + "\n";
            }
            break;
        case "RIGHT":
            if (check.validRightJustify())
                justType = Justified.Right;
            else {
                output = formatHandler(output, check.getCommand());
                errors = errors + check.getErrorMessage() + "\n";
            }
            break;
        case "LEFT":
            if (check.validLeftJustify())
                justType = Justified.Left;
            else {
                output = formatHandler(output, check.getCommand());
                errors = errors + check.getErrorMessage() + "\n";
            }
            break;
        case "CENTER":
            if (check.validCenter())
                justType = Justified.Center;
            else {
                output = formatHandler(output, check.getCommand());
                errors = errors + check.getErrorMessage() + "\n";
            }
            break;
        case "EQUAL":
            if (check.validEqualSpace())
                justType = Justified.Equal;
            else {
                output = formatHandler(output, check.getCommand());
                errors = errors + check.getErrorMessage() + "\n";
            }
            break;
        case "WRAP":
            if (check.validWrap()) {
                System.out.println("Wrap command detected");
                if (check.getParameter().equals("+")) {
                    System.out.println("wrap set to true");
                    wrap = true;
                } else
                    wrap = false;
            } else {
                output = formatHandler(output, check.getCommand());
                errors = errors + check.getErrorMessage() + "\n";
            }
            break;
        case "SINGLE":
            if (check.validSingleSpace())
                isSingleSpaced = true;
            else {
                output = formatHandler(output, check.getCommand());
                errors = errors + check.getErrorMessage() + "\n";
            }
            break;
        case "DOUBLE":
            if (check.validDoubleSpace())
                isSingleSpaced = false;
            else {
                output = formatHandler(output, check.getCommand());
                errors = errors + check.getErrorMessage() + "\n";
            }
            break;
        case "COLUMN":
            if (check.validColumns()) {
                if (check.getParameter().equals("1"))
                    isSingleColumn = true;
                else {
                    isSingleColumn = false;
                }
            } else {
                output = formatHandler(output, check.getCommand());
                errors = errors + check.getErrorMessage() + "\n";
            }
            break;

        // "IMMEDIATE" COMMANDS (affect only the next line) //
        case "TITLE":
            if (check.validTitle()) {
                int margins = (lineSize - next.length()) / 2;
                if (margins < 0)
                    margins = 0;
                for (int i = 0; i < margins; ++i)
                    output = output + " ";
                output = output + next + "\n";
                for (int i = 0; i < margins; ++i)
                    output = output + " ";
                for (int i = 0; i < next.length(); ++i)
                    output = output + "-";
            } else {
                output = formatHandler(output, check.getCommand());
                errors = errors + check.getErrorMessage() + "\n";
            }
            break;
        case "INDENT":
            if (check.validIndents() && justType == Justified.Left) {
                int spaces = Integer.parseInt(check.getParameter());
                for (int i = 0; i < spaces; ++i)
                    output = output + " ";
            } else {
                output = formatHandler(output, check.getCommand());
                errors = errors + check.getErrorMessage() + "\n";
            }
            break;
        case "BLANK":
            if (check.validBlankLines()) {
                int blankLines = Integer.parseInt(check.getParameter());
                for (int i = 0; i < blankLines; ++i)
                    output = output + "\n";
            } else {
                output = formatHandler(output, check.getCommand());
                errors = errors + check.getErrorMessage() + "\n";
            }
            break;
        default:
            errors = errors + check.getErrorMessage() + "\n";
            break;
        }
        return output;
    }

    /**
     * Returns the number of newline characters in the given parameter value.
     * 
     * @param str the string being identified
     * @return count
     * @see doubleColumnHandler
     */
    private int getInstances(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++)
            if (str.charAt(i) == '\n')
                count++;
        return count;
    }

    /**
     * Our doubleColumnHandler class handles inputed text when
     * a double column format command has been executed.
     * 
     * @param output the output already formatted
     * @param doc the input text as an array split for each line
     * @param index the position in which double column was detected
     * @param arraySize the array size of doc
     * @return output
     */
    private String doubleColumnHandler(String output, String[] doc, int index, int arraySize) {
        /*
         * Basic runthrough of implementation of double columns: Parse the document and
         * see if the format is ever changed back to single column. Then calculate the
         * halfway point. Put the chunk between when double column was implemented in
         * one string Put the chunk between halfway and when single column was
         * implemented/ when the document ends in another string. Create output by
         * putting 35 chars from string one on the left and 35 chars from string two on
         * the right.
         */
        Formatter column1 = new Formatter();
        Formatter column2 = new Formatter();
        column1.isSingleSpaced = this.isSingleSpaced;
        column2.isSingleSpaced = this.isSingleSpaced;
        column1.lineSize = 35;
        column2.lineSize = 35;
        column1.wrap = true;
        column2.wrap = true;
        int last = index;
        StringBuilder builder = new StringBuilder();
        while (last < doc.length && doc[last] != null && !doc[last].contains("-a1")) {
            builder.append(doc[last]);
            last++;
        }
        String[] columnDoc = builder.toString().split("\\s+");

        // get first half of text and format into column 1
        int left = 0;
        int right = columnDoc.length - 1;

        String leftString = "";
        String rightString = "";
        while (left <= right) {
            int leftCount = getInstances(column1.getOutput(leftString));
            int rightCount = getInstances(column2.getOutput(rightString));
            if (leftCount == rightCount + (isSingleSpaced ? 1 : 2) || leftCount == rightCount)
                leftString += columnDoc[left++] + " ";
            else
                rightString = columnDoc[right--] + " " + rightString;
        }

        Scanner scanner1 = new Scanner(column1.getOutput(leftString));
        Scanner scanner2 = new Scanner(column2.getOutput(rightString));
        while (scanner1.hasNextLine() && scanner2.hasNextLine()) {
            output = output + scanner1.nextLine() + "          " + scanner2.nextLine();
            output = output + "\n";
        }
        if (scanner1.hasNextLine())
            output = output + scanner1.nextLine();

        return output;
    }

    /**
     * formatHandler handles adding things to output with current formatting settings.
     * It handles all cases except double column handler, which was given its own method
     * because of its complexity, and commands that occur instantly rather than toggle format
     * changes.
     * @param input1
     * @param input2
     * @return output
     * @see doubleColumnHandler
     */
    private String formatHandler(String input1, String input2) {
        String output = input1;

        // Handling special cases
        if (justType == Justified.Center) // Center justified
        {
            int margins = (lineSize - input2.length()) / 2;
            // if the text fits on one line
            if (margins >= 0) {
                for (int i = 0; i < margins; ++i)
                    output = output + " ";
                output = output + input2;
                output = output + "\n";
                if (isSingleSpaced == false)
                    output = output + "\n";
            // if the text doesn't fit on one line and wrap is enabled
            } else if (margins < 0 && wrap == true) {
                int i = 0;
                int k;
                //Here we split our input into word sections
                String[] split = input2.split("\\b");
                String test = "";
                String line[];


                while (i < split.length) {
                    k = 0;
                    test = split[i];
                    line = new String[split.length];
                    // Filling line with words from split that can fit on line

                    // This gets rid of any possible trailing spaces.
                    while (split[i] == "\\s+")
                        ++i;
                    test = split[i];
                    line[k] = split[i];
                    ++i;
                    ++k;

                    // Here we construct a line that fits in our character limit
                    // Using the split input
                    while ((i < split.length) && test.length() < lineSize) {
                        line[k] = split[i];
                        if (i < split.length - 1)
                            test = test + split[i + 1];
                        ++k;
                        ++i;
                    }
                    int numWords = k;
                    int stringLength = 0;


                    for (k = 0; k < numWords; ++k)
                        stringLength = stringLength + line[k].length();

                    margins = (lineSize - stringLength) / 2;

                    // Output spaces before our input line
                    for (k = 0; k < margins; ++k)
                        output = output + " ";
                    // Input line
                    for (k = 0; k < numWords; ++k)
                        output = output + line[k];
                    // Output spaces after input
                    for (k = 0; k < margins; ++k)
                        output = output + " ";

                    output = output + "\n";
                    if (isSingleSpaced == false) // Handles double spaced format
                        output = output + "\n";
                }
            } 
            // if the text doesn't fit on one line and wrap isn't enabled
            else if (wrap == false && margins < 0) {
                output = wrapHandler(output, input2);
            }
        } else if (justType == Justified.Equal) // Equally spaced
        {
            int i;
            int addedSpaces;
            String[] split;
            split = input2.split("\\s+");
            input2 = input2.replaceAll("\\s", "");
            int fit = lineSize - input2.length();
            
            // Handles when the input can fit on one line
            if (fit >= 0) {
                if (split.length != 1) {
                    int extraSpaces;
                    // Calculating added spaces
                    addedSpaces = (lineSize - input2.length()) / (split.length - 1);
                    extraSpaces = lineSize - (input2.length() + addedSpaces * (split.length - 1));
                    for (i = 0; i < split.length - 1; ++i) {
                        output = output + split[i];
                        for (int j = 0; j < addedSpaces; ++j) {
                            output = output + " ";
                        }
                        if (extraSpaces > 0) {
                            output = output + " ";
                            --extraSpaces;
                        }
                    }
                    output = output + split[i];
                } else // Case for if there is only one word on a line
                {
                    addedSpaces = (lineSize - input2.length()) / 2;
                    for (i = 0; i < addedSpaces; ++i)
                        output = output + " ";
                    output = output + input2;
                }

                output = output + "\n";
                if (isSingleSpaced == false)
                    output = output + "\n";
            // Handles when the input can't fit on one line and wrap
            // is turned on
            } else if (fit < 0 && wrap == true) {
                i = 0;
                int k = 0;
                int l;
                String line[];
                String test = "";

                while (i < split.length) {
                    k = 0;
                    test = split[i];
                    line = new String[split.length];
                    // Filling line with words from split that can fit on line
                    while ((i < split.length) && ((k) < (lineSize - test.length()))) {
                        line[k] = split[i];
                        if (i < split.length - 1)
                            test = test + split[i + 1];
                        ++k;
                        ++i;
                    }
                    int numWords = k;
                    
                    // If there is more than one word on a line
                    if (numWords != 1) {
                        int j;
                        int extraSpaces;
                        // Calculating spaces to add
                        int stringLength = 0;
                        for (l = 0; l < numWords; ++l) {
                            stringLength = stringLength + line[l].length();
                        }
                        addedSpaces = (lineSize - stringLength) / (numWords - 1);
                        if (addedSpaces < 1) // shouldn't occur but just in case
                            addedSpaces = 1;
                        extraSpaces = lineSize - (addedSpaces * (numWords - 1) + stringLength);
                        // Output
                        j = 0;
                        while (j < numWords - 1) {
                            output = output + line[j];
                            for (l = 0; l < addedSpaces; ++l) {
                                output = output + " ";
                            }
                            // handling extra room
                            if (extraSpaces > 0) {
                                output = output + " ";
                                --extraSpaces;
                            }
                            ++j;
                        }
                        output = output + line[j];

                        // Add a newline character at the end of the line
                        output = output + "\n";
                        if (isSingleSpaced == false)
                            output = output + "\n";
                    } else // Case for if there is only one word on the line
                    {
                        // It's treated identically to center justification
                        addedSpaces = ((lineSize - line[0].length()) / 2);
                        for (int j = 0; j < addedSpaces; ++j)
                            output = output + " ";
                        output = output + line[0];

                        output = output + "\n";
                        if (isSingleSpaced == false)
                            output = output + "\n";
                    }
                }

            } else if (fit > 0 && wrap == false)
                output = wrapHandler(output, input2);

        } else if (justType == Justified.Right) // Right justified
        {
            int margin = lineSize - input2.length();

            if (margin >= 0) {
                for (int i = 0; i < margin; ++i)
                    output = output + " ";
                output = output + input2;
                output = output + "\n";
                if (isSingleSpaced == false)
                    output = output + "\n";
            } 
            
            else if (margin < 0 && wrap == true) {
                int i = 0;
                int k;
                String[] split = input2.split("\\s+");
                String test = "";
                String line[];
                while (i < split.length) {
                    k = 0;
                    test = split[i];
                    line = new String[split.length];
                    
                    // Filling line with words from split that can fit on line
                    while ((i < split.length) && ((k) < (lineSize - test.length()))) {
                        line[k] = split[i];
                        if (i < split.length - 1)
                            test = test + split[i + 1];
                        ++k;
                        ++i;
                    }


                    int numWords = k;
                    int stringLength = 0;
                    for (k = 0; k < numWords; ++k)
                        stringLength = stringLength + line[k].length();

                    margin = lineSize - stringLength - (numWords - 1);
                    
                    for (k = 0; k < margin; ++k)
                        output = output + " ";
                    
                    for (k = 0; k < numWords - 1; ++k) {
                        output = output + line[k];
                        output = output + " ";
                    }
                    output = output + line[k];

                    output = output + "\n";
                    if (isSingleSpaced == false) // Handles double space
                        output = output + "\n";
                }
            } else if (margin > 0 && wrap == false)
                output = wrapHandler(output, input2);
        } else // Left justified
        {
            int fit = (lineSize - input2.length());
            if (fit >= 0) { // If the input fits on one line
                output = output + input2;
                output = output + "\n";
                if (isSingleSpaced == false)
                    output = output + "\n";
            } // If the input doesn't fit on one line and wrap is on
            else if (fit < 0 && wrap == true) {
                int i = 0;
                int j = 0;
                int k;
                int lineEnd;
                String[] split = input2.split("\\s+");
                String test = "";
                String line[];

                while (i < split.length) {
                    k = 0;
                    test = split[i];
                    line = new String[split.length];
                    // Getting rid of trailing spaces
                    while (split[i] == "\\s+")
                        ++i;
                    test = split[i];
                    line[k] = split[i];
                    ++i;
                    ++k;

                    // Creating a line that can fit in character limit
                    while ((i < split.length) && ((k) < (lineSize - test.length()))) {
                        line[k] = split[i];
                        if (i < split.length - 1)
                            test = test + split[i + 1];
                        ++k;
                        ++i;
                    }

                    int numWords = k;
                    int stringLength = 0;

                    // Calculating length of the line
                    for (k = 0; k < numWords; ++k)
                        stringLength = stringLength + line[k].length();

                    fit = lineSize - stringLength;

                    // Output
                    for (k = 0; k < numWords - 1; ++k) {
                        output = output + line[k];
                        output = output + " ";
                    }
                    output = output + line[k];

                    output = output + "\n";
                    if (isSingleSpaced == false) // Double space handler
                        output = output + "\n";
                }
            } else if (fit < 0 && wrap == false)
                output = wrapHandler(output, input2);
        }
        return output;

    }

    /**
     * wrapHandler handles cases in formatHandler when wrap is turned
     * off. It is the same regardless of justification, so it is in its
     * own method for reusability.
     * 
     * @param input1
     * @param input2
     * @return output
     */
    private String wrapHandler(String input1, String input2) {
        String output = input1;
        output = output + input2;
        output = output + "\n";
        if (isSingleSpaced == false)
            output = output + "\n";
        return output;
    }

    /**
     * This method returns the recorded errors for the purpose
     * of display in the GUI.
     * 
     * @return errors
     */
    public String getErrors() {
        return errors;
    }
}
