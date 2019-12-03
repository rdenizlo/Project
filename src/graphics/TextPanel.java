package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.text.NumberFormat;
import logic.Formatter;

public class TextPanel extends JPanel {
    //creates variables
    private JTextField ErrorDisplayOP; //OP means user output
    private JTextArea Input;
    private JTextArea Output;
    private JButton open;
    private JButton save;
    private JButton clear;
    private JButton format;
    private JTextArea log;
    private JFileChooser fc;
    private JScrollPane jsp1;
    private JScrollPane jsp2;
    private Formatter formatter;

    NumberFormat fmt = NumberFormat.getCurrencyInstance();

    public TextPanel() {
        //declares variables
        open = new JButton("Open file");
        save = new JButton("Save file");
        Input = new JTextArea(38, 38);
        Output = new JTextArea(38, 38);
        jsp1 = new JScrollPane(Input, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp2 = new JScrollPane(Output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        ErrorDisplayOP = new JTextField(20);
        clear = new JButton("Clear");
        format = new JButton("Format");
        JFileChooser fc = new JFileChooser();
        Output.setEditable(false);

        //adds labels and text fields
        add(open);
        add(save);
        add(jsp1);
        add(jsp2);
        add(format);
        add(clear);
        add(ErrorDisplayOP);


        //sets size, color, and font
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(1000, 1000));
        Font font = new Font("Dialog", Font.BOLD, 75);
        Font font2 = new Font("Dialog", Font.ITALIC, 50);
        Font font3 = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12);
        Input.setFont(font3);
        Output.setFont(font3);
        ErrorDisplayOP.setFont(font2);
        open.setFont(font);
        save.setFont(font);
        clear.setFont(font);
        format.setFont(font);


        ErrorDisplayOP.setText("Currently No Errors");

        //makes a button active
        open.addActionListener(new ButtonListener());
        save.addActionListener(new ButtonListener());
        clear.addActionListener(new ButtonListener());
        format.addActionListener(new ButtonListener());
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            if (event.getSource() == save) {

                JFileChooser fs = new JFileChooser(new File("c:\\"));
                fs.setDialogTitle("Save a File");
                fs.setFileFilter(new FileTypeFilter(".txt", "Text File"));
                int result = fs.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {

                    String content = Output.getText();
                    File fi = fs.getSelectedFile();


                    try {
                        FileWriter fw = new FileWriter(fi.getPath());
                        fw.write(content);
                        fw.flush();
                        fw.close();
                        JOptionPane.showMessageDialog(Output, "Successfully Saved");


                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, e2.getMessage());
                    }


                }
            } else if (event.getSource() == format) {

                String str = Input.getText();
                formatter = new Formatter();
                Output.setText(formatter.getOutput(str));
                ErrorDisplayOP.setText(formatter.getErrors());
                formatter = null;

            }

            if (event.getSource() == clear) {

                ErrorDisplayOP.setText("");
                Input.setText("");
                Output.setText("");

            }

            if (event.getSource() == open) {

                OpenFile of = new OpenFile();

                try {
                    of.PickMe();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Input.setText(of.sb.toString());

            }

        }


    }


}

