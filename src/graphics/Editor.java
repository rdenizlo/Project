package graphics;

import javax.swing.*;

public class Editor extends JFrame {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    public Editor(String title) {
        super(title);
    }

    public void set(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(WIDTH, HEIGHT);
        setVisible(true);
    }

}
