package test;

import logic.FileReader;
import logic.Formatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FormatterTest {

    private static FileReader fileReader = new FileReader();

    private String getInput(String testFileName) {
        return fileReader.getText("files/" + testFileName + "_Input.txt");
    }

    private String getOutput(String testFileName) {
        return fileReader.getText("files/" + testFileName + "_Output.txt");
    }

    @Test
    public void testCase1(){
        Formatter formatter = new Formatter();
        String file = "Case1";
        String input = getInput(file);
        String output = getOutput(file);
        Assertions.assertEquals(output, formatter.getOutput(input));
    }

    @Test
    public void testCase2(){
        Formatter formatter = new Formatter();
        String file = "Case2";
        String input = getInput(file);
        String output = getOutput(file);
        Assertions.assertEquals(output, formatter.getOutput(input));
    }

    @Test
    public void testCase3(){
        Formatter formatter = new Formatter();
        String file = "Case3";
        String input = getInput(file);
        String output = getOutput(file);
        Assertions.assertEquals(output, formatter.getOutput(input));
    }

    @Test
    public void testCase4(){
        Formatter formatter = new Formatter();
        String file = "Case4";
        String input = getInput(file);
        String output = getOutput(file);
        Assertions.assertEquals(output, formatter.getOutput(input));
    }

    @Test
    public void testCase5(){
        Formatter formatter = new Formatter();
        String file = "Case5";
        String input = getInput(file);
        String output = getOutput(file);
        Assertions.assertEquals(output, formatter.getOutput(input));
    }

    @Test
    public void testCase6(){
        Formatter formatter = new Formatter();
        String file = "Case6";
        String input = getInput(file);
        String output = getOutput(file);
        Assertions.assertEquals(output, formatter.getOutput(input));
    }

}