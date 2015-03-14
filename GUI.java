/*
Created by Isabeel Nel and Hanrich Potgieter
Date: 13 March 2015
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI
{
	Integer ROW_COUNT = 9;
	Integer COL_COUNT = 8;

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
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        frame.setLayout(layout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton b = new JButton("Start Game");
       	c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		frame.getContentPane().add(b,c);

		JRadioButton playerButton = new JRadioButton("Player vs Player");
    	//playerButton.setActionCommand(birdString);
    	playerButton.setSelected(true);
    	c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		frame.getContentPane().add(playerButton,c);

    	JRadioButton aiButton = new JRadioButton("AI vs AI");
    	//aiButton.setActionCommand("Ai ");
    	c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 1;
		frame.getContentPane().add(aiButton,c);

    	ButtonGroup group = new ButtonGroup();
    	group.add(playerButton);
    	group.add(aiButton);

    	int rowStart = 3;

    	for(int i = 0;i<ROW_COUNT;i++){
    		for(int j = 0;j <COL_COUNT;j++){
    			    c.fill = GridBagConstraints.HORIZONTAL;
					c.gridx = rowStart + j;
					c.gridy = i;
					c.gridheight = 1;
					JButton btn = new JButton();
					btn.setBackground(Color.GREEN);
					frame.getContentPane().add(btn,c);
    		}
    	}

		/*
 		JButton b = new JButton("Just fake button");
 		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		frame.getContentPane().add(b,c);
 		JButton button2 = new JButton("Just fake button");
 		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		 frame.getContentPane().add(button2,c);
        JButton button3 = new JButton("Just fake button");
 		c.fill = GridBagConstraints.HORIZONTAL;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
        JLabel label = new JLabel("This is our game!");
        //frame.getContentPane().add(label);
        
        frame.getContentPane().add(button3,c);
*/
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}