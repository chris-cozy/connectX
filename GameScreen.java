package cpsc2150.extendedConnectX;
import java.util.*;
//author: Christopher Sanders Jr, class: 2150, section: 001, date: 2/7/2021

public class GameScreen {
    /**
     *
     * @invariant c >= 0
     * @invariant c < maxColChoice
     */

    private int currentCol;
    private boolean gameOver;
    private boolean playing;

    //default values for these, will be changed with user input
    private int maxColChoice = 8;
    private int maxRowChoice = 5;
    private int numToWin = 5;
    private int playerNum = 2;

    //array of player tokens
    private char[] players = new char[10];
    private char toke;
    private char Toke;
    private char imp;
    private int curTurn;

    /**
     * Checks if the game is over by searching the conditions
     * @param c
     * @pre c >= 0
     *      c < maxColChoice
     * @post
     * @return true if game is over, false if not
     */
    private boolean checkGameOver(IGameBoard play, int c){
        if(play.checkForWin(c)){
            return true;
        } else if(play.checkTie()){
            System.out.print("This game has ended in a tie.\n");
            return true;
        } else { return false;}
    }


    public static void main(String[] args){
        //creates new gamescreen instance
        GameScreen gS = new GameScreen();

        gS.playing = true;
        //while the players are playing
        while(gS.playing){
            //Takes in the new row and columns choices and assigns them to the corresponding variables
            System.out.print("Beginning a new game.\nChoose the row size:\n");
            Scanner scanner = new Scanner(System.in);
            gS.maxRowChoice = scanner.nextInt();
            while((gS.maxRowChoice > 100) || (gS.maxRowChoice < 2)){
                System.out.print("Invalid row choice, must be between 2 and 100. Enter again:\n");
                gS.maxRowChoice = scanner.nextInt();
            }
            System.out.print("\nChoose the column size:\n");
            gS.maxColChoice = scanner.nextInt();
            while((gS.maxColChoice > 100) || (gS.maxColChoice < 2)){
                System.out.print("Invalid column choice, must be between 2 and 100. Enter again:\n");
                gS.maxColChoice = scanner.nextInt();
            }

            //Takes in the desired number of players and stores it in the corresponding variable
            System.out.print("\nChoose the number of players (2 - 10):\n");
            gS.playerNum = scanner.nextInt();
            while((gS.playerNum > 10) || (gS.playerNum < 2) || (gS.playerNum > (gS.maxColChoice * gS.maxRowChoice))){
                System.out.print("Invalid number of players, must be between 2 and 10. Enter again:\n");
                gS.playerNum = scanner.nextInt();
            }

            //Players choosing their char and storing in char array
            for(int i = 1; i <= gS.playerNum; i++){
                System.out.print("Player " + i + ", choose your character:\n");
                gS.toke = scanner.next().charAt(0);
                gS.Toke = Character.toUpperCase(gS.toke);

                //cycles through array, comparing chosen token to already present tokens to ensure there are no duplicates
                for(int j = 0; j < gS.playerNum; j++){
                    int comp = Character.compare(gS.Toke, gS.players[j]);
                    //if present, ask for new choice and reset j
                    if(comp == 0){
                        System.out.print("That Token has already been assigned. Choose another:\n");
                        gS.toke = scanner.next().charAt(0);
                        gS.Toke = Character.toUpperCase(gS.toke);
                        j = -1;
                    }
                }
                //Once confirmed that token is unique, add it to the token array
                gS.players[i -1] = gS.Toke;
            }

            //Choosing the number of tokens in a row required to win
            System.out.print("\nChoose the number of tokens in a row required to win:\n");
            gS.numToWin = scanner.nextInt();
            while((gS.numToWin > 100) || (gS.numToWin < 2)){
                System.out.print("Invalid choice, must be between 2 and 25. Enter again:\n");
                gS.numToWin = scanner.nextInt();
            }
            //If the number to win is greater than both the row and column sizes it is unattainable
            while((gS.numToWin > gS.maxRowChoice) && (gS.numToWin > gS.maxColChoice)){
                System.out.print("Invalid choice, must be lesser than the row and column choices. Enter again:\n");
                gS.numToWin = scanner.nextInt();
            }

            //Takes in whether the user wants a fast or memory efficient implementation
            System.out.print("\nChoose whether you want a fast implementation (F), or a memory efficient implementation (M):\n");
            gS.imp = scanner.next().charAt(0);
            gS.imp = Character.toUpperCase(gS.imp);
            while((gS.imp != 'M') && (gS.imp != 'F')){
                System.out.print("Invalid response. Enter either F or M\n");
                gS.imp = scanner.next().charAt(0);
            }

            //constructor responses
            IGameBoard play = new GameBoard(gS.maxRowChoice, gS.maxColChoice, gS.numToWin);

            if(gS.imp == 'M'){
                System.out.print("Memory Efficient implementation...\n");
                play = new GameBoardMem(gS.maxRowChoice, gS.maxColChoice, gS.numToWin);
            }



            //while boolean playing != false
            gS.gameOver = false;

            System.out.print("New Game! May the best strategist win\n");
            //While the game is continuing
            while(!gS.gameOver) {

                //rotate through players, asking for their column selection, after each turn:
                //checkIfFree, placeToken, checkGameOver
                //create a while/for loop that will iterate through each set of turns
                for(int turn = 0; turn < gS.playerNum; turn ++){
                    gS.curTurn = turn;
                    System.out.print("Player " + gS.players[turn] + ", enter desired column:\n");
                    char p = gS.players[turn];
                    gS.currentCol = scanner.nextInt();

                    //check if column is valid
                    while((gS.currentCol > gS.maxColChoice) || (gS.currentCol < 0)){
                        System.out.print("Player " + gS.players[turn] + ", Invalid Column! Try again.\n");
                        gS.currentCol = scanner.nextInt();
                    }

                    //check if desired column is free, if so place token, if not ask for new choice
                    while(!play.checkIfFree(gS.currentCol)) {
                        System.out.print("Column is full! Enter another column.\n");
                        gS.currentCol = scanner.nextInt();
                    }
                    play.placeToken(p,gS.currentCol);

                    //Print the current gameboard
                    String gameBoard = play.toString();
                    System.out.print(gameBoard);

                    //after token is placed, check if it resulted in the game being over
                    gS.gameOver = gS.checkGameOver(play, gS.currentCol);
                    if(gS.gameOver){
                        break;
                    }
                }
            }

            //Once checkGameOver is true, ask to play again
            // if play again is yes, overwrite the characters in th player array and restart the process
            //if play again is no, playing = false
            System.out.print("Congratulations Player " + gS.players[gS.curTurn] + "! You have won!\n");
            for(int j = 0; j < gS.playerNum; j++){
                gS.players[j] = ' ';
            }
            System.out.print("Would you like to play again? Y or N\n");
            char playAgain = scanner.next().charAt(0);
            playAgain = Character.toUpperCase(playAgain);
            while((playAgain != 'Y') && (playAgain != 'N')){
                System.out.print("Invalid response. Enter either Y or N\n");
                playAgain = scanner.next().charAt(0);
            }
            if((playAgain == 'N')){
                System.out.print("Goodbye! Exiting program...\n");
                gS.playing = false;
            }

        }
    }
}
