/*
 *  COS 314 Project 1 
 *  Authors:
 *  Isabel Nel : 13070305
 *  Hanrich Potgieter :  12287343
 *
 */

package aiproject_game;

/**
 *
 * @author hanrich
 */
public class AI extends Thread {
    
    //~~~~~~~~ class variables
    public Board board = null;
    protected Integer heuristicVal;
    
    // ~~~~~~~~ class constructor 
    public AI(Board b){
        board = b;
        heuristicVal = null;
    }
    
    
    public Integer HeuristicCount()
    {
        heuristicVal = board.getPossibleMoves().size();
        return heuristicVal;
    }
    
    @Override
    public void run()
    {
        
    }
    
}
