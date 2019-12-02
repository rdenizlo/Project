package logic;

import javax.lang.model.util.ElementScanner6;

public class Formatter {

    private String input, output;
    private int lineSize;
    private boolean indent, wrap, isSingleSpaced, isSingleColumn;
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

    // Needs to be implemented
    // Handles adding things to output with current formatting settings
    private String formatHandler(String input1, String input2)
    {
        String output = input1;
        String thingToAdd = input2;

        // Handling special cases
        if (isSingleColumn != false)
        {
            int margins = (lineSize - input2.length()) / 2;
            int i = 0;
            int j = 1;
            while(margins < 0)
            {

            }
            if (margins >= 0) // This is going to take some work
            {

            }
        }
        else if (justType == Justified.Center)
        {
            int margins = (lineSize - input2.length()) / 2;
            int i = 0;
            int j = 1;
            while(margins < 0)
            {
                output = output + input2.substring(i, lineSize*j);
                i = lineSize * j;
                margins = (lineSize - input2.substring(lineSize*j).length()) / 2;
                ++j;
                if (isSingleSpaced == false)
                    output = output + "\n";
            }
            if (margins >= 0)
            {
                for(i = 0; i < margins; ++i)
                    output = output + " ";
                output = output + input2;
                if (isSingleSpaced == false)
                    output = output + "\n";
            }
        }
        else if (justType == Justified.Equal)
        {
            if (isSingleSpaced == false)
                output = output + "\n";
        }
        return output;
    }
}


