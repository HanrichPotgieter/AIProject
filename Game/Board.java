/*
 *  COS 314 Project 1 
 *  Authors:
 *  Isabel Nel : 13070305
 *  Hanrich Potgieter : 
 *
 */




public class Board {
    
    //~~~~~~~~~~ Class variables 
    public int N; // the size of the NxN matrix.
    public boolean validated; // this value will indicate if the N is valid
    public GamePieces[][] board; // this matrix will hold the whole game grid and which pieces are in which blocks
    public GamePieces temp;
    //Game Pieces
    int EMPTY = 0;
    
    //~~~~~~~~ Class constructor;
    Board(){
    
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
                System.out.print("[");
                if(board[i][j].getCurrentGamePiece().compareTo(GamePieces.gamePieces.Empty_Block) == 0){
                    System.out.print("**");
                }else
                    if(board[i][j].getCurrentGamePiece().compareTo(GamePieces.gamePieces.Player_A_Dark) == 0){
                    System.out.print("aD");
                }else
                    if(board[i][j].getCurrentGamePiece().compareTo(GamePieces.gamePieces.Player_A_Light) == 0){
                    System.out.print("aL");
                }else
                    if(board[i][j].getCurrentGamePiece().compareTo(GamePieces.gamePieces.Player_B_Dark) == 0){
                    System.out.print("bD");
                }else
                   if(board[i][j].getCurrentGamePiece().compareTo(GamePieces.gamePieces.Player_B_Light) == 0){
                    System.out.print("bL");
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
   
   public GamePieces[][] getBoard(){
       return board;
   }
   
}
