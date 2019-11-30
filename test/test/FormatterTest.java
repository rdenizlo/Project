package test;

import logic.FileReader;

class FormatterTest {

    private static FileReader fileReader = new FileReader();

    private String getInput(String testFileName) {
        return fileReader.getText("files/" + testFileName + "_Input.txt");
    }

    private String getOutput(String testFileName) {
        return fileReader.getText("files/" + testFileName + "_Output.txt");
    }

}