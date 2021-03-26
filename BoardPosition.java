package cpsc2150.extendedConnectX;
//author: Christopher Sanders Jr, class: 2150, section: 001, date: 2/7/2021
public class BoardPosition {
    /**
     *
     * @invariant x >= 0
     * @invariant x < 6
     * @invariant y >= 0
     * @invariant y < 9
     */

    private int row;
    private int column;

    /**
     * The constructor, takes in an int for row and an int for column and sets the private variables
     * @param x
     * @param y
     * @pre x >= 0, x < 6
     *      y >= 0, y < 9
     * @post private fields of the BoardPosition are set
     * @return none
     */
    public BoardPosition(int x, int y){
        this.row = x;
        this.column = y;
    }

    /**
     * Gets the row
     * @pre Constructor has been called
     * @post getRow = row
     * @return the row
     */
    public int getRow(){
        return row;
    }

    /**
     * Gets the column
     * @pre Constructor has been called
     * @post getColumn = column
     * @return the column
     */
    public int getColumn(){
        return column;
    }

    /**
     * Checks if two BoardPositions are equal, overriding current equals method
     * @param current
     * @pre current != NULL
     * @post none
     * @return true if equal, false otherwise
     */

    @Override
    public boolean equals(Object obj){
        final BoardPosition current = (BoardPosition) obj;
        if((this.getRow() != current.getRow()) || (this.getColumn() != current.getColumn())){
            return false;
        }
        return true;
    }

    /**
     * Prints a string of the BoardPosition in the format "<row>,<column>"
     * @pre Constructor has been called
     * @post toString = row,column
     * @return String containing the row and column of the BoardPosition
     */
    @Override
    public String toString(){
        //System.out.print("%d, %d\n", getRow(), getColumn());
        String toString = "(" + getRow() + ", " + getColumn() + ")";
        return toString;
    }
}
