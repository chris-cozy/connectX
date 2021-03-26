package cpsc2150.extendedConnectX;

public abstract class AbsGameBoard implements IGameBoard{

    /**
     * Returns a fully formatted String which represents the GameBoard
     * @pre board != NULL
     * @post toString = board in string format
     * @return fully formatted string that displays the current game board
     */
    @Override
    public String toString(){
        String toString = "|";
        for(int i = 0; i <= getNumColumns(); i++){
            if( i < 10){
                toString += " " + i + "|";
            }else{
                toString += i + "|";
            }

        }
        toString += "\n";

        //putting player pieces on the board
        for(int i = getNumRows(); i >= 0; i--){
            for(int j = 0; j <= getNumColumns(); j++){
                BoardPosition temp = new BoardPosition(i,j);
                toString += "| " + whatsAtPos(temp);
            }
            toString += "|\n";
        }
        return toString;
    }
}
