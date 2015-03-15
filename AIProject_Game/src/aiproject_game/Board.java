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
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                           change = true;
                       }
                       else if(above.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light && infront.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light)
                       {
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                           change = true;
                       }
                   }
                   
                   if(below!=null && infront!=null)
                   {
                       if(below.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light && infront.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light)
                       {
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                           change = true;
                       }
                       else if(below.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light && infront.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light)
                       {
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                           change = true;
                       }
                   }
                   
                   if(below!=null && before!=null)
                   {
                       if(below.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light && before.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light)
                       {
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                           change = true;
                       }
                       else if(below.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light && before.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light)
                       {
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
                           change = true;
                       }
                   }
                   
                   if(above!=null && before!=null)
                   {
                       if(above.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light && before.getCurrentGamePiece() == GamePieces.gamePieces.Player_A_Light)
                       {
                           board[i][j].setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
                           change = true;
                       }
                       else if(above.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light && before.getCurrentGamePiece() == GamePieces.gamePieces.Player_B_Light)
                       {
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
       System.out.println("From " + from.x +"."+ from.y +"|| To "+ to.x +"."+ to.y);
       clearSpace();
       updateGUI();
       return false;
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
   
   public void updateGUI()
   {
       gui.update(id);
   }
   
   
   
   public int calculateAllowedNumberOfMoves(int row, int col, GamePieces piece){
       // first validate the selected block 
       boolean validated = validateSelectedPiece(row, col);
       int cellCount = 0;
       int tempVal = 0;
       boolean checkedDone = false;
       GamePieces coveredPiece =  new GamePieces();
       
      if(validated){
         // determine what the covered piece must be 
         if(gameState.getGameState() == GameState.states.Player_A_Turn)
           coveredPiece.setCurrentGamePiece(GamePieces.gamePieces.Player_A_Light);
         else
             if(gameState.getGameState() == GameState.states.Player_B_Turn)
                coveredPiece.setCurrentGamePiece(GamePieces.gamePieces.Player_B_Light);
      
           // first check up.... until you reach an empty block 
          while(checkedDone == false){
              
              tempVal++;
              if(row-(tempVal+1) != 0){
                  if(board[row-tempVal][col] != null){
                      if(board[row-tempVal][col].getCurrentGamePiece() == coveredPiece.getCurrentGamePiece()){}// do nothing
                      if(board[row-tempVal][col].getCurrentGamePiece() == piece.getCurrentGamePiece())// increase cellCount coz cell has been found
                          cellCount++;
                      if(board[row-tempVal][col].getCurrentGamePiece() == GamePieces.gamePieces.Empty_Block)// then exit while loop reached end of the covered area
                          checkedDone = true;
                          
                  }
                  
              
                          
                  
              
              
              
          
       }
       else{} // error block selected is not valid.....
          }}
       return 0;
          
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
   
}
