package cpsc2150.extendedConnectX;

//author: Christopher Sanders Jr, class: 2150, section: 001, date: 2/7/2021
public class GameBoard extends AbsGameBoard implements IGameBoard{


    /**
     *
     * @invariant c >= 0
     * @invariant c < maxCol
     * @invariant p != NULL
     * @invariant pos != NULL
     */
    private char[][] board;
    private char currentP;

    private int maxRow;
    private int maxCol;
    private int numToWin;

    /**
     * Default Constructor
     * @pre has not been called yet
     * @post board = new char[][]
     * @return none
     */
    public GameBoard(int row, int col, int win){
        maxRow = row;
        maxCol = col;
        numToWin = win;
        board = new char[maxRow + 1][maxCol + 1];
        for (int r = 0; r <= maxRow; r++)
        {
            for (int c = 0; c <= maxCol; c++)
            {
                board[r][c] = ' ';
            }
        }
    }

    public int getNumRows(){
        return maxRow;
    }
    public int getNumColumns(){ return maxCol; }
    public int getNumToWin(){
        return numToWin;
    }

    public void placeToken(char p, int c){
        //checks if row is free
        if(checkIfFree(c)){
            //cycles through the rows of the chosen column and places the token in the first empty spot
            for (int j = 0; j <= maxRow; j++){
                BoardPosition current = new BoardPosition(j,c);
                if(whatsAtPos(current) == ' '){
                    board[current.getRow()][current.getColumn()] = p;
                    currentP = p;
                    return;
                }
            }
        }
    }

    public char whatsAtPos(BoardPosition pos){
        //returns whatever is in that array position
        char current = board[pos.getRow()][pos.getColumn()];
        return current;
    }

}
