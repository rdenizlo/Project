package test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import logic.FileReader;
import logic.Formatter;

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
        Assertions.assertEquals(formatter.getOutput(input), output);
    }

}