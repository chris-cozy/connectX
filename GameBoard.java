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

    public boolean checkIfFree(int c){
        //creates new boardposition at the top of the selected column and checks if that space is filled
        BoardPosition temp = new BoardPosition(maxRow,c);
        if ((whatsAtPos(temp) != ' ')){
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

    public boolean checkTie(){
        //goes through every column and makes sure there's not a win, then checks if all of the spaces are full
        for(int i = 0; i <= maxCol; i++){
            if( checkForWin(i)){ return false;}
        }
        for(int i = 0; i <= maxCol; i++) {
            if (checkIfFree(i)) { return false;}
        }
        return true;
    }

    public boolean checkVertWin(BoardPosition pos, char p){
        //new implementation
        //start with a count index of 1
        int count = 0;
        //temp board position at token placement
        int curRow = pos.getRow();
        int curCol = pos.getColumn();

        //count the number of consecutives below token and add to count index
        for(int below = curRow; below > -1; below--){
            BoardPosition temp = new BoardPosition(below, curCol);
            if(whatsAtPos(temp) != p){
                break;
            }else{
                count++;
            }
        }
        //if count index equals numToWin return true, else return false
        if (count >= numToWin){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkDiagWin(BoardPosition pos, char p){
        //check all right and left diagonal situations
        int c1 = 0;
        int c2 = 0;
        int row = pos.getRow();
        int col = pos.getColumn();

        //counts upper right
        while((row < maxRow + 1) && (col < maxCol + 1)){
            BoardPosition current = new BoardPosition(row,col);
            if((whatsAtPos(current) == p)){
                c1++;
                row++;
                col++;
            }else{
                break;
            }
        }

        row = pos.getRow();
        col = pos.getColumn();
        //counts lower left
        while((row > -1) && (col > -1)){
            BoardPosition current = new BoardPosition(row,col);
            if((whatsAtPos(current) == p)){
                c1++;
                row--;
                col--;
            }else{
                break;
            }
        }

        row = pos.getRow();
        col = pos.getColumn();
        //counts upper left
        while((row < maxRow + 1) && (col > -1)){
            BoardPosition current = new BoardPosition(row,col);
            if((whatsAtPos(current) == p)){
                c2++;
                row++;
                col--;
            }else{
                break;
            }
        }

        row = pos.getRow();
        col = pos.getColumn();
        //counts lower right
        while((row > -1) && (col < maxCol + 1)){
            BoardPosition current = new BoardPosition(row,col);
            if((whatsAtPos(current) == p)){
                c2++;
                row--;
                col++;
            }else{
                break;
            }
        }
        if((c1 > numToWin) || (c2 > numToWin)){
            return true;
        } else{
            return false;
        }
    }

    public boolean checkHorizWin(BoardPosition pos, char p){
        //new implementation
        //start with a count index of 1
        int count = -1;
        //temp board position at token placement
        int curRow = pos.getRow();
        int curCol = pos.getColumn();

        //count the number of consecutives to the right token and add to count index
        for(int right = curCol; right < maxCol; right++){
            BoardPosition temp = new BoardPosition(curRow, right);
            if(whatsAtPos(temp) != p){
                break;
            }else{
                count++;
            }
        }
        //count the number of consecutives to the left token and add to count index
        for(int left = curCol; left > -1; left--){
            BoardPosition temp = new BoardPosition(curRow, left);
            if(whatsAtPos(temp) != p){
                break;
            }else{
                count++;
            }
        }

        //if count index equals numToWin return true, else return false
        if (count >= numToWin){
            return true;
        }else{
            return false;
        }
    }

    public char whatsAtPos(BoardPosition pos){
        //returns whatever is in that array position
        char current = board[pos.getRow()][pos.getColumn()];
        return current;
    }

}
