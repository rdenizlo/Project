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


    public String getOutput(String preParsed) {
        input = preParsed;
        String linecheck;
        String output = "";
        int i = 0;
        int j = 0;
        String[] splitDoc = new String[50];
        while(i < input.length())
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
                output = commandHandler(check, output);
            }
            else
            {
                output = formatHandler(output, splitDoc[i]);
            }
        }
        return output;
    }

    private String commandHandler(Command foundCommand, String input)
    {
        String output = input;
        Command check = foundCommand;
        switch (check.commandTypeToString()) {
            // "TOGGLE" COMMANDS
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
                        isSingleColumn = false;
                }
                else
                    output = formatHandler(output, check.getCommand());
                break;

            // "IMMEDIATE" COMMANDS
            case "TITLE":   // needs to be implemented
                if(check.validTitle())
                {

                }
                else
                {
                    output = formatHandler(output, check.getCommand());
                }
                break;
            case "INDENT": // needs to be implemented
                if(check.validIndents())
                {

                }
                else
                    output = formatHandler(output, check.getCommand());
                break;
            case "BLANK": // needs to be implemented
                if(check.validBlankLines())
                {

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
    private String formatHandler(String input1, String input2)
    {
        String output = input1;
        String thingToAdd = input2;
        return output;
    }
}


