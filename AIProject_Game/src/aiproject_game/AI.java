/*
 *  COS 314 Project 1 
 *  Authors:
 *  Isabel Nel : 13070305
 *  Hanrich Potgieter :  12287343
 *
 */

package aiproject_game;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.awt.Point;

/**
 *
 * @author hanrich
 */
public class AI extends Thread {

    
    //~~~~~~~~ class variables
  
    protected Integer heuristicVal;
    public  Board board = null;
    Integer Ply = 1;
    public Boolean playerVSAI = false;
    //ArrayList<Move> moves;
    
    //~~~~~~ class constructor
    public AI(Board board)
    {
        this.board = board; 
        
    }
    
    
    /*
    public void randomAI()
    {
        Scanner a = new Scanner(System.in);
        System.out.println("Let AI move..");
        a.nextLine();
        
        while(true)
        {   
            moves = board.getPossibleMoves();
            Integer maximum = moves.size()-1;
            Integer minimum = 0;
            
            Random rn = new Random();
            int n = maximum - minimum + 1;
            int i = rn.nextInt() % n;
            Move move = moves.get(Math.abs(i));
            if(move != null)
            {
                board.move(move.from,move.to);
            }
            try{
            Thread.sleep(100);
            }
            catch(Exception e)
            {
                
            }
        }
    }
    */
    @Override
    public void run()
    {
        while(true)
        {
            try{
            Thread.sleep(1000);
            }
            catch(Exception e)
            {
                
            }
            if(playerVSAI)
            {
                GameState tmp = new GameState();
                tmp.setGameState(GameState.states.Player_A_Turn);
                while(board.getGameState().getGameState() == tmp.getGameState())
                {
                    //System.out.println("Waiting for player to move");
                }
            }
            if(board.gameState.getGameState() == GameState.states.Player_A_Win)
            {
                System.out.println("Player A has won!");
                break;
            }
            if(board.gameState.getGameState() == GameState.states.Player_B_Win)
            {
                 System.out.println("Player B has won!");
                 break;
            }
            ArrayList<Move> moves = board.getPossibleMoves();
            GamePieces[][] newBoardArray  = board.getBoard();
            Board newBoard = new Board(board.numInitialCells);
            newBoard.validateBoardSize(board.N);
            newBoard.setGameState(board.getGameState());
            newBoard.setBoard(newBoardArray);
            newBoard.gui = null;
            generateTree(true,false,newBoard,Ply,0);

            System.out.println("AI has done its calculation");
            Move tmp = nextMove;
            this.board.move(nextMove.from,nextMove.to,false);
            
            //this.board.printBoard();
            //this.board.updateGUI();
        }

    }
    public Move nextMove;
    public synchronized Integer generateTree(Boolean max,Boolean min,Board b, int plyDepth,int currentDepth)
    {
        if(plyDepth <= currentDepth)
        {
            return 0;
        }
        //System.out.println("Tree a depth :" + currentDepth);
        //b.printBoard();
        ArrayList<Move> moves = b.getPossibleMoves();
        //System.out.println("Moves size:"+moves.size());
        Iterator<Move> it  = moves.iterator();
        
        while(it.hasNext()){
            Move move = it.next();
            GamePieces[][] newBoardArray  = b.getBoard();
            Board newBoard = new Board(b.numInitialCells);
            //newBoard.setGameState(b.getGameState());
            newBoard.validateBoardSize(b.N);
            
            newBoard.setBoard(newBoardArray);
            newBoard.gui = null;
            //newBoard.printBoard();
            
            newBoard.move(move.from, move.to,false);      
            move.heuristicVal += newBoard.hueristicCellCount(move.to) + newBoard.heuristicDistanceCount();
            System.out.println(move.heuristicVal);
            if(max){
                move.heuristicVal += generateTree(false,true,newBoard,plyDepth,currentDepth+1);
            }
            if(min){
                move.heuristicVal += generateTree(true,false,newBoard,plyDepth,currentDepth+1); 
            }
           
        }
        if(currentDepth == 0)
        {
            nextMove = getMaxMove(moves);
            return 0;
        }
        if(max)
        {
            return getMax(moves);
        }
        else if(min)
        {
            return getMin(moves);
        }
        //return null;
        return null;
    }
    public synchronized Move getMaxMove(ArrayList<Move> list)
    {
        Integer max = -1000000;
        Move x = null;
        Iterator<Move> it  = list.iterator();
        while(it.hasNext())
        {
            Move move = it.next();
            if(move.heuristicVal > max)
            {
                max = move.heuristicVal;
                x = new Move();
                x.from = new Point(move.from.x,move.from.y);
                x.to = new Point(move.to.x,move.to.y);
            }
        }
        return x;
    }
    
    public synchronized Integer  getMax(ArrayList<Move> list)
    {
        Integer max = -1000000;
        Iterator<Move> it  = list.iterator();
        while(it.hasNext())
        {
            Move move = it.next();
            if(move.heuristicVal > max)
                max = move.heuristicVal;
        }
        return max;
    }
    public synchronized Integer getMin(ArrayList<Move> list)
    {
        Integer min = 1000000;
        Iterator<Move> it  = list.iterator();
        while(it.hasNext())
        {
            Move move = it.next();
            if(move.heuristicVal < min)
                min = move.heuristicVal;
        }
        return min;
    }
    
}
