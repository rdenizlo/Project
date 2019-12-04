/** 
 *  Group:       1
 *  Name: 		 Thomas Chilton, Ramon Deniz, Antonio Gomez, Maximus Kieu
 *  Class:		 CSE360
 *  Section: 	 85141
 *  Assignment:  Final Project
 */
 package graphics;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.*;
import java.text.NumberFormat;
import javax.swing.filechooser.*;
import logic.Formatter;

/**
 * TextPanel serves as the main front end of our GUI
 * It allows the user to open files, save files, input text
 * in a textArea, format text from an open file or from inputed text,
 * and see formated text in a textArea or save it to a .txt file.
 * 
 * @author Antonio Gomez
 */
public class TextPanel extends JPanel
{
		//creates variables
		private JTextArea ErrorDisplayOP; //OP means user output
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
		
		public TextPanel()
		{
			//declares variables
			open = new JButton("Open file");
			save = new JButton("Save file");
			Input = new JTextArea(38,38);
			Output = new JTextArea(38,38);
			jsp1 = new JScrollPane(Input, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			jsp2 = new JScrollPane(Output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			ErrorDisplayOP = new JTextArea(6,40);
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
			setPreferredSize(new Dimension(1000,1000));
			Font font = new Font("Dialog", Font.BOLD, 75);
			Font font2 = new Font("Arial", Font.ITALIC, 25);
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
            /**
             * ButtonListener handles what happens when the buttons
             * on our GUI are pressed.
             * 
             */
			private class ButtonListener implements ActionListener
			{
				public void actionPerformed (ActionEvent event)
				{
					
						if (event.getSource() == save)
						{

							JFileChooser fs = new JFileChooser(new File("c:\\"));
							fs.setDialogTitle("Save a File");
							fs.setFileFilter(new FileTypeFilter(".txt", "Text File"));
							int result = fs.showSaveDialog(null);
							if(result == JFileChooser.APPROVE_OPTION) {
								
								String content = Output.getText();
								File fi = fs.getSelectedFile();
								
							
							
						       try {
						    	   FileWriter fw = new FileWriter(fi.getPath());
						    	   fw.write(content);
						    	   fw.flush();
						    	   fw.close();
						    	   JOptionPane.showMessageDialog(Output, "Successfully Saved");
						    	   
						       } catch (Exception e2) {
						    	   JOptionPane.showMessageDialog(null,  e2.getMessage());
						       }

							}	
							
						}
						else if (event.getSource() == format)
						{
							
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