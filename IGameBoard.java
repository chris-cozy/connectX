package cpsc2150.extendedConnectX;

public interface IGameBoard {

    /**
     * Returns the number of rows in the GameBoard
     * @param
     * @pre
     * @post none
     * @return getNumRows = number of rows in Gameboard
     */
    public int getNumRows();
    /**
     * Returns the number of columns in the GameBoard
     * @param
     * @pre
     * @post none
     * @return getNumRows = number of columns in Gameboard
     */
    public int getNumColumns();
    /**
     * Returns the number of tokens in a row needed to win the game, specified by the user
     * @param
     * @pre
     * @post none
     * @return getNumToWin = numToWin
     */
    public int getNumToWin();

    /**
     * Checks whether a column can accept another token, or if it is full
     * @param c
     * @pre c >= 0
     *      c < maxCol
     * @post none
     * @return true if column can accept another token, false otherwise
     */
    default boolean checkIfFree(int c){
        //creates new boardposition at the top of the selected column and checks if that space is filled
        BoardPosition temp = new BoardPosition(getNumRows(),c);
        if ((whatsAtPos(temp) != ' ')){
            return false;
        }
        return true;
    }
    /**
     * Checks if the last token placed (in column c) results in a player win
     * @param c
     * @pre c >= 0
     *      c < maxCol
     * @post none
     * @return true if last token placed results in a player win, false otherwise
     */
    default boolean checkForWin(int c){
        //cycles through column from the top to find the first filled spot (last placed token) then checks if that resulted in a win
        for (int j = getNumRows(); j > -1; j--){
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

    /**
     * Checks if the GameBoard results in a tie game
     * @pre none
     * @post none
     * @return true if gameboard results in a tie game, false otherwise
     */
    default boolean checkTie(){
        //goes through every column and makes sure there's not a win, then checks if all of the spaces are full
        for(int i = 0; i <= getNumColumns(); i++){
            if( checkForWin(i)){ return false;}
        }
        for(int i = 0; i <= getNumColumns(); i++) {
            if (checkIfFree(i)) { return false;}
        }
        return true;
    }

    /**
     * Places he correct player token in column c
     * @param p
     * @param c
     * @pre c >= 0, c < maxCol
     *      p != null
     * @post token was placed
     * @return none
     */
    public void placeToken(char p, int c);

    /**
     * Checks if last token placed results in a horizontal win
     * @param pos
     * @param p
     * @pre p != null
     *      pos != NULL
     * @post none
     * @return true if last token placed results in a horizontal win, false otherwise
     */
    default boolean checkHorizWin(BoardPosition pos, char p){
        //new implementation
        //start with a count index of 1
        int count = -1;
        //temp board position at token placement
        int curRow = pos.getRow();
        int curCol = pos.getColumn();

        //count the number of consecutive tokens to the right token and add to count index
        for(int right = curCol; right < getNumColumns(); right++){
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
        if (count >= getNumToWin()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Checks if last token placed results in a vertical win
     * @param pos
     * @param p
     * @pos p != null
     *      pos != NULL
     * @post none
     * @return true if last token placed results in a vertical win, false otherwise
     */
    default boolean checkVertWin(BoardPosition pos, char p){
        //new implementation
        //start with a count index of 1
        int count = 0;
        //temp board position at token placement
        int curRow = pos.getRow();
        int curCol = pos.getColumn();

        //count the number of consecutive tokens below token and add to count index
        for(int below = curRow; below > -1; below--){
            BoardPosition temp = new BoardPosition(below, curCol);
            if(whatsAtPos(temp) != p){
                break;
            }else{
                count++;
            }
        }
        //if count index equals numToWin return true, else return false
        if (count >= getNumToWin()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Checks if last token placed results in a diagonal win
     * @param pos
     * @pre p != null
     *      pos != NULL
     * @post none
     * @return true if last token placed results in a diagonal win, false otherwise
     */
    default boolean checkDiagWin(BoardPosition pos, char p){
        //check all right and left diagonal situations
        int c1 = 0;
        int c2 = 0;
        int row = pos.getRow();
        int col = pos.getColumn();

        //counts upper right
        while((row < getNumRows() + 1) && (col < getNumColumns() + 1)){
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
        while((row < getNumRows() + 1) && (col > -1)){
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
        while((row > -1) && (col < getNumColumns() + 1)){
            BoardPosition current = new BoardPosition(row,col);
            if((whatsAtPos(current) == p)){
                c2++;
                row--;
                col++;
            }else{
                break;
            }
        }
        if((c1 > getNumToWin()) || (c2 > getNumToWin())){
            return true;
        } else{
            return false;
        }
    }

    /**
     * Returns the char that is in the BoardPosition pos, or ' ' if there is none
     * @param pos
     * @pre pos != NULL
     * @post whatsAtPos = char at the position
     * @return char that is in position pos
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * Checks if player is at BoardPosition pos
     * @param pos
     * @param player
     * @pre player != NULL
     *      pos != NULL
     *      board != NULL
     * @post none
     * @return true if the player is at that position, false otherwise
     */
    default boolean isPlayerAtPos(BoardPosition pos, char player){
        if(whatsAtPos(pos) == player){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a fully formatted String which represents the GameBoard
     * @pre board != NULL
     * @post toString = board in string format
     * @return fully formatted string that displays the current game board
     */
    @Override
    public String toString();

}
