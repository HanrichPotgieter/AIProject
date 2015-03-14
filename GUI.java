/*
Created by Isabeel Nel and Hanrich Potgieter
Date: 13 March 2015
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Point;

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

    	JFrame window = new JFrame();
        window.setSize(840, 560);

		Grid grid = new Grid();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 100;
		c.gridwidth = 100;
		window.add(grid);

		window.setVisible(true);


		/*
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
		*/
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

    public static class Grid extends JPanel {

        private ArrayList<Point> fillCells;

        public Grid() {
            fillCells = new ArrayList<>(25);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Point fillCell : fillCells) {
                int cellX = 10 + (fillCell.x * 10);
                int cellY = 10 + (fillCell.y * 10);
                g.setColor(Color.RED);
                g.fillRect(cellX, cellY, 10, 10);
            }
            g.setColor(Color.BLACK);
            g.drawRect(10, 10, 800, 500);

            for (int i = 10; i <= 800; i += 10) {
                g.drawLine(i, 10, i, 510);
            }

            for (int i = 10; i <= 500; i += 10) {
                g.drawLine(10, i, 810, i);
            }
        }

        public void fillCell(int x, int y) {
            fillCells.add(new Point(x, y));
            repaint();
        }

    }

}