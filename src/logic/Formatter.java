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

    // TODO - Example method, remove after
    public void setInput(String input) {
        this.input = input;
    }


    public String getOutput() {
        
        return output;
    }

    // TODO - Example method, remove after
    public void doubleInput() {
        input += input;
    }

}
