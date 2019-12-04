/** 
 *  Group:       1
 *  Name: 		 Thomas Chilton, Ramon Deniz, Antonio Gomez, Maximus Kieu
 *  Class:		 CSE360
 *  Section: 	 85141
 *  Assignment:  Final Project
 */

/**
 * This class takes any command input (e.g -n#, -r, -c...) and validates the command. For any valid command,
 * the class has the option to return the parameter (e.g -n#, -b#, -p#).
 * 
 *  @author Ramon Deniz
 */

package logic;

public class Command {

    private enum CommandType {
        CHARACTER, RIGHT, LEFT, CENTER, EQUAL, WRAP, SINGLE, DOUBLE, TITLE, INDENT, BLANK, COLUMN
    }

    private CommandType commandType;
    private String command, parameter;
    private String errorMessage;

    /**
     * Creates a Command instance and identifies the command being validated.
     * 
     * @param command
     */
    public Command(String command) {
        this.command = command;
        identifyCommand();
    }

    /**
     * Sets the error message if the command is unspecified.
     */
    private void setUnspecifiedCommandErrorMessage() {
        errorMessage = "The command: \"" + command + "\" doesn't specify the command type and only contains a dash"
                + " character.";
    }

    /**
     * Sets the error message if the command is invalid.
     */
    private void setInvalidCommandErrorMessage() {
        errorMessage = "The command: \"" + command + "\" doesn't begin with a dash" + " character.";
    }

    /**
     * Sets the error message if the command is unsupported.
     */
    private void setUnsupportedCommandErrorMessage() {
        errorMessage = "Command: \"-" + command.charAt(1) + "\" is not supported.";
    }

    /**
     * Sets the error message if the command holds more parameters than allowed.
     */
    private void setInvalidLengthErrorMessage() {
        errorMessage = "The command contained more parameters than expected. Make sure"
                + " there's no spaces or extra characters after a valid command.";
    }

    /**
     * Sets the error message if the command holds a parameter that is not a
     * positive integer value.
     */
    private void setInvalidNumberErrorMessage() {
        errorMessage = "The value: \"" + command.substring(2) + "\" following \"" + command.substring(0, 2)
                + "\" should be a positive integer value.";
    }

    /**
     * Identifies the corresponding command input to the correct Command type. It
     * also identifies any errors encountered.
     */
    private void identifyCommand() {
        if (command == null || command.length() < 2) {
            setUnspecifiedCommandErrorMessage();
        }

        char first = command.charAt(0);
        char second = command.charAt(1);

        if (first != '-') {
            setInvalidCommandErrorMessage();
        } else {
            switch (second) {
            case 'n':
                commandType = CommandType.CHARACTER;
                break;
            case 'r':
                commandType = CommandType.RIGHT;
                break;
            case 'l':
                commandType = CommandType.LEFT;
                break;
            case 'c':
                commandType = CommandType.CENTER;
                break;
            case 'e':
                commandType = CommandType.EQUAL;
                break;
            case 'w':
                commandType = CommandType.WRAP;
                break;
            case 's':
                commandType = CommandType.SINGLE;
                break;
            case 'd':
                commandType = CommandType.DOUBLE;
                break;
            case 't':
                commandType = CommandType.TITLE;
                break;
            case 'p':
                commandType = CommandType.INDENT;
                break;
            case 'b':
                commandType = CommandType.BLANK;
                break;
            case 'a':
                commandType = CommandType.COLUMN;
                break;
            default:
                setUnsupportedCommandErrorMessage();
                break;
            }
        }
    }

    /**
     * Validates if the command input meets the expected length.
     * 
     * @param expectedLength
     */
    private boolean validLength(int expectedLength) {
        if (expectedLength != command.length()) {
            setInvalidLengthErrorMessage();
            return false;
        }

        return true;
    }

    /**
     * Validates if the command input is a valid command of type: -n#
     */
    public boolean validCharactersPerLine() {
        if (commandType != CommandType.CHARACTER)
            return false;
        int index = 2;
        while (index < command.length() && command.charAt(index) >= '0' && command.charAt(index) <= '9') {
            index++;
        }
        if (validLength(index)) {
            parameter = command.substring(2, index);
            return true;
        }

        setInvalidNumberErrorMessage();
        return false;
    }

    /**
     * Validates if the command input is a valid command of type: -r
     */
    public boolean validRightJustify() {
        return commandType == CommandType.RIGHT && validLength(2);
    }

    /**
     * Validates if the command input is a valid command of type: -l
     */
    public boolean validLeftJustify() {
        return commandType == CommandType.LEFT && validLength(2);
    }

    /**
     * Validates if the command input is a valid command of type: -c
     */
    public boolean validCenter() {
        return commandType == CommandType.CENTER && validLength(2);
    }

    /**
     * Validates if the command input is a valid command of type: -e
     */
    public boolean validEqualSpace() {
        return commandType == CommandType.EQUAL && validLength(2);
    }

    /**
     * Validates if the command input is a valid command of type: -w- OR -w+
     */
    public boolean validWrap() {
        if (commandType != CommandType.WRAP)
            return false;
        if (validLength(3)) {
            parameter = Character.toString(command.charAt(2));
            return (parameter.equals("-") || parameter.equals("+"));
        }

        return false;
    }

    /**
     * Validates if the command input is a valid command of type: -s
     */
    public boolean validSingleSpace() {
        return commandType == CommandType.SINGLE && validLength(2);
    }

    /**
     * Validates if the command input is a valid command of type: -d
     */
    public boolean validDoubleSpace() {
        return commandType == CommandType.DOUBLE && validLength(2);
    }

    /**
     * Validates if the command input is a valid command of type: -t
     */
    public boolean validTitle() {
        return commandType == CommandType.TITLE && validLength(2);
    }

    /**
     * Validates if the command input is a valid command of type: -p#
     */
    public boolean validIndents() {
        if (commandType != CommandType.INDENT)
            return false;
        int index = 2;
        while (index < command.length()) {
            if (command.charAt(index) >= '0' && command.charAt(index) <= '9') {
                index++;
            }
        }
        if (validLength(index)) {
            parameter = command.substring(2, index);
            return true;
        }

        setInvalidNumberErrorMessage();
        return false;
    }

    /**
     * Validates if the command input is a valid command of type: -b#
     */
    public boolean validBlankLines() {
        if (commandType != CommandType.BLANK)
            return false;
        int index = 2;
        while (index < command.length()) {
            if (command.charAt(index) >= '0' && command.charAt(index) <= '9') {
                index++;
            }
        }
        if (validLength(index)) {
            parameter = command.substring(2, index);
            return true;
        }

        setInvalidNumberErrorMessage();
        return false;
    }

    /**
     * Validates if the command input is a valid command of type: -a#
     */
    public boolean validColumns() {
        if (commandType != CommandType.COLUMN)
            return false;
        int index = 2;
        while (index < command.length()) {
            if (command.charAt(index) >= '0' && command.charAt(index) <= '9') {
                index++;
            }
        }
        if (validLength(index)) {
            parameter = command.substring(2, index);
            if (parameter.equals("1") || parameter.equals("2"))
                return true;
            errorMessage = "The number of columns must be either 1 or 2.";
            return false;
        }

        setInvalidNumberErrorMessage();
        return false;
    }

    /**
     * Gets the parameter for commands that support it
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * Gets the error message generated from identifying the command input, if any.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Gets the CommandType of the command input as a String.
     */
    public String commandTypeToString() {
        String toString = commandType.toString();
        return toString;
    }

    /**
     * Gets the initial command input given when creating a Command instance.
     */
    public String getCommand() {
        return command;
    }
}
