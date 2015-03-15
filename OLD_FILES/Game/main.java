/*
 *  COS 314 Project 1 
 *  Authors:
 *  Isabel Nel : 13070305
 *  Hanrich Potgieter : 
 *
 */



import java.util.Scanner;


public class main {

   
    public static void main(String[] args) {
        
        //~~~~~~~~~~~~~~ Class variables 
        Board board;
        int sizeOfN = 0;
        int initialCells = 0;
        Game game;
       
        Scanner in = new Scanner(System.in);
        
        // Create the board:
        System.out.println("*****************************************************");
        System.out.println("                 WELCOME TO CELL WARS....");
        System.out.println("*****************************************************");
        System.out.println("Please provide the number of initial cells for each player: ");
        initialCells = in.nextInt();
        System.out.println("Please provide a number N to create NxN grid:");
        System.out.println("****Note N needs to be an even number larger than 8****");
        
        sizeOfN = in.nextInt();
        board =  new Board(initialCells); 
        
        while(true){
            
            if(board.validateBoardSize(sizeOfN))
            {
               return;
            }
            else{
                System.out.println("ERROR the number of n must be even and latger than 8");
                System.out.println("Please provide a new numberN to create the grid:");
                sizeOfN = in.nextInt();
            }
        }// if it exits this while then the grid value n has been validated and the board
         // has been generated so now we can just start the game
        
}
        
       
    }
    

