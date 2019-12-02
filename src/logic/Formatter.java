package logic;

public class Formatter {

    private String input;
    private int lineSize;
    private boolean indent, wrap, isSingleSpaced, isSingleColumn;
    private enum Justified {
        Left, Right, Center, Equal;
    }
    private Justified justType;
    private String output;

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
            if(splitDoc[i].length() <= 5)
            {
                if(splitDoc[i].charAt(0) == '-')
                {
                    linecheck = splitDoc[i];
                    Command check = new Command(linecheck);
                    output = commandHandler(check, output);
                }
                else
                {
                    output = output + splitDoc[i];
                }
            }
            else
            {
                output = output + splitDoc[i];
            }
        }
        return output;
    }

    private String commandHandler(Command foundCommand, String input)
    {
        String output = input
        Command check = foundCommand;
        switch (check.command) {
            case CommandType.CHARACTER: // needs to be implemented
                
                break;
            case CommandType.RIGHT:
                justType = Justified.Right;
                break;
            case CommandType.LEFT:
                justType = Justified.Left;
                break;
            case CommandType.CENTER:
                justType = Justified.Center;
                break;
            case CommandType.EQUAL:
                justType = Justified.Equal;
                break;
            case CommandType.WRAP: // needs to be implemented
                break;
            case CommandType.COLUMN: // needs to be implemented
                break;
            case CommandType.TITLE:   // needs to be implemented
                break;
            case CommandType.INDENT: // needs to be implemented
                break;
            case CommandType.BLANK: // needs to be implemented
                
                break;
            case CommandType.COlUMN: // needs to be implemented
                break;
            default:
                break;
        }
}

