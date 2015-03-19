/*
 *  COS 314 Project 1 
 *  Authors:
 *  Isabel Nel : 13070305
 *  Hanrich Potgieter :  12287343
 *
 */
package aiproject_game;
import java.util.Random;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;



public class Board {
    // embedded class to help out with initial states when creating a board. 
    GUI gui = null;
    Integer id = null;
    GameState gameState;
    public void setGameState(GameState x)
    {
        gameState = x;
    }
    public GameState getGameState()
    {
        GameState x = new GameState();
        x.setGameState(gameState.getGameState());
        return x;
    }

    public void setGUI(GUI g,int id)
    {
        gui = g;
        this.id = id;
    }
    protected class allocated{
        // ~~~~~~ class variables 
        protected int row;
        protected int col;
        
        //~~~~~~~~~ Class constructor.
        allocated(){
            row = -1;
            col = -1;
        }
        public void setValues(int r, int c){
            row = r;
            col = c;
        }
    }
    
    
    //~~~~~~~~~~ Class variables 
    public int N; // the size of the NxN matrix.
    public boolean validated; // this value will indicate if the N is valid
    private GamePieces[][] board; // this matrix will hold the whole game grid and which pieces are in which blocks
    public GamePieces temp;
    public int EMPTY = 0;
    public Random random;
    protected int numInitialCells;
    public ArrayList<allocated> allocatedList; // used for cell counts... in the searchAroundBlock function
    public ArrayList<allocated> checkedList; // used to make sure no stack overflow occurs in searcAroundBlock function
    
     public static  String WHITE = "\u001B[37m";
     public static  String BLUE = "\u001B[34m";
     public static  String RED = "\u001B[31m";
     public static  String PURPLE ="\u001B[35m";;
     public static  String GREEN = "\u001B[32m";
     public static  String RESET = "\u001B[0m";
     public static  String CYAN = "\u001B[36m";
     public static  String ANSI_PURPLE = "\u001B[35m";
    
    //~~~~~~~~ Class constructor;
    Board( int initialCells){
    
     N = 0;
     numInitialCells = initialCells;
     gameState = new GameState();
     
    }
    
    //~~~~~~~~~~~ validateBoardSize will ensure that the size specified by N is an even number > 8
    public boolean validateBoardSize(int n){
  
        // number needs to be > 8 and must be even 
        if(n > 8 && (n%2 == 0)){
            N = n;
            validated = true;
            createBoard();
            return validated;
        }else {
            validated = false;
            return validated;
        }
    }
    
    //~~~~~~~~~ createBoard will generate the game grid. 
    private void createBoard(){
       
        board = new GamePieces[N][N];
        int min = 0;
        int max = 0;
        allocated[] allocatedSells;
        boolean alreadyAllocated;
        int val1 = 0;
        int val2 = 0;
      
        // Initialize whole board to emptyBlocks
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                board[i][j] = new GamePieces();
            }
        }
        // Randomize each players cells...
         random = new Random();
         
//********PLAYER_A********* set pieces for Player A and initial state will start at the right side. 
         max = (N/2) -  2;
         min = 0;
         allocatedSells = new allocated[numInitialCells]; // this will just store the random numbers that already 
                                     // has blocks allocated for initial ststes
         // just initialize all of the allocated values....
         for(int i = 0; i < numInitialCells; i++)
             allocatedSells[i] = new allocated();
         
        // We now need to allocate 5 pieces of player A to 5 differant blocks 
        boolean stillLooking = true;
        for(int i = 0; i < numInitialCells; i++){
            
            while(stillLooking == true){
              val1 = random.nextInt((N-1) - 0 + 1) + 0;
              val2 = random.nextInt(max - min + 1) + min;
              alreadyAllocated = false;
              
              // Check if block has not yet been allocated to a piece 
              for(int j = 0; j < numInitialCells; j++)
              {
                  if((allocatedSells[j].col == val2) && (allocatedSells[j].row == val1) )
                      alreadyAllocated = true;
              }
              
              if(alreadyAllocated == false){ // then blocks can be allocated
                allocatedSells[i].setValues(val1, val2);
                stillLooking = false;
              }
              else // keep looking for random numbers to get an unallocated block. 
                  stillLooking = true;
            }
            stillLooking = true; // reset the value and start searching for 2 new values to allocate a piece to the block
         }
        //System.out.println("***********");
        for(int i = 0; i < numInitialCells; i++){
         //   System.out.println("allocated : "+ i + "  : " + allocatedSells[i].row + allocatedSells[i].col);
            board[allocatedSells[i].row][allocatedSells[i].col].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Dark);
        }
        
        // now we need to create a covered region where all squeres around each cell is covered in a 
        //ighter shade of that cell...
        for(int i = 0; i < numInitialCells; i++) {
            
            int row = allocatedSells[i].row;
            int col = allocatedSells[i].col;
         
      if((row !=0))
        if(board[row-1][col] != null) // one cell up
             if((board[row-1][col].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
                board[row-1][col].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
        
      if(row != (N-1))
        if(board[row+1][col] != null) // one cell down
           if((board[row+1][col].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row+1][col].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
       
        if(col != (N-1))
         if(board[row][col+1] != null) // one cell right
           if((board[row][col+1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row][col+1].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
         
        if(col != 0)
          if(board[row][col-1] != null) // one cell left
           if((board[row][col-1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row][col-1].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
        
       
     if((row != 0) && (col != N-1) )
        if(board[row-1][col+1] != null) // one cell diagonal up right
           if((board[row-1][col+1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row-1][col+1].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
       
     if((row != 0) && (col != 0))
        if(board[row-1][col-1] != null) // one cell diagonal up left
           if((board[row-1][col-1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row-1][col-1].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
     
     if((row != N-1) && (col != 0))
        if(board[row+1][col-1] != null) // one cell diagonal down left
           if((board[row+1][col-1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row+1][col-1].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
     
     if((row != N-1) && (col != N-1))
        if(board[row+1][col+1] != null) // one cell diagonal down right
           if((board[row+1][col+1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row+1][col+1].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
     
    }
       // now we need to join up the covered areas. 
        
// *****PLAYER A NOW PLACED ***** 
        
//********PLAYER_B*********  
         max =  N - 1;
         min = (N/2) + 2;
        // System.out.println("min: " + min + " max: " + max);
         allocatedSells = new allocated[numInitialCells]; // this will just store the random numbers that already 
                                            // has blocks allocated for initial ststes
         // just initialize all of the allocated values....
         for(int i = 0; i < numInitialCells; i++)
             allocatedSells[i] = new allocated();
         
        // We now need to allocate 5 pieces of player A to 5 differant blocks 
       stillLooking = true;
        for(int i = 0; i < numInitialCells; i++){
            
            while(stillLooking == true){
              val1 = random.nextInt((N-1) - 0 + 1) + 0;
              val2 = random.nextInt(max - min + 1) + min;
              alreadyAllocated = false;
              
              // Check if block has not yet been allocated to a piece 
              for(int j = 0; j < numInitialCells; j++)
              {
                  if((allocatedSells[j].col == val2) && (allocatedSells[j].row == val1) )
                      alreadyAllocated = true;
              }
              
              if(alreadyAllocated == false){ // then blocks can be allocated
                allocatedSells[i].setValues(val1, val2);
                stillLooking = false;
              }
              else // keep looking for random numbers to get an unallocated block. 
                  stillLooking = true;
            }
            stillLooking = true; // reset the value and start searching for 2 new values to allocate a piece to the block
         }
       // System.out.println("***********");
        int counter = 0;
        for(int i =0; i < numInitialCells; i++){
            
           // System.out.println("allocated : "+ i + "  : " + allocatedSells[i].row + allocatedSells[i].col);
            board[allocatedSells[i].row][allocatedSells[i].col].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Dark);
            counter++;
        }
        
        // now we need to create a covered region where all squeres around each cell is covered in a 
        //ighter shade of that cell...
        for(int i = 0; i < numInitialCells; i++) {
            
            int row = allocatedSells[i].row;
            int col = allocatedSells[i].col;
         
      if((row !=0))
        if(board[row-1][col] != null) // one cell up
             if((board[row-1][col].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
                board[row-1][col].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
        
      if(row != (N-1))
        if(board[row+1][col] != null) // one cell down
           if((board[row+1][col].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row+1][col].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
       
        if(col != (N-1))
         if(board[row][col+1] != null) // one cell right
           if((board[row][col+1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row][col+1].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
         
        if(col != 0)
          if(board[row][col-1] != null) // one cell left
           if((board[row][col-1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row][col-1].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
        
       
     if((row != 0) && (col != N-1) )
        if(board[row-1][col+1] != null) // one cell diagonal up right
           if((board[row-1][col+1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row-1][col+1].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
       
     if((row != 0) && (col != 0))
        if(board[row-1][col-1] != null) // one cell diagonal up left
           if((board[row-1][col-1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row-1][col-1].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
     
     if((row != N-1) && (col != 0))
        if(board[row+1][col-1] != null) // one cell diagonal down left
           if((board[row+1][col-1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row+1][col-1].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
     
     if((row != N-1) && (col != N-1))
        if(board[row+1][col+1] != null) // one cell diagonal down right
           if((board[row+1][col+1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block))
            board[row+1][col+1].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
     
}
        // ***** PLAYER B NOW PLACED..... ***** 
        
        coveredArea();  
       // printBoard();
       
    }
    
    public void printBoard(){
        System.out.println("Printing the Grid");
        
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                System.out.print("[");
                if(board[i][j].getCurrentGamePiece().compareTo(GamePieces.gamePieces.Empty_Block) == 0){
                    System.out.print("**");
                }else
                    if(board[i][j].getCurrentGamePiece().compareTo(GamePieces.gamePieces.Player_A_Dark) == 0){
                    System.out.print(BLUE+"aD"+RESET);
                }else
                    if(board[i][j].getCurrentGamePiece().compareTo(GamePieces.gamePieces.Player_A_Light) == 0){
                    System.out.print(CYAN+"aL"+RESET);
                }else
                    if(board[i][j].getCurrentGamePiece().compareTo(GamePieces.gamePieces.Player_B_Dark) == 0){
                    System.out.print(RED+"bD"+RESET);
                }else
                   if(board[i][j].getCurrentGamePiece().compareTo(GamePieces.gamePieces.Player_B_Light) == 0){
                    System.out.print(PURPLE+"bL"+RESET);
                }
                 System.out.print("]");
            }
            System.out.print('\n');
        }
            
    }
    
    //~~~~~~~~~~~ changeBoardPiece 
   public void changeBoardPiece(int atRow, int atCol, GamePieces newPiece){
       board[atRow][atCol] = newPiece;
   }
   
   //~~~~~~~~~~~~ getBoard - this returns the 2D array with all the pieces of the board in the current state. 
   public synchronized GamePieces[][] getBoard(){
       GamePieces[][] copyBoard = new GamePieces[N][N];
       for(int i = 0;i<N;i++)
            for(int j = 0;j<N;j++)
            {
                copyBoard[i][j] = new GamePieces();
                copyBoard[i][j].setCurrentGamePiece(board[i][j].getCurrentGamePiece());
            }
       return copyBoard;
   }
   
   //~~~~~~~~~~~ joinCoveredArea this function will check and join the pieces mentioned if they touch. 
   public void joinCoveredAreas(GamePieces.gamePieces piece, allocated[] allocatedArray){
       
   }
     
   public void coveredArea()
   {
       boolean change = true;
       while(change)
       {
           change = false;
       for(int i = 0;i < N;i++)
       {
           for(int j = 0; j < N; j++)
           {
               if(board[i][j].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block)
               {
                  
                   GamePieces above = null;
                   GamePieces below = null;
                   GamePieces infront = null;
                   GamePieces before = null;   
                   if(i-1 >= 0)
                       above = board[i-1][j];
                   if(i+1 < N)
                       below = board[i+1][j];
                   if(j-1 >= 0)
                       before = board[i][j-1];
                   if(j+1 < N)
                       infront = board[i][j+1];
                   
                   if(above!=null && infront!=null)
                   {
                       if(above.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light && infront.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light)
                       {
                           if(board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark ||board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark )
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                           change = true;
                       }
                       else if(above.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light && infront.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light)
                       {
                           if(board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark ||board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark )
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                           change = true;
                       }
                   }
                   
                   if(below!=null && infront!=null)
                   {
                       if(below.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light && infront.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light)
                       {
                           if(board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark ||board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark )
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                           change = true;
                       }
                       else if(below.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light && infront.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light)
                       {
                           if(board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark ||board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark )
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                           change = true;
                       }
                   }
                   
                   if(below!=null && before!=null)
                   {
                       if(below.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light && before.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light)
                       {
                           if(board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark ||board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark )
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                           change = true;
                       }
                       else if(below.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light && before.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light)
                       {
                           if(board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark ||board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark )
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                           change = true;
                       }
                   }
                   
                   if(above!=null && before!=null)
                   {
                       if(above.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light && before.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light)
                       {
                           if(board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark ||board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark )
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                           change = true;
                       }
                       else if(above.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light && before.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light)
                       {
                           if(board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark ||board[i][j].getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark )
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                           change = true;
                       }
                   }
               }
           }
       }
       }
   }
   
   public boolean move(Point from,Point to,Boolean test)
   {
       //Checking turns
       GamePieces piece = new GamePieces();
       GamePieces coveredPiece = new GamePieces();
       
       if(gameState.getGameState() == GameState.states.Player_A_Turn){
           piece.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Dark);
           coveredPiece.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
       }
       else if(gameState.getGameState() == GameState.states.Player_B_Turn)
       {
           piece.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Dark);
           coveredPiece.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
       }
       else
       {
           return false;
       }
       
       if(board[from.x][from.y].getCurrentGamePiece() != piece.getCurrentGamePiece())
       {
           return false;
       }
       
       Integer movecount = calculateAllowedNumberOfMoves(from.x, from.y);
       
       Integer xdir = Math.abs(to.x - from.x);
       Integer ydir = Math.abs(to.y - from.y);
       //System.out.println("xdir "+ xdir);
       //System.out.println("ydir "+ ydir);
       //System.out.println("mc "+ movecount);
       
       boolean cont = false;
       if(ydir >= 0 && ydir <= movecount)
       {
           if(xdir >= 0 && xdir <= movecount)
           {
               cont = true;
           }
       }
       if(cont == false)
           return false;
       
       //Check if we will be moving in a straight line.
       if(from.x != to.x)
           if(from.y != to.y)
               return false;
       //check if point is not itself
       if(from.x == to.x)
           if(from.y == to.y)
               return false;
       // Check if there is another object in our way. 
       if(isSelectable(to))
           return false;
       if(test)
           return true;
       clearSpace();
       
       takeOthers(to,to);
       
       
       //System.out.println("From " + from.x +"."+ from.y +"|| To "+ to.x +"."+ to.y);
       
       
       
       if(board[from.x][from.y].getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Dark) 
       {
           board[to.x][to.y].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Dark);
           board[from.x][from.y].setCurrentGamePiece(GamePieces.gamePieces.Empty_Block);
       }
       
       if(board[from.x][from.y].getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Dark) 
       {
           board[to.x][to.y].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Dark);
           board[from.x][from.y].setCurrentGamePiece(GamePieces.gamePieces.Empty_Block);
       }
       
       colorSpace();
       
       coveredArea();
       updateGUI();
       //Change turn when move is done..
       if(piece.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Dark)
           gameState.setGameState(GameState.states.Player_B_Turn);
       else if(piece.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Dark)
           gameState.setGameState(GameState.states.Player_A_Turn);
       return true;
   }
   
   public void clearSpace()
   {
       for(int i = 0; i< N;i++)
       {
           for(int j = 0; j < N;j++)
           {
              if(board[i][j].getCurrentGamePiece()== GamePieces.gamePieces.Player_B_Light || board[i][j].getCurrentGamePiece()== GamePieces.gamePieces.Player_A_Light )
              {
                  board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Empty_Block);
              }
           }
       }
   }
   
   public void ColorA()
   {
       for(int i = 0; i < N; i++)
       {
           for(int j = 0;j < N;j++)
           {
               if(board[i][j].getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Dark )
               {
                   colorPiece(new Point(i,j));
               }
           }
       }
   }
   public void ColorB()
   {
       for(int i = 0; i < N; i++)
       {
           for(int j = 0;j < N;j++)
           {
               if(board[i][j].getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Dark )
               {
                   colorPiece(new Point(i,j));
               }
           }
       }
   }
   public void colorSpace()
   {
       if(gameState.getGameState() == GameState.states.Player_A_Turn){
           ColorA();
           ColorB();
       }
       else if(gameState.getGameState() == GameState.states.Player_B_Turn)
       {
           ColorB();
           ColorA();
       }
   }
   
   public void colorPiece(Point tmp)
   {
       GamePieces piece = new GamePieces();
       
       piece.setCurrentGamePiece(board[tmp.x][tmp.y].getCurrentGamePiece());
       ArrayList<Point> list  = new ArrayList<Point>();
       // Side Top
       list.add(new Point(tmp.x-1,tmp.y-1));
       list.add(new Point(tmp.x,tmp.y-1));
       list.add(new Point(tmp.x+1,tmp.y-1));
       list.add(new Point(tmp.x-1,tmp.y));
       list.add(new Point(tmp.x+1,tmp.y));
       list.add(new Point(tmp.x-1,tmp.y+1));
       list.add(new Point(tmp.x,tmp.y+1));
       list.add(new Point(tmp.x+1,tmp.y+1));
       Iterator<Point> it = list.iterator(); 
       while(it.hasNext()){
           Point z = it.next();
           if(validPoint(z))
           {
               if(piece.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Dark && board[z.x][z.y].getCurrentGamePiece()== GamePieces.gamePieces.Empty_Block)
               {
                   board[z.x][z.y].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
               }
               else if(piece.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Dark && board[z.x][z.y].getCurrentGamePiece()== GamePieces.gamePieces.Empty_Block)
               {
                   board[z.x][z.y].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
               }
               
           }
       }
   }
   
   
   
   public synchronized void updateGUI()
   {
       if(gui != null)
           gui.update(id);
   }
   
   
   
   public int calculateAllowedNumberOfMoves(int row, int col){
      
       int cellCount = 0;
       allocatedList = new ArrayList<>();
       checkedList = new ArrayList<>();
       searchAroundBlock(row, col);
       cellCount = allocatedList.size();
       
       //System.out.println("cellCount = " + cellCount);
       
       return cellCount;
          
   }
   
   //~~~~~~~~~~~~ searchAround block will search around the block and return cellcount.
   // sRow and sCol specifies the block around which it must search,
   public void searchAroundBlock(int sRow, int sCol ){
      
       GamePieces piece = new GamePieces();
       GamePieces coveredPiece = new GamePieces();
       allocated tempVal;
       
       if(gameState.getGameState() == GameState.states.Player_A_Turn){
           piece.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Dark);
           coveredPiece.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
       }
       else{ // it is player B's turn....
           piece.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Dark);
           coveredPiece.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
       }
       
       // add block you are checking to checked list so that it does not check itself again....
       tempVal = new allocated();
       tempVal.setValues(sRow, sCol);
       checkedList.add(tempVal);
       
       //******check up
       if(sRow != 0){
            if(board[sRow-1][sCol].getCurrentGamePiece() == piece.getCurrentGamePiece()){// then it is a dark block add to arrayList
             addToArrayList(sRow-1,sCol);
             if(containsInCheckedList(sRow-1,sCol) == false)// block has not yet been checked
                searchAroundBlock(sRow-1,sCol);
            }
            if(board[sRow-1][sCol].getCurrentGamePiece() == coveredPiece.getCurrentGamePiece()){// then it is a light block so just check
               if(containsInCheckedList(sRow-1,sCol) == false)// block has not yet been checked
                    searchAroundBlock(sRow-1,sCol);
            }
      }
       // ******check diagonal up right
       if((sRow != 0) && sCol < N-1){
           if(board[sRow-1][sCol+1].getCurrentGamePiece() == piece.getCurrentGamePiece()){// then ot is a dark block add to arraylist and check around you
             addToArrayList(sRow-1,sCol+1);
             if(containsInCheckedList(sRow-1,sCol+1) == false)// block has ot yet been checked
                searchAroundBlock(sRow-1,sCol+1);
           }
           if(board[sRow-1][sCol+1].getCurrentGamePiece() == coveredPiece.getCurrentGamePiece()){// then it is a light block so just check
               if(containsInCheckedList(sRow-1,sCol+1) == false)// block has not yet been checked
                 searchAroundBlock(sRow-1,sCol+1);
           }
        }
        // ******check diagonal up left
       if((sRow != 0) && sCol != 0){
           if(board[sRow-1][sCol-1].getCurrentGamePiece() == piece.getCurrentGamePiece()){// then ot is a dark block add to arraylist and check around you
             addToArrayList(sRow-1,sCol-1);
             if(containsInCheckedList(sRow-1,sCol-1) == false)// block has not yet been checked
                searchAroundBlock(sRow-1,sCol-1);
           }
           if(board[sRow-1][sCol-1].getCurrentGamePiece() == coveredPiece.getCurrentGamePiece()){// then it is a light block so just check
               if(containsInCheckedList(sRow-1,sCol-1) == false)// block has not yet been checked
                 searchAroundBlock(sRow-1,sCol-1);
           }
        }
        // ******check left
       if( sCol != 0){
           if(board[sRow][sCol-1].getCurrentGamePiece() == piece.getCurrentGamePiece()){// then ot is a dark block add to arraylist and check around you
             addToArrayList(sRow,sCol-1);
             if(containsInCheckedList(sRow,sCol-1) == false)// block has not yet been checked
                searchAroundBlock(sRow,sCol-1);
           }
           if(board[sRow][sCol-1].getCurrentGamePiece() == coveredPiece.getCurrentGamePiece()){// then it is a light block so just check
               if(containsInCheckedList(sRow,sCol-1) == false)// block has not yet been checked
                 searchAroundBlock(sRow,sCol-1);
           }
        }
        // ******check right
       if( sCol < N-1){
           if(board[sRow][sCol+1].getCurrentGamePiece() == piece.getCurrentGamePiece()){// then ot is a dark block add to arraylist and check around you
             addToArrayList(sRow,sCol+1);
             if(containsInCheckedList(sRow,sCol+1) == false)// block has not yet been checked
             searchAroundBlock(sRow,sCol+1);
           }
           if(board[sRow][sCol+1].getCurrentGamePiece() == coveredPiece.getCurrentGamePiece()){// then it is a light block so just check
              if(containsInCheckedList(sRow,sCol+1) == false)// block has not yet been checked
                 searchAroundBlock(sRow,sCol+1);
           }
        }
       // ******check down
       if( sRow < N-1){
           if(board[sRow+1][sCol].getCurrentGamePiece() == piece.getCurrentGamePiece()){// then ot is a dark block add to arraylist and check around you
             addToArrayList(sRow+1,sCol);
             if(containsInCheckedList(sRow+1,sCol) == false)// block has not yet been checked
                 searchAroundBlock(sRow+1,sCol);
           }
           if(board[sRow+1][sCol].getCurrentGamePiece() == coveredPiece.getCurrentGamePiece()){// then it is a light block so just check
              if(containsInCheckedList(sRow+1,sCol) == false)// block has not yet been checked
               searchAroundBlock(sRow+1,sCol);
           }
        }
        // ******check diagonal right down
       if( (sRow < N-1) && (sCol < N-1)){
           if(board[sRow+1][sCol+1].getCurrentGamePiece() == piece.getCurrentGamePiece()){// then ot is a dark block add to arraylist and check around you
             addToArrayList(sRow+1,sCol+1);
             if(containsInCheckedList(sRow+1,sCol+1) == false)// block has not yet been checked
                searchAroundBlock(sRow+1,sCol+1);
           }
           if(board[sRow+1][sCol+1].getCurrentGamePiece() == coveredPiece.getCurrentGamePiece()){// then it is a light block so just check
              if(containsInCheckedList(sRow+1,sCol+1) == false)// block has not yet been checked
                 searchAroundBlock(sRow+1,sCol+1);
           }
        }
        // ******check diagonal left down
       if( (sRow < N-1) && (sCol != 0)){
           if(board[sRow+1][sCol-1].getCurrentGamePiece() == piece.getCurrentGamePiece()){// then ot is a dark block add to arraylist and check around you
             addToArrayList(sRow+1,sCol-1);
              if(containsInCheckedList(sRow+1,sCol-1) == false)// block has not yet been checked
             searchAroundBlock(sRow+1,sCol-1);
           }
           if(board[sRow+1][sCol-1].getCurrentGamePiece() == coveredPiece.getCurrentGamePiece()){// then it is a light block so just check
              if(containsInCheckedList(sRow+1,sCol-1) == false)// block has not yet been checked
               searchAroundBlock(sRow+1,sCol-1);
           }
        }
     
   }
   
   //~~~~~~~~~~~~~ addToAllocatedList - checks if the mentioned coordinites is in the allocated array list.. 
   private void addToArrayList(int row, int col){
       
       boolean alreadyContains = false;
       allocated temp = new allocated();
       temp.setValues(row, col);
       for(int i = 0; i < allocatedList.size(); i++){
           if(!allocatedList.isEmpty())
               if(((allocatedList.get(i).row) == row) && ((allocatedList.get(i).col) == col))
                   alreadyContains = true;
       }
           
       if(alreadyContains == false){
           allocatedList.add(temp);
       }
           
   }
   
   private boolean containsInCheckedList(int row, int col){
       
       boolean contains = false;
       if(!checkedList.isEmpty()){
           for(int i = 0; i < checkedList.size(); i++){
               if((checkedList.get(i).row == row) && (checkedList.get(i).col == col))
                   contains = true;
           }
       }
       return contains;
   }
   
   //~~~~~~~~~~~~~ validateSelectedPiece - selected piece must be one of the Payers own pieces. 
   public boolean validateSelectedPiece(int row, int col){
   
       boolean validated = false;
      if(gameState.getGameState() == GameState.states.Player_A_Turn)
          if(board[row][col].getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Dark)
              validated =  true;
      
       if(gameState.getGameState() == GameState.states.Player_B_Turn)
          if(board[row][col].getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Dark)
              validated =  true;
      
      
       return validated;
   }
   
   public boolean isSelectable(Point a)
   {
       if(board[a.x][a.y].getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Dark || board[a.x][a.y].getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Dark )
           return true;
       return false;
   }
   
   public void takeOthers(Point tmp,Point previous)
   {  
       GamePieces piece = new GamePieces();
       GamePieces coveredPiece = new GamePieces();
       
       if(gameState.getGameState() == GameState.states.Player_A_Turn){
           piece.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Dark);
           coveredPiece.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
       }
       else if(gameState.getGameState() == GameState.states.Player_B_Turn)
       {
           piece.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Dark);
           coveredPiece.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
       }
       /*
                    Top
                    XXX
               Back XXX Front
                    XXX
                    Bottom
       */
       ArrayList<Point> list  = new ArrayList<Point>(16);
       // Side Top
       list.add(new Point(tmp.x-1,tmp.y+2));
       list.add(new Point(tmp.x,tmp.y+2));
       list.add(new Point(tmp.x+1,tmp.y+2));
       //Side Front
       list.add(new Point(tmp.x+2,tmp.y+1));
       list.add(new Point(tmp.x+2,tmp.y));
       list.add(new Point(tmp.x+2,tmp.y-1));
       //Side Bottom
       list.add(new Point(tmp.x+1,tmp.y-2));
       list.add(new Point(tmp.x,tmp.y-2));
       list.add(new Point(tmp.x-1,tmp.y-2));
       //Side Back
       list.add(new Point(tmp.x-2,tmp.y-1));
       list.add(new Point(tmp.x-2,tmp.y));
       list.add(new Point(tmp.x-2,tmp.y+1));
       //Side above
       list.add(new Point(tmp.x,tmp.y+1));
       list.add(new Point(tmp.x,tmp.y-1));
       list.add(new Point(tmp.x+1,tmp.y));
       list.add(new Point(tmp.x-1,tmp.y));
       //Corners
       list.add(new Point(tmp.x-1,tmp.y-1));
       list.add(new Point(tmp.x+1,tmp.y-1));
       list.add(new Point(tmp.x+1,tmp.y+1));
       list.add(new Point(tmp.x-1,tmp.y+1));
       
       Iterator<Point> it = list.iterator();
       while(it.hasNext())
       {
           Point z = it.next();
           if(validPoint(z))
           {
               if(board[z.x][z.y].getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Dark && piece.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Dark)
               {
                   board[z.x][z.y].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Dark);
                   takeOthers(z,tmp);
               }
               if(board[z.x][z.y].getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Dark && piece.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Dark)
               {
                   board[z.x][z.y].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Dark);
                   takeOthers(z,tmp);
               }
           }
       }
   }
   
   public boolean validPoint(Point x)
   {
       if(x.x < N && x.x >= 0){
           if(x.y < N && x.y >= 0){
               //if(board[x.x][x.y].getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark && board[x.x][x.y].getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark )
               //{
                 return true;  
              //}
           }
       }
               
       return false;
  
    }
    public boolean validPointAI(Point x)
   {
       if(x.x < N && x.x >= 0){
           if(x.y < N && x.y >= 0){
               if(board[x.x][x.y].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block
                       || board[x.x][x.y].getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light
                       || board[x.x][x.y].getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light
                       )
                 return true;  
           }
       }
               
       return false;
  
    }
    

     public ArrayList<Move> getPossibleMoves()
    {
       GamePieces piece = new GamePieces();
       ArrayList<Move> moves = new ArrayList<Move>();
       if(gameState.getGameState() == GameState.states.Player_A_Turn){
           piece.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Dark);
       }
       else if(gameState.getGameState() == GameState.states.Player_B_Turn)
       {
           piece.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Dark);
       }
       
       for(int i = 0; i < N; i++){
           for(int j = 0; j < N; j++){
                if(board[i][j].getCurrentGamePiece() == piece.getCurrentGamePiece())
                {
                   
                    ArrayList<Move> tmp = calcMoves(new Point(i,j));
                    Iterator<Move> it = tmp.iterator();
                    while(it.hasNext())
                    {
                        Move move = it.next();
                        moves.add(move);
                    }
                }
           }
       }
       
       Iterator<Move> it = moves.iterator();
       while(it.hasNext())
       {
           Move move = it.next();
           //System.out.println("From x:" + move.from.x + " From y:" + move.from.y);
           //System.out.println("To x:" + move.to.x + " To y:" + move.to.y);
       }
       return moves;
    }
    
      public ArrayList<Move> calcMoves(Point from)
    {
         
        ArrayList<Move> moves = new ArrayList<Move>();
        Integer amount =  calculateAllowedNumberOfMoves(from.x, from.y);
        for(int i = 1;i <= amount;i++)
        {
            Point x  = new Point(from.x,from.y+i);
            if(validPointAI(x))
            {
                Move move = new Move();
                move.from = from;
                move.to = x;
                moves.add(move);
            }
                
        }
        for(int i = 1;i <= amount;i++)
        {
            Point x  = new Point(from.x,from.y-i);
            if(validPointAI(x))
            {
                Move move = new Move();
                move.from = from;
                move.to = x;
                moves.add(move);
            }
                
        }
        for(int i = 1;i <= amount;i++)
        {
            Point x  = new Point(from.x+i,from.y);
            if(validPointAI(x))
            {
                Move move = new Move();
                move.from = from;
                move.to = x;
                moves.add(move);
            }
                
        }
        for(int i = 1;i <= amount;i++)
        {
            Point x  = new Point(from.x-i,from.y);
            if(validPointAI(x))
            {
                Move move = new Move();
                move.from = from;
                move.to = x;
                moves.add(move);
            }
                
        }
        return moves;
    }
      
     public int heuristicDistanceCount(){
         
         ArrayList<Point> currentPlayer;
         ArrayList<Point> myCoveredAreas;
         GamePieces piece;
         GamePieces current;
         currentPlayer = new ArrayList<>();
         myCoveredAreas = new ArrayList<>();
         piece = new GamePieces();
         current = new GamePieces();
         boolean canEnterCoveredArea = false;
         
         if(gameState.getGameState() == GameState.states.Player_A_Turn){
             piece.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Dark);// opponenet
             current.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light); // current
         }else
         {
            piece.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Dark); // opponenet
            current.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);// current
         }
         // now you know where the current players' opponents cell positions and now need to calculate the distance tehy are from the covered areas of your cells.
           currentPlayer = getCells(piece);
          
         // for each position in current player determine how far it is from the closest covered area of the other player
          // if other player can enter the covered area (-5)
          // if other player can get close enough to touch border
        
          canEnterCoveredArea =  otherPlayerCanEnterCoveredArea(currentPlayer);
          
           
           
       
          
         return 0;
     }
     
     public boolean otherPlayerCanEnterCoveredArea(ArrayList Opponent){
         
         
         
     return false;
     }
     
      
    //~~~~~~~~~~~~~~ heuristicCellCount - returns the amount of the players cells - theopponents cells
      public synchronized int hueristicCellCount(Point p){
          
         
        
          int hValue = 0;
          GamePieces currentPiece;
          GamePieces piece;
          ArrayList<Point> enemyCells;
          ArrayList<Integer> distanceFromEnemy;
          int eX, eY;
          int pX, pY;
          int distance = 0;
          int temp = 0;
          
          currentPiece = new GamePieces();
          piece = new GamePieces();
          
          piece.setCurrentGamePiece(board[p.x][p.y].getCurrentGamePiece());
          if(piece.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Dark)
              currentPiece.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Dark);
          else
               currentPiece.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Dark);
              
          //currentPiece.setCurrentGamePiece(piece.getCurrentGamePiece());
          enemyCells = new ArrayList<>();
          distanceFromEnemy = new ArrayList<>();
          enemyCells = getCells(currentPiece);
          pX = p.x;
          pY = p.y;
          
          for(int i = 0; i < enemyCells.size(); i++){
             eX =  enemyCells.get(i).x;
             eY = enemyCells.get(i).y;
             
             if(eX == pX){// they are in range in the same row
                 distance = Math.abs((eY - pY));
             }else
             if(eY == pY){ // then they are in range in the same col
                 distance = Math.abs((eX - pX));
             }else
             {
                 distance = Math.abs((eX - pX)) + Math.abs((eY - pY));
             }

             distanceFromEnemy.add(distance);
                 
          }
          // now get the lowest value in the arrayList = will be the closest cell
          if(!distanceFromEnemy.isEmpty()){
              for(int i = 0; i < distanceFromEnemy.size(); i++)
              {
                  if(distanceFromEnemy.get(i) < distance){ // then swop and distance is now the smallest value thus far
                      distance = distanceFromEnemy.get(i);
                  }
              }
          }
          
          // now distance is the smallest value in the array... now assign a value on how good it is....
          // the closer it is to another cell the better 
         return distance;
          
     }
     
     //~~~~~~~~~ getCells - return the number of dark cells of specified player. 
     public ArrayList<Point> getCells(GamePieces piece){
         
         ArrayList<Point> playerPoints = new ArrayList<>();
         Point temp = new Point();
         
         for(int i = 0; i < N-1; i++){
            for(int j = 0; j  < N-1; j++){
                if(board[i][j].getCurrentGamePiece() == piece.getCurrentGamePiece()){
                    temp.x = i;
                    temp.y = j;
                    playerPoints.add(temp);
                }
            }
         }
         
         return playerPoints;
         
     }


    public void setBoard(GamePieces[][] board)
    {
       for(int i = 0;i<N;i++)
            for(int j = 0;j<N;j++)
                this.board[i][j] = board[i][j];
    }

}
