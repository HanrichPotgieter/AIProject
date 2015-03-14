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
        Board board =  new Board(); 
        int sizeOfN = 0;
       
        Scanner in = new Scanner(System.in);
        
        // Create the board:
        System.out.println("Please provide a number N to create NxN grid:");
        System.out.println("****Note N needs to be an even number larger than 8****");
        sizeOfN = in.nextInt();
       
        
        while(true){
            
            if(board.validateBoardSize(sizeOfN))
            {
               System.out.println("Generating board.....");
               return;
            }
            else{
                System.out.println("ERROR the number of n must be even and latger than 8");
                System.out.println("Please provide a new numberN to create the grid:");
                sizeOfN = in.nextInt();
            }
    }
}
        
       
    }
    

