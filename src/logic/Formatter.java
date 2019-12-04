package logic;

import javax.lang.model.util.ElementScanner6;
import java.lang.String;
import java.util.Arrays;

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
                    if (check.getParameter() == "+")
                        wrap = true;
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

    // Gave double columns its own method because of its need for unique
    // parameters and its complexity
    private String doubleColumnHandler(String output, String[] doc, int index, int arraySize)
    {
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
        String leftString = "";
        String rightString = "";
        String lineCheck;

        boolean commandFound = false;
        int i = index;
        int a = 0;
        int b = 34;
        int j;
        // We need to go through the document and check for -a1 commands
        // If we find one we will use that line index.
        while (i < arraySize && commandFound == false)
        {
            if(doc[i].charAt(0) == '-')
            {
                lineCheck = doc[i];
                Command check = new Command(lineCheck);
                output = commandHandler(check, output, doc[i+1]);
                if (check.validColumns())
                    if (check.getParameter().equals("1"))
                        commandFound = true;
            }
            ++i;
        }
        int halfway = (i - index)/2;
        // Handling leftString
        for (j = 0; j < halfway; ++j)
        {
            leftString = leftString + doc[j];
        }
        // Handling rightString
        for (j = halfway; j < i; ++j)
        {
            rightString = rightString + doc[j];
        }
        j = 0;
        while (b < rightString.length() && b < leftString.length())
        {
            output = output + leftString.substring(a, b) + "          "
                + rightString.substring(a, b);
            a = a + 35;
            b = b + 35;
            if (isSingleSpaced == false)
                output = output + "\n";
        }
        isSingleColumn = true;
        return output;
    }

    // Handles adding things to output with current formatting settings
    // TODO:
    //      Wrap
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
            else
            {
                int i = 0;
                int j = 0;
                int fit = margins;
                int lineEnd;
                while(fit < 0) 
                {
                    lineEnd = lineSize*j;
                    if (lineEnd > input2.length())
                        lineEnd = input2.length();
                    fit = lineSize - input2.substring(lineEnd).length();
                    margins = (lineSize - input2.substring(lineEnd).length()) / 2;
                    ++j;
                    lineEnd = lineSize*j;
                    if (lineEnd > input2.length())
                        lineEnd = input2.length();
                    for (int k = 0; k < margins; ++k)
                        output = output + " ";
                    output = output + input2.substring(i, lineEnd);
                    for(int k = 0; k < margins; ++k)
                        output = output + " ";
                    i = lineEnd;

                    output = output + "\n";
                    if (isSingleSpaced == false)
                        output = output + "\n";
                }
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
            else    
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
            else
            {
                int i = 0;
                int j = 0;
                int lineEnd;
                int fit = margin;
                while (fit < 0) 
                {
                    lineEnd = lineSize*j;
                    if (lineEnd > input2.length())
                        lineEnd = input2.length();
                    fit = lineSize - input2.substring(lineEnd).length();
                    ++j;
                    lineEnd = lineSize*j;
                    if (lineEnd > input2.length())
                        lineEnd = input2.length();

                    margin = lineSize - input2.substring(i, lineEnd).length();
                    for(int k = 0; k < margin; ++k)
                        output = output + " ";

                    output = output + input2.substring(i, lineEnd);
                    i = lineEnd;

                    output = output + "\n";
                    if (isSingleSpaced == false)
                        output = output + "\n";
                }
            }
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
            else
            {
                int i = 0;
                int j = 0;
                int lineEnd;
                while (fit < 0)
                {
                    lineEnd = lineSize*j;
                    if (lineEnd > input2.length())
                        lineEnd = input2.length();
                    fit = lineSize - input2.substring(lineEnd).length();
                    ++j;
                    lineEnd = lineSize*j;
                    if (lineEnd > input2.length())
                        lineEnd = input2.length();
                    output = output + input2.substring(i, lineEnd);
                    i = lineEnd;
                    output = output + "\n";
                    if (isSingleSpaced == false)
                        output = output + "\n";
                } 
            }
        }
        return output;
    }

    public String getErrors()
    {
        return errors;
    }
}


