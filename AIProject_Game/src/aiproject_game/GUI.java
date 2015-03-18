/*
 *  COS 314 Project 1 
 *  Authors:
 *  Isabel Nel : 13070305
 *  Hanrich Potgieter :  12287343
 *
 */
package aiproject_game;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Point;

public class GUI implements ActionListener
{
    ArrayList<Grid> boards = new ArrayList<Grid>();
    Integer id = 0;
    //Buttons and stuff
    SpinnerNumberModel model = new SpinnerNumberModel(10,10,1000,2);
    JSpinner nSpinner = new JSpinner(model);
    SpinnerNumberModel startingBlocksModel = new SpinnerNumberModel(1,1,1000,1); 
    JSpinner startingBlocksSpinner = new JSpinner(startingBlocksModel); 
    private Integer n = 10;

	public GUI()
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                creatGUI();
            }
        });
	}

    public void setConstaraints(GridBagConstraints c,int x,int y,int h)
    {
        c.fill = GridBagConstraints.BOTH;
        c.gridx = x;
        c.gridy = y;
        c.gridheight = h;
    }

	public void creatGUI()
	{
        JFrame frame = new JFrame("GAME");
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        frame.setLayout(layout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(this);
        setConstaraints(c,0,0,3);
		frame.getContentPane().add(startButton,c);

		JRadioButton playerButton = new JRadioButton("Player vs Player");
    	playerButton.setSelected(true);
        setConstaraints(c,1,0,1);
		frame.getContentPane().add(playerButton,c);

    	JRadioButton aiButton = new JRadioButton("AI vs AI");
    	//aiButton.setActionCommand("Ai ");
        setConstaraints(c,1,2,1);
		frame.getContentPane().add(aiButton,c);

        JRadioButton haiButton = new JRadioButton("Human vs AI");
        //aiButton.setActionCommand("Ai ");
        setConstaraints(c,1,1,1);
        frame.getContentPane().add(haiButton,c);

    	ButtonGroup group = new ButtonGroup();
    	group.add(playerButton);
    	group.add(aiButton);
        group.add(haiButton);

        Label nLabel = new Label("Select N:");
        setConstaraints(c,0,4,1);
        frame.getContentPane().add(nLabel,c);
        setConstaraints(c,1,4,1);
        frame.getContentPane().add(nSpinner,c);

        Label startingBlocksLabel = new Label("Select initial cells:");
        setConstaraints(c,0,5,1);
        frame.getContentPane().add(startingBlocksLabel,c);

        setConstaraints(c,1,5,1);
        frame.getContentPane().add(startingBlocksSpinner,c);

        Label plyDepthLabel = new Label("Ply depth:");
        setConstaraints(c,0,6,1);
        frame.getContentPane().add(plyDepthLabel,c);

        SpinnerNumberModel plyDepthLabelModel = new SpinnerNumberModel(1,1,1000,1); 
        JSpinner plyDepthLabelSpinner = new JSpinner(plyDepthLabelModel);
        setConstaraints(c,1,6,1);
        frame.getContentPane().add(plyDepthLabelSpinner,c);

        Label pruningLabel = new Label("Alpha-Beta pruning");
        setConstaraints(c,0,7,1);
        frame.getContentPane().add(pruningLabel,c);

        JCheckBox pruningCheckbox = new JCheckBox(); 
        setConstaraints(c,1,7,1);
        frame.getContentPane().add(pruningCheckbox,c);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("hallo");
        // The game will start here. multiple instances of a game will be possibale
        GridBagConstraints c = new GridBagConstraints();
        JFrame window = new JFrame();
       
        n = (Integer)nSpinner.getValue();
        Integer num = (Integer)startingBlocksSpinner.getValue();
        Board board = new Board(num);
        board.setGUI(this,id);
        id++;
        Grid grid = new Grid(n,board);
        boards.add(grid);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
     
        window.setSize(grid.getWidthGrid(),grid.getHeightGrid());
        window.add(grid);
        window.setVisible(true);
        
        if(board.validateBoardSize(n))
        {
            loadBoard(board.getBoard(),grid);
        }
        else
        {
            System.out.println("Failed to create board");
        }
        
        AI ai = new AI(board);
        ai.start();
        
    }

    public void loadBoard(GamePieces[][] boardLayout,Grid grid)
    {
        for(int i = 0;i<n;i++)
        {
            for(int j = 0;j<n;j++)
            {
                Enum tmp = boardLayout[i][j].getCurrentGamePiece();
                if(tmp.toString() == "Empty_Block")
                {
                    grid.fillCell(j,i,Color.WHITE);
                }
                if(tmp.toString() == "Player_A_Dark")
                {
                    grid.fillCell(j,i,Color.BLUE);
                }
                if(tmp.toString() == "Player_B_Dark")
                {
                    grid.fillCell(j,i,Color.RED);
                }
                if(tmp.toString() == "Player_A_Light")
                {
                    grid.fillCell(j,i,Color.CYAN);
                }
                if(tmp.toString() == "Player_B_Light")
                {
                    grid.fillCell(j,i,Color.PINK);
                }
 
            }
        }
    }

    public class cellContents
    {
        public Point point;
        public Color color;
        public cellContents(Point p,Color c)
        {
            point = p;
            color = c;
        }
    }

    public class Grid extends JPanel implements MouseListener{
        private Board board = null;
        private ArrayList<Point> clicks = new ArrayList<Point>();
        private ArrayList<cellContents> fillCells;
       	private Integer ROW_COUNT = 8;
		private Integer COL_COUNT = 8;
		private Integer ROW_WIDTH = 20;
		private Integer COL_WIDTH = 30;
		private Integer totalWidth = COL_WIDTH*COL_COUNT;
		private Integer totalHeight = ROW_WIDTH*ROW_COUNT;
        private Point selectedPoint =  new Point();

        public Grid(int n,Board b) {
            board = b;
            ROW_COUNT = n;
            COL_COUNT = n;
            totalWidth = COL_WIDTH*COL_COUNT;
            totalHeight = ROW_WIDTH*ROW_COUNT;
            fillCells = new ArrayList<>(25);
            addMouseListener(this);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (cellContents fillCell : fillCells) {
                int cellX = (fillCell.point.x * COL_WIDTH);
                int cellY = (fillCell.point.y * ROW_WIDTH);
                g.setColor(fillCell.color);
                Graphics2D g2d = (Graphics2D) g;
               
                Rectangle r = new Rectangle(cellX, cellY, COL_WIDTH, ROW_WIDTH);
                g2d.fill(r);
                //g.setColor(Color.BLACK);
                //Rectangle rO = new Rectangle(cellX+2, cellY+2, COL_WIDTH-4, ROW_WIDTH-4);
                //g2d.fill(rO);
            }
            g.setColor(Color.BLACK);
            g.drawRect(0,0,totalWidth, totalHeight);


            for (int i = 0; i <= totalWidth; i += COL_WIDTH) {
                g.drawLine(i, 0, i, totalHeight);
            }

            for (int i = 0; i <= totalHeight; i += ROW_WIDTH) {
                g.drawLine(0, i, totalWidth, i);
            }

            // Draw selection
                int cellX = (selectedPoint.x * COL_WIDTH);
                int cellY = (selectedPoint.y * ROW_WIDTH);
                Graphics2D g2d = (Graphics2D) g;    
                g.setColor(Color.BLACK);
                Rectangle rO = new Rectangle(cellX+2, cellY+2, COL_WIDTH-4, ROW_WIDTH-4);
                g2d.fill(rO);
        }

        public void fillCell(int x, int y,Color c) {
            cellContents cell = new cellContents(new Point(x, y),c);
            fillCells.add(cell);
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
            //System.out.println("Pressed events");
            //System.out.println(getRow(e.getY())+","+getCol(e.getX()));
            if(board.isSelectable(new Point(getRow(e.getY()),getCol(e.getX()))) || clicks.size() >= 1)
            {
                System.out.println("selectable");
                  clicks.add(new Point(getRow(e.getY()),getCol(e.getX())));     
            }
            //clicks.add(new Point(getRow(e.getY()),getCol(e.getX())));
            selectedPoint.y = getRow(e.getY());
            selectedPoint.x = getCol(e.getX());
            board.getPossibleMoves();
            if(clicks.size() > 1)
            {
                Point from = clicks.remove(0);
                Point to = clicks.remove(0);
                board.move(from,to,false);
            }
            repaint();
               
        }
        public Integer getCol(int x)
        {
            Integer tmp = x/COL_WIDTH;
            return tmp;
        }
        public Integer getRow(int y)
        {
            Integer tmp = y/ROW_WIDTH;
            return tmp;
        }
        public Integer getWidthGrid()
        {
            return totalWidth;
        }
        public Integer getHeightGrid()
        {
            return totalHeight+ROW_WIDTH;
        }
        
        public void update()
        {
            loadBoard(board.getBoard(),this);
            repaint();
        }
    }

    public void update(int id)
    {
        Grid tmp = boards.get(id);
        tmp.update();
      
    }

}