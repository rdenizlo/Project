package logic;

import javax.lang.model.util.ElementScanner6;

public class Formatter {

    private String input;
    private int lineSize;
    private boolean wrap, isSingleSpaced, isSingleColumn;
    private enum Justified {
        Left, Right, Center, Equal;
    }
    private Justified justType;

    public Formatter() {
        input = "No Input Exists";
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
        String[] splitDoc = new String[1000];
        while(i < input.length() && j < 1000)
        {
            splitDoc[j] = "";
            while(input.charAt(i) != '\n' && i < input.length())
            {
                splitDoc[j] = splitDoc[j] + input.charAt(i);
                ++i;
            }
            ++j;
        }  

        for(i = 0; i < splitDoc.length; ++i)
        {

            if(splitDoc[i].charAt(0) == '-')
            {
                linecheck = splitDoc[i];
                Command check = new Command(linecheck);
                output = commandHandler(check, output, splitDoc[i+1]);
            }
            else
            {
                output = formatHandler(output, splitDoc[i]);
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
                    output = formatHandler(output, check.getCommand());
                break;
            case "RIGHT":
                if (check.validRightJustify())
                    justType = Justified.Right;
                else
                    output = formatHandler(output, check.getCommand());
                break;
            case "LEFT":
                if (check.validLeftJustify())
                    justType = Justified.Left;
                else
                    output = formatHandler(output, check.getCommand());
                break;
            case "CENTER":
                if (check.validCenter())
                    justType = Justified.Center;
                else
                    output = formatHandler(output, check.getCommand());
                break;
            case "EQUAL":
                if (check.validEqualSpace())
                    justType = Justified.Equal;
                else
                    output = formatHandler(output, check.getCommand());
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
                    output = formatHandler(output, check.getCommand());
                break;
            case "SINGLE":
                if(check.validSingleSpace())
                    isSingleSpaced = true;
                else
                    output = formatHandler(output, check.getCommand());
                break;
            case "DOUBLE": 
                if(check.validDoubleSpace())
                    isSingleSpaced = false;
                else
                    output = formatHandler(output, check.getCommand());
                break;
            case "COLUMN":
                if(check.validColumns())
                {
                    if(check.getParameter().equals("1"))
                        isSingleColumn = true;
                    else
                    {
                        isSingleColumn = false;
                        lineSize = 80;
                    }
                }
                else
                    output = formatHandler(output, check.getCommand());
                break;

            // "IMMEDIATE" COMMANDS (affect only the next line) //
            case "TITLE":   
                if(check.validTitle()) 
                {
                    int margins = (lineSize - next.length()) / 2;
                    if (margins < 0)
                        margins = 0;
                    output = output + margins + next + "\n";
                    for(int i = 0; i < lineSize; ++i)
                        output = output + "-";
                }
                else
                {
                    output = formatHandler(output, check.getCommand());
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
                    output = formatHandler(output, check.getCommand());
                break;
            case "BLANK": 
                if(check.validBlankLines())
                {
                    int blankLines = Integer.parseInt(check.getParameter());
                    for(int i = 0; i < blankLines; ++i)
                        output = output + "/n";
                }
                else
                    output = formatHandler(output, check.getCommand());
                break;
            default:
                break;
        }
        return output;
    }

    // Handles adding things to output with current formatting settings
    // To Implement:
    //      Two columns
    //      Equally spaced (when fit < 0)
    //      Wrap
    private String formatHandler(String input1, String input2)
    {
        String output = input1;
        String thingToAdd = input2;

        // Handling special cases
        /*
            How i'll need to implement two columns:
                Parse the document and see if the format is ever changed 
                back to single column. Then calculate the halfway point.
                Put the chunk between when double column was implemented in one string
                Put the chunk between halfway and when single column was implemented/
                when the document ends in another string. Create output by putting 35
                chars from string one on the left and 35 chars from string two on the
                right.
        */
        if (isSingleColumn != false) // Two columns (not implemented)
        {
            int fit;
            int i = 0;
            int j = 1;
            String leftString, rightString;
            while(fit < 0)
            {

            }
            if (fit >= 0) // This is going to take some work
            {

            }
        }
        else if (justType == Justified.Center) // Center justified
        {
            int margins = (lineSize - input2.length()) / 2;
            if (margins >= 0)
            {
                for(int i = 0; i < margins; ++i)
                    output = output + " ";
                output = output + input2;
                if (isSingleSpaced == false)
                    output = output + "\n";
            }
            else
            {
                int i = 0;
                int j = 0;
                while(margins < 0)
                {
                    margins = (lineSize - input2.substring(lineSize*j).length()) / 2;
                    ++j;
                    output = output + input2.substring(i, lineSize*j);
                    i = lineSize * j;
                    if (isSingleSpaced == false)
                        output = output + "\n";
                }
            }
        }
        else if (justType == Justified.Equal) // Equally spaced
        {
            int fit = lineSize - input2.length();
            int spaceCount;
            if (fit >= 0)
            {
                spaceCount = 0;
                for (int i = 0; i < input2.length(); ++i)
                {
                    if (input2.charAt(i) == ' ')
                        ++spaceCount;
                }
                int addedSpaces = (lineSize - input2.length())/spaceCount;
                String spaces = " ";
                for (int i = 0; i < addedSpaces; ++i)
                    spaces = spaces + " ";
                input2.replaceAll(" ", spaces);
                output = output + input2;
                if (isSingleSpaced == false)
                    output = output + "\n";
            }
            else    // needs to be implemented
            {
                int i;
                int j;
                while (fit < 0)
                {
                    spaceCount = 0;
                    
                    if (isSingleSpaced == false)
                        output = output + "\n";
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
                if (isSingleSpaced == false)
                    output = output + "\n";
            }
            else
            {
                int i = 0;
                int j = 0;
                while (margin < 0)
                {
                    margin = lineSize - input2.substring(lineSize*j).length();
                    ++j;
                    for(int k = 0; k < margin; ++k)
                        output = output + " ";
                    output = output + input2.substring(i, lineSize*j);
                    i = lineSize*j;
                    if (isSingleSpaced == false)
                        output = output + "\n";
                }
            }
        }
        else    // Left justified, single column
        {
            int fit = (lineSize - input2.length());
            if (fit >= 0)
            {
                output = output + input2;
                if (isSingleSpaced == false)
                    output = output + "\n";
            }
            else
            {
                int i = 0;
                int j = 0;
                while (fit < 0)
                {
                    fit = lineSize - input2.substring(lineSize*j).length();
                    ++j;
                    output = output + input2.substring(i, lineSize*j);
                    i = lineSize*j;
                    if (isSingleSpaced == false)
                        output = output + "\n";
                } 
            }
        }
        return output;
    }
}


