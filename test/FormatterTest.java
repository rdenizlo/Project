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
    public void testInput() {
        String file = "Test";
        String input = getInput(file);

        Formatter formatter = new Formatter();
        formatter.setInput(input);
        formatter.doubleInput();

        String output = getOutput(file);
        Assertions.assertEquals(output, formatter.getOutput());
    }
}