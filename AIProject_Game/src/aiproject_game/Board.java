/*
 *  COS 314 Project 1 
 *  Authors:
 *  Isabel Nel : 13070305
 *  Hanrich Potgieter : 
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
    public GamePieces[][] board; // this matrix will hold the whole game grid and which pieces are in which blocks
    public GamePieces temp;
    int EMPTY = 0;
    Random random;
    int numInitialCells;
    
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
        printBoard();
       
    }
    
    private void printBoard(){
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
   public GamePieces[][] getBoard(){
       return board;
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
   
   public boolean move(Point from,Point to)
   {
       calculateAllowedNumberOfMoves(from.x, from.y);
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
       clearSpace();
       
       takeOthers(to,to);
       
       
       System.out.println("From " + from.x +"."+ from.y +"|| To "+ to.x +"."+ to.y);
       
       
       
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
   
   public void colorSpace()
   {
       for(int i = 0; i < N; i++)
       {
           for(int j = 0;j < N;j++)
           {
               if(board[i][j].getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Dark)
               {
                   GamePieces a = null;
                   GamePieces b = null;
                   GamePieces c = null;
                   GamePieces d = null;
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
                   
                   if(i-1 >= 0 && j-1>=0)
                       a = board[i-1][j-1];
                   if(i+1 <N && j-1 >=0)
                       b = board[i+1][j-1];
                   if(i-1 >= 0 && j+1<N)
                       c = board[i-1][j+1];
                   if(i+1 < N  && j+1<N)
                       d = board[i+1][j+1];
       
                       
                   if(a != null)
                       if(a.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark || a.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark)
                       a.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                   if(b != null)
                       if(b.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark || b.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark)
                       b.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                   if(c != null)
                       if(c.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark || c.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark)
                       c.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                   if(d != null)
                       if(d.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark || d.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark)
                       d.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                   
                   if(infront != null)
                       if(infront.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark || infront.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark)
                       infront.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                   if(below != null)
                       if(below.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark || below.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark)
                       below.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                   if(above != null)
                       if(above.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark || above.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark)
                       above.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                   if(before != null)
                       if(before.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark || before.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark)
                       before.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
               }
               
                          
               if(board[i][j].getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Dark)
               {
                   GamePieces a = null;
                   GamePieces b = null;
                   GamePieces c = null;
                   GamePieces d = null;
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
                   
                   if(i-1 >= 0 && j-1>=0)
                       a = board[i-1][j-1];
                   if(i+1 <N && j-1 >=0)
                       b = board[i+1][j-1];
                   if(i-1 >= 0 && j+1<N)
                       c = board[i-1][j+1];
                   if(i+1 < N  && j+1<N)
                       d = board[i+1][j+1];
                   
                    if(a != null)
                        if(a.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark|| a.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark)
                       a.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                   if(b != null)
                       if(b.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark || b.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark)
                       b.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                   if(c != null)
                       if(c.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark || c.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark)
                       c.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                   if(d != null)
                       if(d.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark || d.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark)
                       d.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                   
                   if(infront != null)
                       if(infront.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark || infront.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark)
                       infront.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                   if(below != null)
                       if(below.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark || below.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark)
                       below.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                   if(above != null)
                       if(above.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark || above.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark )
                       above.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                   if(before != null)
                       if(before.getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark || before.getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark)
                       before.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
               }
           }
       }
   }
   
   
   
   public void updateGUI()
   {
       gui.update(id);
   }
   
   
   
   public int calculateAllowedNumberOfMoves(int row, int col){
       // first validate the selected block 
      // boolean validated = validateSelectedPiece(row, col);
       int cellCount = 0;
       int tempVal = 0;
       int countUp = 0;
       int countDown = 0;
       int countLeft = 0;
       int countRight = 0;
       int newRow = 0;
       int newCol = 0;
       boolean checkedDone = false;
       GamePieces coveredPiece =  new GamePieces();
       GamePieces piece = board[row][col];
       
       if(piece.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Dark)
           coveredPiece.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
       if(piece.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Dark)
           coveredPiece.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
       
 //*****************COUNT UP************
           newRow = row;
           newCol = col;
           
           while(checkedDone == false){
               if(newRow > 0 && newRow < N){
                   if(board[newRow-1][newCol].getCurrentGamePiece() == piece.getCurrentGamePiece())// dark block
                       countUp++;
                   if(board[newRow-1][newCol].getCurrentGamePiece() == coveredPiece.getCurrentGamePiece())// covered block
                       countUp++;
                   if(board[newRow-1][newCol].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block)// white block
                       checkedDone = true;
               }else
                   checkedDone = true;
               newRow--;
           }
  //***********************COUNT DOWN*************
           newRow = row;
           newCol = col;
           checkedDone = false; 
           
           while(checkedDone == false){
               if(newRow < N-1 && newRow >= 0){
                   if(board[newRow+1][col].getCurrentGamePiece() == piece.getCurrentGamePiece())// dark block
                       countDown++;
                   if(board[newRow+1][col].getCurrentGamePiece() == coveredPiece.getCurrentGamePiece())// covered block
                       countDown++;
                   if(board[newRow+1][col].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block)// white block
                       checkedDone = true;
               }else
                   checkedDone = true;
               newRow++;
           }
   //******************COUNT LEFT*******
           newRow = row;
           newCol = col;
           checkedDone = false;
           
           while(checkedDone == false){
               if(newCol > 0 && newCol < N){
                   if(board[newRow][newCol-1].getCurrentGamePiece() == piece.getCurrentGamePiece()) // dark block
                       countLeft++;
                   if(board[newRow][newCol-1].getCurrentGamePiece() == coveredPiece.getCurrentGamePiece())// light block
                       countLeft++;
                   if(board[newRow][newCol-1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block)// white block
                       checkedDone = true;
               }else
                   checkedDone = true;
               newCol--;
           }
           
    //**********COUNT RIGHT*****
           newRow = row;
           newCol = col;
           checkedDone = false;
           
           while(checkedDone == false){
               if(newCol >= 0 && newCol < N-1){
                   if(board[newRow][newCol+1].getCurrentGamePiece() == piece.getCurrentGamePiece())// dark block 
                       countRight++;
                   if(board[newRow][newCol+1].getCurrentGamePiece() == coveredPiece.getCurrentGamePiece()) // covered block
                       countRight++;
                   if(board[newRow][newCol+1].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block)// white block
                       checkedDone = true;
               }else
                   checkedDone = true;
               newCol++;
           }
               
           /*System.out.println("countUp " + countUp);
           System.out.println("CountDown " + countDown);
           System.out.println("CountLeft " + countLeft);
           System.out.println("CountRight " + countRight);*/
           
           // now determine the rectangle....
           /*
           
                a---------------b
                |               |
                |               |
                |               |
                c---------------d
           */
          allocated a = new allocated();
          allocated b = new allocated();
          allocated c = new allocated();
          allocated d = new allocated();
          
          a.col = col - countLeft;
          a.row = row - countUp;
          
          b.col = col + countRight;
          b.row = row - countUp;
          
          c.col = col - countLeft;
          c.row = row + countDown;
          
          d.col = col + countRight;
          d.row = row +countDown;
         
          
          for(int q = a.row; q < c.row; q++){
             for(int p = a.col; p < b.col; p++){
                 
                 if(board[q][p].getCurrentGamePiece() == piece.getCurrentGamePiece()) // dark block 
                     cellCount++;
             }
          }
             

          System.out.println("cellCount = " + cellCount);

           
       return cellCount;
          
   }
   
   //~~~~~~~~~~~~~ validateSelectedPiece - selected piece must be one of the layers own pieces. 
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
       Iterator<Point> it = list.iterator();
       while(it.hasNext())
       {
           Point z = it.next();
           if(validPoint(z))
           {
               if(board[z.x][z.y].getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Dark)
               {
                   if(board[tmp.x][tmp.y].getCurrentGamePiece() != GamePieces.gamePieces.Player_B_Dark)
                   {
                       board[z.x][z.y].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Dark);
                       if(z.x != previous.x && z.y != previous.y)
                            takeOthers(new Point(z.x,z.y),tmp);
                   }
               }
               
               if(board[z.x][z.y].getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Dark)
               {
                   if(board[tmp.x][tmp.y].getCurrentGamePiece() != GamePieces.gamePieces.Player_A_Dark)
                   {
                       board[z.x][z.y].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Dark);
                        if(z.x != previous.x && z.y != previous.y)
                            takeOthers(new Point(z.x,z.y),tmp);
                   }
               }
           }
       }
   }
   
   public boolean validPoint(Point x)
   {
       if(x.x < N && x.x >= 0)
           if(x.y < N && x.y >= 0)
               return true;
       return false;
   }
   
   
}
