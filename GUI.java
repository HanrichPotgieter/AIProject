/*
Created by Isabeel Nel and Hanrich Potgieter
Date: 13 March 2015
*/


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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("This is our game!");
        frame.getContentPane().add(label);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}