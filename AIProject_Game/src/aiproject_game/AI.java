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
/**
 *
 * @author hanrich
 */
public class AI extends Thread {

    
    //~~~~~~~~ class variables
  
    protected Integer heuristicVal;
    public  Board board = null;
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
       
        try{
        Thread.sleep(1000);
        }
        catch(Exception e)
        {
                
        }
        ArrayList<Move> moves = board.getPossibleMoves();
        GamePieces[][] newBoardArray  = board.getBoard();
        Board newBoard = new Board(board.N);
        newBoard.validateBoardSize(board.N);
        board.setBoard(newBoardArray);
        
        generateTree(true,false,newBoard,2,0);
        
        System.out.println("AI has done its calculation");
    }
    
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
            Board newBoard = new Board(b.N);
            newBoard.validateBoardSize(b.N);
            b.setBoard(newBoardArray);
            b.move(move.from, move.to);
            move.heuristicVal += newBoard.hueristicCellCount();
            System.out.println(move.heuristicVal);
            if(max)
                move.heuristicVal += generateTree(false,true,newBoard,plyDepth,currentDepth+1);
            if(min)
                move.heuristicVal += generateTree(true, false,newBoard,plyDepth,currentDepth+1);            
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
    
    public Integer getMax(ArrayList<Move> list)
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
    public Integer getMin(ArrayList<Move> list)
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
