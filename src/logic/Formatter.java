package logic;

import javax.lang.model.util.ElementScanner6;
import java.lang.String;
import java.util.Arrays;
import java.util.Scanner;

// Currently not being used
//import javax.lang.model.util.ElementScanner6;

public class Formatter {

    private String input, errors;
    private int lineSize;
    private boolean wrap, isSingleSpaced, isSingleColumn;
    private enum Justified {
        Left, Right, Center, Equal;
    }
    private Justified justType;

    public Formatter() {
        input = "No Input Exists";
        errors = "";
        lineSize = 80;
        wrap = false;
        isSingleSpaced = true;
        isSingleColumn = true;
        justType = Justified.Left;
    }

    public String getOutput(String unParsed) {
        input = unParsed;
        String linecheck;
        String output = "";
        int i = 0;
        int j = 0;
        int arraySize;
        String[] splitDoc = new String[1000];
        while(i < input.length() && j < 1000)
        {
            splitDoc[j] = "";
            while(i < input.length() && input.charAt(i) != '\n')
            {
                splitDoc[j] = splitDoc[j] + input.charAt(i);
                ++i;
            }
            ++i;
            ++j;
            if (splitDoc[j-1].isEmpty())
                --j;
        }  
        arraySize = j;
        for(i = 0; i < arraySize; ++i)
        {
            if(splitDoc[i].charAt(0) == '-')
            {
                linecheck = splitDoc[i];
                Command check = new Command(linecheck);
                output = commandHandler(check, output, splitDoc[i+1]);
                if(check.commandTypeToString().equals("TITLE"))
                    i++;
            }
            else
            {
                if(isSingleColumn)
                    output = formatHandler(output, splitDoc[i]);
                else
                    output = doubleColumnHandler(output, splitDoc, i, arraySize);
            }
        }
        return output;
    }

    private String commandHandler(Command foundCommand, String input, String next)
    {
        String output = input;
        Command check = foundCommand;
        switch (check.commandTypeToString()) {
            // "TOGGLE" COMMANDS (affect all future lines) //
            case "CHARACTER": 
                if(check.validCharactersPerLine())
                    lineSize = Integer.parseInt(check.getParameter());
                else
                {
                    output = formatHandler(output, check.getCommand());
                    errors = errors + check.getErrorMessage() + "\n";
                }
                break;
            case "RIGHT":
                if (check.validRightJustify())
                    justType = Justified.Right;
                else
                {
                    output = formatHandler(output, check.getCommand());
                    errors = errors + check.getErrorMessage() + "\n";
                }
                break;
            case "LEFT":
                if (check.validLeftJustify())
                    justType = Justified.Left;
                else
                {
                    output = formatHandler(output, check.getCommand());
                    errors = errors + check.getErrorMessage() + "\n";
                }
                break;
            case "CENTER":
                if (check.validCenter())
                    justType = Justified.Center;
                else
                {
                    output = formatHandler(output, check.getCommand());
                    errors = errors + check.getErrorMessage() + "\n";
                }
                break;
            case "EQUAL":
                if (check.validEqualSpace())
                    justType = Justified.Equal;
                else
                {
                    output = formatHandler(output, check.getCommand());
                    errors = errors + check.getErrorMessage() + "\n";
                }
                break;
            case "WRAP":
                if(check.validWrap())
                {
                    System.out.println("Wrap command detected");
                    if (check.getParameter().equals("+"))
                    {
                        System.out.println("wrap set to true");
                        wrap = true;
                    }
                    else
                        wrap = false;
                }
                else
                {
                    output = formatHandler(output, check.getCommand());
                    errors = errors + check.getErrorMessage() + "\n";
                }
                break;
            case "SINGLE":
                if(check.validSingleSpace())
                    isSingleSpaced = true;
                else
                {
                    output = formatHandler(output, check.getCommand());
                    errors = errors + check.getErrorMessage() + "\n";
                }
                break;
            case "DOUBLE": 
                if(check.validDoubleSpace())
                    isSingleSpaced = false;
                else
                {
                    output = formatHandler(output, check.getCommand());
                    errors = errors + check.getErrorMessage() + "\n";
                }
                break;
            case "COLUMN":
                if(check.validColumns())
                {
                    if(check.getParameter().equals("1"))
                        isSingleColumn = true;
                    else
                    {
                        isSingleColumn = false;
                    }
                }
                else
                {
                    output = formatHandler(output, check.getCommand());
                    errors = errors + check.getErrorMessage() + "\n";
                }
                break;

            // "IMMEDIATE" COMMANDS (affect only the next line) //
            case "TITLE":   
                if(check.validTitle()) 
                {
                    int margins = (lineSize - next.length()) / 2;
                    if (margins < 0)
                        margins = 0;
                    for(int i = 0; i < margins; ++i)
                        output = output + " ";
                    output = output + next + "\n";
                    for(int i = 0; i < margins; ++i)
                        output = output + " ";
                    for(int i = 0; i < next.length(); ++i)
                        output = output + "-";
                }
                else
                {
                    output = formatHandler(output, check.getCommand());
                    errors = errors + check.getErrorMessage() + "\n";
                }
                break;
            case "INDENT": 
                if(check.validIndents() && justType == Justified.Left)
                {
                    int spaces = Integer.parseInt(check.getParameter());
                    for(int i = 0; i < spaces; ++i)
                        output = output + " ";
                }
                else
                {
                    output = formatHandler(output, check.getCommand());
                    errors = errors + check.getErrorMessage() + "\n";
                }    
                break;
            case "BLANK": 
                if(check.validBlankLines())
                {
                    int blankLines = Integer.parseInt(check.getParameter());
                    for(int i = 0; i < blankLines; ++i)
                        output = output + "\n";
                }
                else
                {
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

    private int getInstances(String str){
        int count = 0;
        for(int i = 0; i < str.length(); i++)
            if(str.charAt(i) == '\n')
                count++;
        return count;
    }

    // Gave double columns its own method because of its need for unique
    // parameters and its complexity
    private String doubleColumnHandler(String output, String[] doc, int index, int arraySize)
    {
        //TODO
        /*
            Basic runthrough of implementation of double columns:
                Parse the document and see if the format is ever changed 
                back to single column. Then calculate the halfway point.
                Put the chunk between when double column was implemented in one string
                Put the chunk between halfway and when single column was implemented/
                when the document ends in another string. Create output by putting 35
                chars from string one on the left and 35 chars from string two on the
                right.
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
        while(last < doc.length && doc[last] != null && !doc[last].contains("-a1")){
            builder.append(doc[last]);
            last++;
        }
        String[] columnDoc = builder.toString().split("\\s+");

        // get first half of text and format into column 1
        int left = 0;
        int right = columnDoc.length - 1;

        String leftString = "";
        String rightString = "";
        while(left <= right){
            int leftCount = getInstances(column1.getOutput(leftString));
            int rightCount = getInstances(column2.getOutput(rightString));
            if(leftCount == rightCount + (isSingleSpaced? 1 : 2) || leftCount == rightCount)
                leftString += columnDoc[left++] + " ";
            else
                rightString = columnDoc[right--] + " " + rightString;
        }

        Scanner scanner1 = new Scanner(column1.getOutput(leftString));
        Scanner scanner2 = new Scanner(column2.getOutput(rightString));
        while(scanner1.hasNextLine() && scanner2.hasNextLine()){
            output = output + scanner1.nextLine() + "          " + scanner2.nextLine();
            output = output + "\n";
        }
        if(scanner1.hasNextLine())
            output = output + scanner1.nextLine();

        return output;
    }

    // Handles adding things to output with current formatting settings
    // TODO:
    //      Fix end of line cutoff
    private String formatHandler(String input1, String input2)
    {
        String output = input1;

        // Handling special cases
        if (justType == Justified.Center) // Center justified
        {
            int margins = (lineSize - input2.length()) / 2;
            if (margins >= 0)
            {
                for(int i = 0; i < margins; ++i)
                    output = output + " ";
                output = output + input2;
                output = output + "\n";
                if (isSingleSpaced == false)
                    output = output + "\n";
            }
            else if (margins < 0 && wrap == true)
            {
                int i = 0;
                int j = 0;
                int k;
                int fit = margins;
                int lineEnd;
                String[] split = input2.split("\\b");
                String test = "";
                String line[];
                while (i < split.length) 
                {
                    k = 0;
                    test = split[i];
                    line = new String[split.length];
                    // Filling line with words from split that can fit on line
                    while (split[i] == "\\s+")
                        ++i;
                    test = split[i];
                    line[k] = split[i];
                    ++i;
                    ++k;
                    while((i < split.length) && test.length() < lineSize)
                    {
                        line[k] = split[i];
                        if(i < split.length-1)
                            test = test + split[i + 1];
                        ++k;
                        ++i;
                    }
                    int numWords = k;
                    int stringLength = 0;
                    for(k = 0; k < numWords; ++k)
                        stringLength = stringLength + line[k].length();

                    margins = (lineSize - stringLength)/2;

                    fit = lineSize - stringLength;

                    for (k = 0; k < margins; ++k)
                        output = output + " ";
                    for (k = 0; k < numWords; ++k)
                        output = output + line[k];
                    for(k = 0; k < margins; ++k)
                        output = output + " ";

                    output = output + "\n";
                    if (isSingleSpaced == false)
                        output = output + "\n";
                }
            }
            else if (wrap == false && margins < 0)
            {
                output = wrapHandler(output, input2);
            }
        }
        else if (justType == Justified.Equal) // Equally spaced
        {
            int i;
            int addedSpaces;
            String[] split;
            split = input2.split("\\s+");
            input2 = input2.replaceAll("\\s", "");
            int fit = lineSize - input2.length();
            if (fit >= 0)
            {
                if (split.length != 1)
                {
                    int extraSpaces;
                    // Calculating added spaces
                    addedSpaces = (lineSize - input2.length())/(split.length-1);
                    extraSpaces = lineSize - (input2.length() + addedSpaces*(split.length-1));
                    for(i = 0; i < split.length-1; ++i)
                    {
                        output = output + split[i];
                        for(int j = 0; j < addedSpaces; ++j)
                        {
                            output = output + " ";
                        }
                        if (extraSpaces > 0)
                        {
                            output = output + " ";
                            --extraSpaces;
                        }
                    }
                    output = output + split[i];
                }
                else // Case for if there is only one word on a line
                {
                    addedSpaces = (lineSize - input2.length()) / 2;
                    for(i = 0; i < addedSpaces; ++i)
                        output = output + " ";
                    output = output + input2;
                }

                output = output + "\n";
                if (isSingleSpaced == false)
                    output = output + "\n";
            }
            else if (fit < 0 && wrap == true)   
            { 
                i = 0;
                int k = 0;
                int l;
                String line[];
                String test = "";
                while(i < split.length)
                {
                    k = 0;
                    test = split[i];
                    line = new String[split.length];
                    // Filling line with words from split that can fit on line
                    while((i < split.length) && ((k)  < (lineSize - test.length())))
                    {
                        line[k] = split[i];
                        if(i < split.length-1)
                            test = test + split[i + 1];
                        ++k;
                        ++i;
                    }
                    int numWords = k;
                    if(numWords != 1)
                    {
                        int j;
                        int extraSpaces;
                        // Calculating spaces to add
                        int stringLength = 0;
                        for(l = 0; l < numWords; ++l)
                        {
                            stringLength = stringLength + line[l].length();
                        }
                        addedSpaces = (lineSize - stringLength)/(numWords-1);
                        if(addedSpaces < 1) // shouldn't occur
                            addedSpaces = 1;
                        extraSpaces = lineSize - (addedSpaces*(numWords-1) + stringLength);
                        // Output
                        j = 0;
                        while(j < numWords-1)
                        {
                            output = output + line[j];
                            for(l = 0; l < addedSpaces; ++l)
                            {
                                output = output + " ";
                            }
                            // handling extra room
                            if (extraSpaces > 0)
                            {
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
                    }
                    else // Case for if there is only one word on the line
                    {
                        // It's treated identically to center justification
                        addedSpaces = ((lineSize - line[0].length())/2);
                        for (int j = 0; j < addedSpaces; ++j)
                            output = output + " ";
                        output = output + line[0];

                        output = output + "\n";
                        if (isSingleSpaced == false)
                            output = output + "\n";
                    }
                }
                
            }
            else if (fit > 0 && wrap == false)
                output = wrapHandler(output, input2);
            
        }
        else if (justType == Justified.Right)   // Right justified
        {
            int margin = lineSize - input2.length();
            if (margin >= 0)
            {
                for(int i = 0; i < margin; ++i)
                    output = output + " ";
                output = output + input2;
                output = output + "\n";
                if (isSingleSpaced == false)
                    output = output + "\n";
            }
            else if (margin < 0 && wrap == true)
            {
                int i = 0;
                int k;
                String[] split = input2.split("\\s+");
                String test = "";
                String line[];
                while (i < split.length) 
                {
                    k = 0;
                    test = split[i];
                    line = new String[split.length];
                    // Filling line with words from split that can fit on line
                    while((i < split.length) && ((k)  < (lineSize - test.length())))
                    {
                        line[k] = split[i];
                        if(i < split.length-1)
                            test = test + split[i + 1];
                        ++k;
                        ++i;
                    }
                    int numWords = k;
                    int stringLength = 0;
                    for(k = 0; k < numWords; ++k)
                        stringLength = stringLength + line[k].length();

                    margin = lineSize - stringLength - (numWords-1);
                    for(k = 0; k < margin; ++k)
                        output = output + " ";
                    for (k = 0; k < numWords-1; ++k)
                    {
                        output = output + line[k];
                        output = output + " ";
                    }
                    output = output + line[k];

                    output = output + "\n";
                    if (isSingleSpaced == false)
                        output = output + "\n";
                }
            }
            else if (margin > 0 && wrap == false)
                output = wrapHandler(output, input2);
        }
        else    // Left justified
        {
            int fit = (lineSize - input2.length());
            if (fit >= 0)
            {
                output = output + input2;
                output = output + "\n";
                if (isSingleSpaced == false)
                    output = output + "\n";
            }
            else if (fit < 0 && wrap == true) 
            {
                int i = 0;
                int j = 0;
                int k;
                int lineEnd;
                String[] split = input2.split("\\s+");
                String test = "";
                String line[];
                while (i < split.length) 
                {
                    k = 0;
                    test = split[i];
                    line = new String[split.length];
                    // Filling line with words from split that can fit on line
                    while (split[i] == "\\s+")
                        ++i;
                    test = split[i];
                    line[k] = split[i];
                    ++i;
                    ++k;
                    while((i < split.length) && ((k)  < (lineSize - test.length())))
                    {
                        line[k] = split[i];
                        if(i < split.length-1)
                            test = test + split[i + 1];
                        ++k;
                        ++i;
                    }
                    int numWords = k;
                    int stringLength = 0;
                    for(k = 0; k < numWords; ++k)
                        stringLength = stringLength + line[k].length();
                    fit = lineSize - stringLength;
                    for (k = 0; k < numWords-1; ++k)
                    {
                        output = output + line[k];
                        output = output + " ";
                    }
                    output = output + line[k];

                    output = output + "\n";
                    if (isSingleSpaced == false)
                        output = output + "\n";
                }
            }
            else if (fit < 0 && wrap == false)
                output = wrapHandler(output, input2);
        }
        return output;

    }

    private String wrapHandler (String input1, String input2)
    {
        String output = input1;
        output = output + input2;
        output = output + "\n";
        if (isSingleSpaced == false)
            output = output + "\n";
        return output;
    }


    public String getErrors()
    {
        return errors;
    }
}


