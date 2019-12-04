/** 
 *  Group:       1
 *  Name: 		 Thomas Chilton, Ramon Deniz, Antonio Gomez, Maximus Kieu
 *  Class:		 CSE360
 *  Section: 	 85141
 *  Assignment:  Final Project
 */

/**
 * Tests the quality and stability of the Formatter class and ensures that the test cases pass for the
 * project's entire lifecycle.
 * 
 *  @author Thomas Chilton, Ramon Deniz
 *  @see Formatter
 */

package test;

import logic.FileReader;
import logic.Formatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FormatterTest {

    private static FileReader fileReader = new FileReader();

    /**
     * Opens the specified file, but limits its scope to Input files only
     * 
     * @param testFileName
     */
    private String getInput(String testFileName) {
        return fileReader.getText("files/" + testFileName + "_Input.txt");
    }

    /**
     * Opens the specified file, but limits its scope to Output files only
     * 
     * @param testFileName
     */
    private String getOutput(String testFileName) {
        return fileReader.getText("files/" + testFileName + "_Output.txt");
    }

    /**
     * Opens the 1st test case, both input and output, and compares the Formatter's
     * result to the expected output.
     */
    @Test
    public void testCase1() {
        Formatter formatter = new Formatter();
        String file = "Case1";
        String input = getInput(file);
        String output = getOutput(file);
        Assertions.assertEquals(output, formatter.getOutput(input));
    }

    /**
     * Opens the 2nd test case, both input and output, and compares the Formatter's
     * result to the expected output.
     */
    @Test
    public void testCase2() {
        Formatter formatter = new Formatter();
        String file = "Case2";
        String input = getInput(file);
        String output = getOutput(file);
        Assertions.assertEquals(output, formatter.getOutput(input));
    }

    /**
     * Opens the 3rd test case, both input and output, and compares the Formatter's
     * result to the expected output.
     */
    @Test
    public void testCase3() {
        Formatter formatter = new Formatter();
        String file = "Case3";
        String input = getInput(file);
        String output = getOutput(file);
        Assertions.assertEquals(output, formatter.getOutput(input));
    }

    /**
     * Opens the 4th test case, both input and output, and compares the Formatter's
     * result to the expected output.
     */
    @Test
    public void testCase4() {
        Formatter formatter = new Formatter();
        String file = "Case4";
        String input = getInput(file);
        String output = getOutput(file);
        Assertions.assertEquals(output, formatter.getOutput(input));
    }

    /**
     * Opens the 5th test case, both input and output, and compares the Formatter's
     * result to the expected output.
     */
    @Test
    public void testCase5() {
        Formatter formatter = new Formatter();
        String file = "Case5";
        String input = getInput(file);
        String output = getOutput(file);
        Assertions.assertEquals(output, formatter.getOutput(input));
    }

    /**
     * Opens the 6th test case, both input and output, and compares the Formatter's
     * result to the expected output.
     */
    @Test
    public void testCase6() {
        Formatter formatter = new Formatter();
        String file = "Case6";
        String input = getInput(file);
        String output = getOutput(file);
        Assertions.assertEquals(output, formatter.getOutput(input));
    }

}