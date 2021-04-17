package cpsc2150.extendedConnectX;

import java.util.HashMap;
import java.util.*;

public class GameBoardMem extends AbsGameBoard implements IGameBoard{
    /**
     *
     * @invariant c >= 0
     * @invariant c < maxCol
     * @invariant p != NULL
     * @invariant pos != NULL
     */
    private HashMap<Character, List<BoardPosition>>board;
    private char currentP;

    private int maxRow;
    private int maxCol;
    private int numToWin;
    private char [] players = new char[10];
    private int playerNum;

    /**
     * Default Constructor
     * @param int number of rows
     *        int number of columns
     *        int number to win
     *        char array of player characters
     *        int number of players
     * @pre has not been called yet
     * @post board = new Hashmap<Character, List<BoardPosition>>
     * @return none
     */
    public GameBoardMem(int row, int col, int win, char[] people, int numPlayers){
        players = people;
        playerNum = numPlayers;
        maxRow = row;
        maxCol = col;
        numToWin = win;
        //Create empty map to represent the board
        board = new HashMap<Character, List<BoardPosition>>();
    }



    public int getNumRows(){
        return maxRow;
    }
    public int getNumColumns(){ return maxCol; }
    public int getNumToWin(){
        return numToWin;
    }

    public boolean checkIfFree(int c){
        //creates new boardposition at the top of the selected column and checks if that space is filled
        BoardPosition temp = new BoardPosition(maxRow,c);
        if((whatsAtPos(temp) != ' ')){
            return false;
        }
        return true;
    }

    public boolean checkForWin(int c){
        //cycles through column from the top to find the first filled spot (last placed token) then checks if that resulted in a win
        for (int j = maxRow; j > -1; j--){
            BoardPosition current = new BoardPosition(j,c);
            if(whatsAtPos(current) != ' '){
                char player = whatsAtPos(current);
                if(checkHorizWin(current, player)){
                    return true;
                } else if(checkVertWin(current, player)){
                    return true;
                } else if(checkDiagWin(current, player)){
                    return true;
                } else{
                    return false;
                }
            }
        }
        return false;
    }

    public void placeToken(char p, int c){
        if(!board.isEmpty()){
            if(checkIfFree(c)){
                //loops through the free column starting from the bottom to find the first available space
                for(int j = 0; j <= maxRow; j++){
                    //innocent until proven guilty
                    boolean free = true;
                    //creates a temp position for space being evaluated
                    BoardPosition current = new BoardPosition(j,c);
                    //could use get for each player index, set their list of positions equal to a temp list, then call list.contains()
                    for(int i = 0; i < playerNum; i++){
                        //sets a temp list to the list of board positions occupied by that player
                        List<BoardPosition>temp = board.get(players[i]);
                        //if a player is occupied that position, exit the search and move to the next position
                        if (temp != null){
                            if(temp.contains(current)){
                                free = false;
                                break;
                            }
                        }
                    }
                    if(free){
                        //if no key for character in the map, create one and place value
                        if(!board.containsKey(p)){
                            List<BoardPosition>x = new ArrayList<BoardPosition>();
                            board.put(p, x);
                            List<BoardPosition>temp = board.get(p);
                            temp.add(current);
                            System.out.print("\nBoard is: " + board + "\n");
                        }else{
                            List<BoardPosition>temp = board.get(p);
                            temp.add(current);
                        }
                        return;
                    }
                    //If not free, keep looping
                }
            }
        }
        BoardPosition current = new BoardPosition(0,c);
        List<BoardPosition>x = new ArrayList<BoardPosition>();
        x.add(current);
        board.put(p, x);
    }

    public boolean checkTie(){
        //goes through every column and makes sure there's not a win, then checks if all of the spaces are full
        for(int i = 0; i <= maxCol; i++){
            if(checkForWin(i)){ return false;}
        }
        for(int i = 0; i <= maxCol; i++) {
            if (checkIfFree(i)) { return false;}
        }
        return true;
    }

    public char whatsAtPos(BoardPosition pos){
        //cycle through player array like in place token and find the list with the position, and return the key with it
        for(int i = 0; i < playerNum; i++){
            if (board.containsKey(players[i])){
                //sets a temp list to the list of board positions occupied by that player
                List<BoardPosition>temp = board.get(players[i]);
                //if a player is occupied that position, exit the search and move to the next position
                if(temp.contains(pos)){
                    return players[i];
                }
            }

        }
        return ' ';
    }

    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player){
        //finds the players key and check for the position in it's value
        if(board.containsKey(player)){
            List<BoardPosition>temp = board.get(player);
            if(temp.contains(pos)){
                return true;
            }
        }
        return false;
    }
}
