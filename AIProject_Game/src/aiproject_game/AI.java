/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    Board board = null;
    ArrayList<Move> moves;
    
    public AI(Board board)
    {
        this.board = board; 
    }
    
    
    
    public Integer HeuristicCount()
    {
        return 0;
    }
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
    @Override
    public void run()
    {
        moves = board.getPossibleMoves();
        Iterator<Move> it  = moves.iterator();
        while(it.hasNext()){
            Move move = it.next();
            GamePieces[][] newBoardArray  = board.getBoard();
            Board newBoard = new Board(board.N);
            board.setBoard(newBoardArray);
            //Get heuristic on baord
            //----------------------
        }
        
    }
    
}
