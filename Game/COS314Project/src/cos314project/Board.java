/*
 *  COS 314 Project 1 
 *  Authors:
 *  Isabel Nel : 13070305
 *  Hanrich Potgieter : 
 *
 */

package cos314project;


public class Board {
    
    //~~~~~~~~~~ Class variables 
    public int N; // the size of the NxN matrix.
    public boolean validated; // this value will indicate if the N is valid
    public GamePieces[][] board; // this matrix will hold the whole game grid and which pieces are in which blocks
    public GamePieces temp;
    
    //~~~~~~~~ Class constructor;
    Board(int n){
    
     N = 0;
     
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
        
        // Initialize whole board to emptyBlocks
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                
                board[i][j] = new GamePieces();
            }
                
        }
            
        
       
    }
    
    private void printBoard(){
        System.out.println("Printing the Grid");
        
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                
            }
        }
            
    }
   
}
