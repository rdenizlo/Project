package test;

import logic.Command;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandTest {

    @Test
    public void testCharacters() {
        Command command = new Command("-n15");
        boolean result = command.validCharactersPerLine();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals("15", parameter);
    }

    @Test
    public void testRight() {
        Command command = new Command("-r");
        boolean result = command.validRightJustify();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    @Test
    public void testLeft() {
        Command command = new Command("-l");
        boolean result = command.validLeftJustify();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    @Test
    public void testCenter(){
        Command command = new Command("-c");
        boolean result = command.validCenter();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    @Test
    public void testEqual(){
        Command command = new Command("-e");
        boolean result = command.validEqualSpace();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    @Test
    public void testWrapOn(){
        Command command = new Command("-w+");
        boolean result = command.validWrap();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals("+", parameter);
    }

    @Test
    public void testWrapOff(){
        Command command = new Command("-w-");
        boolean result = command.validWrap();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals("-", parameter);
    }

    @Test
    public void testSingle(){
        Command command = new Command("-s");
        boolean result = command.validSingleSpace();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    @Test
    public void testDouble(){
        Command command = new Command("-d");
        boolean result = command.validDoubleSpace();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    @Test
    public void testTitle(){
        Command command = new Command("-t");
        boolean result = command.validTitle();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(null, parameter);
    }

    @Test
    public void testIndent(){
        Command command = new Command("-p12355");
        boolean result = command.validIndents();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals("12355", parameter);
    }

    @Test
    public void testBlank(){
        Command command = new Command("-b1");
        boolean result = command.validBlankLines();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals("1", parameter);
    }

    @Test
    public void testColumn3(){
        Command command = new Command("-a3");
        boolean result = command.validColumns();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(false, result);
        Assertions.assertEquals("3", parameter);
    }

    @Test
    public void testColumn1(){
        Command command = new Command("-a1");
        boolean result = command.validColumns();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(true, result);
        Assertions.assertEquals("1", parameter);
    }

    @Test
    public void testInvalidCommand(){
        Command command = new Command("-z23");
        boolean result = command.validIndents();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(false, result);
        Assertions.assertEquals(null, parameter);
    }

    @Test
    public void testExtraParameters(){
        Command command = new Command("-n23 -b12");
        boolean result = command.validCharactersPerLine();
        System.out.println(command.getErrorMessage());
        String parameter = command.getParameter();
        Assertions.assertEquals(false, result);
    }

}
