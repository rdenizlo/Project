/** 
 *  Group:       1
 *  Name: 		 Thomas Chilton, Ramon Deniz, Antonio Gomez, Maximus Kieu
 *  Class:		 CSE360
 *  Section: 	 85141
 *  Assignment:  Final Project
 */

/**
* Tests the quality and stability of the all commands (e.g -n#, -r, -c, -p#...) and ensures that the test cases
* pass throughout the project's lifecycle.
* 
*  @see Command
*  @author Ramon Deniz
*/

package test;

import logic.Command;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandTest {

    /**
     * Test -n# command
     */
    @Test
    public void testCharacters() {
        Command command = new Command("-n15");
        boolean result = command.validCharactersPerLine();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals("15", parameter);
    }

    /**
     * Test -r command
     */
    @Test
    public void testRight() {
        Command command = new Command("-r");
        boolean result = command.validRightJustify();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    /**
     * Test -l command
     */
    @Test
    public void testLeft() {
        Command command = new Command("-l");
        boolean result = command.validLeftJustify();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    /**
     * Test -c command
     */
    @Test
    public void testCenter() {
        Command command = new Command("-c");
        boolean result = command.validCenter();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    /**
     * Test -e command
     */
    @Test
    public void testEqual() {
        Command command = new Command("-e");
        boolean result = command.validEqualSpace();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    /**
     * Test -w+ command
     */
    @Test
    public void testWrapOn() {
        Command command = new Command("-w+");
        boolean result = command.validWrap();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals("+", parameter);
    }

    /**
     * Test -w- command
     */
    @Test
    public void testWrapOff() {
        Command command = new Command("-w-");
        boolean result = command.validWrap();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals("-", parameter);
    }

    /**
     * Test -s command
     */
    @Test
    public void testSingle() {
        Command command = new Command("-s");
        boolean result = command.validSingleSpace();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    /**
     * Test -d command
     */
    @Test
    public void testDouble() {
        Command command = new Command("-d");
        boolean result = command.validDoubleSpace();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    /**
     * Test -t command
     */
    @Test
    public void testTitle() {
        Command command = new Command("-t");
        boolean result = command.validTitle();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    /**
     * Test -p# command
     */
    @Test
    public void testIndent() {
        Command command = new Command("-p12355");
        boolean result = command.validIndents();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals("12355", parameter);
    }

    /**
     * Test -b# command
     */
    @Test
    public void testBlank() {
        Command command = new Command("-b1");
        boolean result = command.validBlankLines();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals("1", parameter);
    }

    /**
     * Test -a3 command, should fail
     */
    @Test
    public void testColumn3() {
        Command command = new Command("-a3");
        boolean result = command.validColumns();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(false, result);
        Assertions.assertEquals("3", parameter);
    }

    /**
     * Test -a1 command
     */
    @Test
    public void testColumn1() {
        Command command = new Command("-a1");
        boolean result = command.validColumns();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals("1", parameter);
    }

    /**
     * Test for an invalid command
     */
    @Test
    public void testInvalidCommand() {
        Command command = new Command("-z23");
        boolean result = command.validIndents();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(false, result);
        Assertions.assertEquals(null, parameter);
    }

    /**
     * Test for a command that has extra parameters
     */
    @Test
    public void testExtraParameters() {
        Command command = new Command("-n23 -b12");
        boolean result = command.validCharactersPerLine();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(false, result);
    }

    /**
     * Test -b that never specifies a number amount
     */
    @Test
    public void testInvalidBlank() {
        Command command = new Command("-b");
        boolean result = command.validBlankLines();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(false, result);
        Assertions.assertEquals(null, parameter);
    }

}
