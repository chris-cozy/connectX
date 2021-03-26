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
    public boolean checkIfFree(int c);
    /**
     * Checks if the last token placed (in column c) results in a player win
     * @param c
     * @pre c >= 0
     *      c < maxCol
     * @post none
     * @return true if last token placed results in a player win, false otherwise
     */
    public boolean checkForWin(int c);

    /**
     * Checks if the GameBoard results in a tie game
     * @pre none
     * @post none
     * @return true if gameboard results in a tie game, false otherwise
     */
    public boolean checkTie();

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
    public boolean checkHorizWin(BoardPosition pos, char p);

    /**
     * Checks if last token placed results in a vertical win
     * @param pos
     * @param p
     * @pos p != null
     *      pos != NULL
     * @post none
     * @return true if last token placed results in a vertical win, false otherwise
     */
    public boolean checkVertWin(BoardPosition pos, char p);

    /**
     * Checks if last token placed results in a diagonal win
     * @param pos
     * @pre p != null
     *      pos != NULL
     * @post none
     * @return true if last token placed results in a diagonal win, false otherwise
     */
    public boolean checkDiagWin(BoardPosition pos, char p);

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
