/*
Created by Isabeel Nel and Hanrich Potgieter
Date: 13 March 2015
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI
{
	public GUI()
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                creatGUI();
            }
        });
	}

	public void creatGUI()
	{
        JFrame frame = new JFrame("GAME");
        GridLayout layout = new GridLayout(0,3);
        frame.setLayout(layout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 		JButton b = new JButton("Just fake button");
 		JButton c = new JButton("Just fake button");
 		//layout.add(b);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("This is our game!");
        frame.getContentPane().add(label);
        frame.getContentPane().add(b);
        frame.getContentPane().add(c);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}