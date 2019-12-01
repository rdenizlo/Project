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
            if(splitDoc[i].length() <= 3)
            {
                if(splitDoc[i].charAt(0) = '-')
                {
                    linecheck = splitDoc[i];
                    Command check = new Command(linecheck);
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

    private commandHandler(Command foundCommand)
    {
        Command check = foundCommand;
        switch (check) {
            case 'CommandType.CHARACTER':
                
                break;
            case 'CommandType.RIGHT':
                break;
            case 'CommandType.LEFT':
                break;
            case 'CommandType.CENTER':
                break;
            case 'CommandType.EQUAL':
                break;
            case 'CommandType.WRAP':
                break;
            case 'CommandType.SINGLE':
                break;
            case 'CommandType.DOUBLE':
                break;
            case 'CommandType.TITLE':
                break;
            case 'CommandType.INDENT':
                break;
            case 'CommandType.BLANK':
                break;
            case 'CommandType.COLUMN':
                break;
            default:
    }
}

