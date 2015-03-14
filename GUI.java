/*
Created by Isabeel Nel and Hanrich Potgieter
Date: 13 March 2015
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Point;

public class GUI implements ActionListener
{
    private Integer n = 10;

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
        b.addActionListener(this);
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
		c.gridy = 2;
		c.gridheight = 1;
		frame.getContentPane().add(aiButton,c);

        JRadioButton haiButton = new JRadioButton("Human vs AI");
        //aiButton.setActionCommand("Ai ");
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        frame.getContentPane().add(haiButton,c);

    	ButtonGroup group = new ButtonGroup();
    	group.add(playerButton);
    	group.add(aiButton);
        group.add(haiButton);

        Label nLabel = new Label("Select N:");
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 4;
        c.gridheight = 1;
        frame.getContentPane().add(nLabel,c);

        SpinnerNumberModel model = new SpinnerNumberModel(10,10,1000,2); 
        JSpinner nSpinner = new JSpinner(model);
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = 4;
        c.gridheight = 1;
        frame.getContentPane().add(nSpinner,c);

        Label startingBlocksLabel = new Label("Select initial cells:");
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 5;
        c.gridheight = 1;
        frame.getContentPane().add(startingBlocksLabel,c);

        SpinnerNumberModel startingBlocksModel = new SpinnerNumberModel(1,1,1000,1); 
        JSpinner startingBlocksSpinner = new JSpinner(startingBlocksModel);
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = 5;
        c.gridheight = 1;
        frame.getContentPane().add(startingBlocksSpinner,c);

        Label plyDepthLabel = new Label("Ply depth:");
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 6;
        c.gridheight = 1;
        frame.getContentPane().add(plyDepthLabel,c);

        SpinnerNumberModel plyDepthLabelModel = new SpinnerNumberModel(1,1,1000,1); 
        JSpinner plyDepthLabelSpinner = new JSpinner(plyDepthLabelModel);
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = 6;
        c.gridheight = 1;
        frame.getContentPane().add(plyDepthLabelSpinner,c);

        Label pruningLabel = new Label("Alpha-Beta pruning");
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 7;
        c.gridheight = 1;
        frame.getContentPane().add(pruningLabel,c);

        JCheckBox pruningCheckbox = new JCheckBox(); 
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = 7;
        c.gridheight = 1;
        frame.getContentPane().add(pruningCheckbox,c);




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

    public void actionPerformed(ActionEvent e) {
        //System.out.println("hallo");
        // The game will start here. multiple instances of a game will be possibale
        GridBagConstraints c = new GridBagConstraints();
        JFrame window = new JFrame();
        window.setSize(400, 300);

        Grid grid = new Grid();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        window.add(grid);

        window.setVisible(true);
        grid.fillCell(0,0);
        grid.fillCell(3,4);
    }

    public class Grid extends JPanel implements MouseListener{

        private ArrayList<Point> fillCells;
       	private Integer ROW_COUNT = 8;
		private Integer COL_COUNT = 8;
		private Integer ROW_WIDTH = 20;
		private Integer COL_WIDTH = 30;
		private Integer totalWidth = COL_WIDTH*COL_COUNT;
		private Integer totalHeight = ROW_WIDTH*ROW_COUNT;

        public Grid() {
            fillCells = new ArrayList<>(25);
            addMouseListener(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Point fillCell : fillCells) {
                int cellX = (fillCell.x * COL_WIDTH);
                int cellY = (fillCell.y * ROW_WIDTH);
                //g.setColor(Color.RED);
                //g.fillRect(cellX, cellY, COL_WIDTH, ROW_WIDTH);
                //
                //g.fill(r);
                Graphics2D g2d = (Graphics2D) g;
                Rectangle r = new Rectangle(cellX, cellY, COL_WIDTH, ROW_WIDTH);
                g2d.fill(r);
            }
            g.setColor(Color.BLACK);
            g.drawRect(0,0,totalWidth, totalHeight);


            for (int i = 0; i <= totalWidth; i += COL_WIDTH) {
                g.drawLine(i, 0, i, totalHeight);
            }

            for (int i = 0; i <= totalHeight; i += ROW_WIDTH) {
                g.drawLine(0, i, totalWidth, i);
            }
        }

        public void fillCell(int x, int y) {
            fillCells.add(new Point(x, y));
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
 
        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Pressed events");
            System.out.println(getRow(e.getY())+","+getCol(e.getX()));
        }
        public String getCol(int x)
        {
            Integer tmp = x/COL_WIDTH;
            return tmp.toString();
        }
        public String getRow(int y)
        {
            Integer tmp = y/ROW_WIDTH;
            return tmp.toString();
        }




    }

}