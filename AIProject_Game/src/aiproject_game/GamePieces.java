/*
 *  COS 314 Project 1 
 *  Authors:
 *  Isabel Nel : 13070305
 *  Hanrich Potgieter : 
 *
 */

package aiproject_game;

public class GamePieces {
    
    //~~~~~~~~~~ Class variables
    public enum gamePieces{
                            Empty_Block,
                            Player_A_Dark,
                            Player_A_Light,
                            Player_B_Dark,
                            Player_B_Light,
    }
    private gamePieces currentGamePiece;
    
    
    // ~~~~~~~  Class Constructor
    GamePieces(){
        currentGamePiece = gamePieces.Empty_Block;
        //System.out.println("*****" + gamePieces.Empty_Block);
    }
    
    //~~~~~~~~~  getCutterntGamePiece
    public gamePieces getCurrentGamePiece(){
        return currentGamePiece;
    }
    
    //~~~~~~~~ setCurrentGamePiece 
    public void setCurrentGamePiece(gamePieces piece){
        currentGamePiece = piece;
    }
    
}
