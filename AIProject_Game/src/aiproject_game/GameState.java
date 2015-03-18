/*
 *  COS 314 Project 1 
 *  Authors:
 *  Isabel Nel : 13070305
 *  Hanrich Potgieter :  12287343
 *
 */


package aiproject_game;

public class GameState {
    // ~~~~~~~~~~ Class variables 
    public enum states {
                       Player_A_Turn,
                       Player_B_Turn,
                       Player_A_Win,
                       Player_B_Win
    };
   private states currentState;
    
    //~~~~~~~~~~ Class constructor
    GameState() {
    currentState = states.Player_A_Turn;
    }
    
    //~~~~~~~~~ GetGameState will return the current game state 
    public states getGameState(){
        return currentState;
    }
    
    //~~~~~~~~~ setGameState will be used to set the state of the game
    public void setGameState(states newState){
        currentState = newState; 
    }
    
    public void swop()
    {
        if(currentState == states.Player_A_Turn)
        {
            currentState = states.Player_B_Turn;
        }
        else if(currentState == states.Player_B_Turn)
        {
            currentState = states.Player_A_Turn;
        }
    }
    
    
}
