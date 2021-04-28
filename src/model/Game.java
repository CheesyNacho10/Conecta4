/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Controller of the game logic
 * @author Nacho
 */
public class Game {
    
    private Cell[][] board;
    private GameState state;
    private int columns, rows;
    
    /**
     * Creates a game with a board of 7 rows x 8 columns empty and ready to be played
     */
    public Game() {
        board = new Cell[8][7];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
        state = GameState.PLAYING;
        
        columns = board.length;
        rows = board[0].length;
    }
    
    /**
     * Checks if a column has an empty Cell
     * @param c Column to be analysed
     * @return True if there are empty Cells
     */
    public boolean columnHasSpace(int c) {
        for (int i = 0; i < getRows(); i++) {
            if (getCell(c, i) == Cell.EMPTY) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the next row where the circle should go in that column
     * @param c Column desired
     * @return Next row index
     */
    public int getNextRow(int c) {
        int res = getRows() - 1;
        while(res >= 0 && getCell(c, res) != Cell.EMPTY){
            res--;
        }
        return res;
    }
    
    /**
     * Changes the Cell state that should change for a given column
     * and for the player that it's making its turn
     * @param c Column where the circle will go
     * @param p Player who adds the circle
     * @return 
     */
    public int addCircle(int c, boolean p) {
        int row = getNextRow(c);
        if (p) { setCell(Cell.PLAYER_ONE, c, row); }
        else { setCell(Cell.PLAYER_TWO, c, row); }
        state = checkGameState(c, row, p);
        return row;
    }
    
    /**
     * Gets the cell in the position CxR
     * @param c Column
     * @param r Row
     * @return Cell in CxR
     */
    public Cell getCell(int c, int r) { return board[c][r]; }
    
    /**
     * Sets the cell in the position CxR
     * @param cell New state
     * @param c Column
     * @param r Row
     */
    public void setCell(Cell cell, int c, int r) { board[c][r] = cell; }
    
    /**
     * Gets the stat of the game
     * @return Game State
     */
    public GameState getState() { return state; }
    
    /**
     * Sets a new state
     * @param s The new state
     */
    public void setState(GameState s) { state = s; }
    
    /**
     * Gets the number of rows
     * @return Rows
     */
    public int getRows() { return rows; }
    
    /**
     * Gets the number of columns
     * @return Columns
     */
    public int getColumns() { return columns; }
    
    /**
     * Root method that checks every possible way to win from a certain point
     * @param column Column where the circle is
     * @param row Row where the circle is
     * @param p Player that could win
     * @return Current game state
     */
    private GameState checkGameState(int column, int row, boolean p) {
        // What type of Cells are we looking for
        Cell cellType = p? Cell.PLAYER_ONE : Cell.PLAYER_TWO;
        
        if(checkHorizontal(column, row, cellType) || 
                checkVertical(column, row, cellType) ||
                    checkDiagonalBotLeft(column, row, cellType) ||
                        checkDiagonalBotRight(column, row, cellType)) {
            // Depending of the player we choose a GameState
            return p? GameState.PLAYER_ONE_VICTORY : GameState.PLAYER_TWO_VICTORY;
        }
        // If there is no space left, then is a draw
        if (!boardHasSpace()) { return GameState.DRAW; }
        // When nothing fits, the players still play
        return GameState.PLAYING;
    }
    
    /**
     * Root method that checks if there is a win play in that row
     * @param column Column to start
     * @param row Row to check
     * @param cellType The Cells that we are looking for
     * @return True if ther is a winner play
     */
    private boolean checkHorizontal(int column, int row, Cell cellType) {
        int onTheLeft = countLeft(column, row, cellType);
        int onTheRight = countRight(column, row, cellType);
        int total = onTheLeft + onTheRight + 1;
        
        return total >= 4;
    }
    
    /**
     * Counts the left side
     * @param column Column to start
     * @param row Row to check
     * @param cellType The Cells that we are looking for
     * @return Number of equal cells in line
     */
    private int countLeft(int column, int row, Cell cellType) {
        int cellsInRow = 0;
        column--;
        while(column >= 0 && cellType == getCell(column, row)) {
            column--;
            cellsInRow++;
        }
        return cellsInRow;
    }
    
    /**
     * Counts the right side
     * @param column Column to start
     * @param row Row to check
     * @param cellType The Cells that we are looking for
     * @return Number of equal cells in line
     */
    private int countRight(int column, int row, Cell cellType) {
        int cellsInRow = 0;
        column++;
        while(column < getColumns() && cellType == getCell(column, row)) {
            column++;
            cellsInRow++;
        }
        return cellsInRow;
    }
    
    /**
     * Root method that checks if there is a win play in that column
     * @param column Column to check
     * @param row Row to start
     * @param cellType The Cells that we are looking for
     * @return True if ther is a winner play
     */
    private boolean checkVertical(int column, int row, Cell cellType) {
        int onTheTop = countTop(column, row, cellType);
        int onTheBottom = countBottom(column, row, cellType);
        int total = onTheTop + onTheBottom + 1;
        
        return total >= 4;
    }
    
    /**
     * Counts the bottom side
     * @param column Column to check
     * @param row Row to start
     * @param cellType The Cells that we are looking for
     * @return Number of equal cells in line
     */
    private int countBottom(int column, int row, Cell cellType) {
        int cellsInRow = 0;
        row--;
        while(row >= 0 && cellType == getCell(column, row)) {
            row--;
            cellsInRow++;
        }
        return cellsInRow;
    }
    
    /**
     * Counts the top side
     * @param column Column to check
     * @param row Row to start
     * @param cellType The Cells that we are looking for
     * @return Number of equal cells in line
     */
    private int countTop(int column, int row, Cell cellType) {
        int cellsInRow = 0;
        row++;
        while(row < getRows() && cellType == getCell(column, row)) {
            row++;
            cellsInRow++;
        }
        return cellsInRow;
    }
    
    /**
     * Root method that checks if there is a win play in that diagonal
     * @param column Column to start
     * @param row Row to start
     * @param cellType The Cells that we are looking for
     * @return True if ther is a winner play
     */
    private boolean checkDiagonalBotLeft(int column, int row, Cell cellType) {
        int onTheTop = countTopRight(column, row, cellType);
        int onTheBottom = countBottomLeft(column, row, cellType);
        int total = onTheTop + onTheBottom + 1;
        
        return total >= 4;
    }
    
    /**
     * Counts the bottom left side
     * @param column Column to start
     * @param row Row to start
     * @param cellType The Cells that we are looking for
     * @return Number of equal cells in line
     */
    private int countBottomLeft(int column, int row, Cell cellType) {
        int cellsInRow = 0;
        row++;
        column--;
        while(column >= 0 && row < getRows() && cellType == getCell(column, row)) {
            row++;
            column--;
            cellsInRow++;
        }
        return cellsInRow;
    }
    
    /**
     * Counts the top right side
     * @param column Column to start
     * @param row Row to start
     * @param cellType The Cells that we are looking for
     * @return Number of equal cells in line
     */
    private int countTopRight(int column, int row, Cell cellType) {
        int cellsInRow = 0;
        row--;
        column++;
        while(column < getColumns() && row >= 0 && cellType == getCell(column, row)) {
            row--;
            column++;
            cellsInRow++;
        }
        return cellsInRow;
    }
    
    /**
     * Root method that checks if there is a win play in that diagonal
     * @param column Column to start
     * @param row Row to start
     * @param cellType The Cells that we are looking for
     * @return True if ther is a winner play
     */
    private boolean checkDiagonalBotRight(int column, int row, Cell cellType) {
        int onTheTop = countTopLeft(column, row, cellType);
        int onTheBottom = countBottomRight(column, row, cellType);
        int total = onTheTop + onTheBottom + 1;
        
        return total >= 4;
    }
    
    /**
     * Counts the top left side
     * @param column Column to start
     * @param row Row to start
     * @param cellType The Cells that we are looking for
     * @return Number of equal cells in line
     */
    private int countTopLeft(int column, int row, Cell cellType) {
        int cellsInRow = 0;
        row--;
        column--;
        while(column >= 0 && row >= 0 && cellType == getCell(column, row)) {
            row--;
            column--;
            cellsInRow++;
        }
        return cellsInRow;
    }
    
    /**
     * Counts the bottom right side
     * @param column Column to start
     * @param row Row to start
     * @param cellType The Cells that we are looking for
     * @return Number of equal cells in line
     */
    private int countBottomRight(int column, int row, Cell cellType) {
        int cellsInRow = 0;
        row++;
        column++;
        while(column < getColumns() && row < getRows() && cellType == getCell(column, row)) {
            row++;
            column++;
            cellsInRow++;
        }
        return cellsInRow;
    }
    
    /**
     * Checks if the boad has any empty cells
     * @return True if there is any empty one
     */
    private boolean boardHasSpace() {
        for(int i = 0; i < getColumns(); i++) {
            if(columnHasSpace(i)) { return true; }
        }
        return false;
    }
}
