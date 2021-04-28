/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Nacho
 */
public class Game {
    
    private Cell[][] board;
    private GameState state;
    private int columns, rows;
    
    // creates an empty game board with 8 columns and 7 rows
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
    
    public boolean columnHasSpace(int c) {
        for (int i = 0; i < getRows(); i++) {
            if (getCell(c, i) == Cell.EMPTY) {
                return true;
            }
        }
        return false;
    }
    
    public int getNextRow(int c) {
        int res = getRows() - 1;
        while(res >= 0 && getCell(c, res) != Cell.EMPTY){
            res--;
        }
        return res;
    }
    
    public int addCircle(int c, boolean p) {
        int row = getNextRow(c);
        if (p) { setCell(Cell.PLAYER_ONE, c, row); }
        else { setCell(Cell.PLAYER_TWO, c, row); }
        state = checkGameState(c, row, p);
        return row;
    }
    
    public Cell getCell(int c, int r) { return board[c][r]; }
    
    public void setCell(Cell cell, int c, int r) { board[c][r] = cell; }
    
    public GameState getState() { return state; }
    
    public void setState(GameState s) { state = s; }
    
    public int getRows() { return rows; }
    
    public int getColumns() { return columns; }
    
    
    // EXPERIMENTAL
    // creates an empty game board
//    public Game(int c, int r) {
//        board = new Cell[c][r];
//        for (int i = 0; i < c; i++) {
//            for (int j = 0; j < r; j++) {
//                board[i][j] = Cell.EMPTY;
//            }
//        }
//        state = GameState.PLAYING;
//        
//        columns = board.length;
//        rows = board[0].length;
//    }

    private GameState checkGameState(int column, int row, boolean p) {
        Cell cellType = p? Cell.PLAYER_ONE : Cell.PLAYER_TWO;
        
        if(checkHorizontal(column, row, cellType) || 
                checkVertical(column, row, cellType) ||
                    checkDiagonalBotLeft(column, row, cellType) ||
                        checkDiagonalBotRight(column, row, cellType)) {
            return p? GameState.PLAYER_ONE_VICTORY : GameState.PLAYER_TWO_VICTORY;
        }
        return GameState.PLAYING;
    }
    
    private boolean checkHorizontal(int column, int row, Cell cellType) {
        int onTheLeft = countLeft(column, row, cellType);
        int onTheRight = countRight(column, row, cellType);
        int total = onTheLeft + onTheRight + 1;
        
        return total >= 4;
    }
    
    private int countLeft(int column, int row, Cell cellType) {
        int cellsInRow = 0;
        column--;
        while(column >= 0 && cellType == getCell(column, row)) {
            column--;
            cellsInRow++;
        }
        return cellsInRow;
    }
    
    private int countRight(int column, int row, Cell cellType) {
        int cellsInRow = 0;
        column++;
        while(column < getColumns() && cellType == getCell(column, row)) {
            column++;
            cellsInRow++;
        }
        return cellsInRow;
    }
    
    private boolean checkVertical(int column, int row, Cell cellType) {
        int onTheTop = countTop(column, row, cellType);
        int onTheBottom = countBottom(column, row, cellType);
        int total = onTheTop + onTheBottom + 1;
        
        return total >= 4;
    }
    
    private int countBottom(int column, int row, Cell cellType) {
        int cellsInRow = 0;
        row--;
        while(row >= 0 && cellType == getCell(column, row)) {
            row--;
            cellsInRow++;
        }
        return cellsInRow;
    }
    
    private int countTop(int column, int row, Cell cellType) {
        int cellsInRow = 0;
        row++;
        while(row < getRows() && cellType == getCell(column, row)) {
            row++;
            cellsInRow++;
        }
        return cellsInRow;
    }
    
    private boolean checkDiagonalBotLeft(int column, int row, Cell cellType) {
        int onTheTop = countTopRight(column, row, cellType);
        int onTheBottom = countBottomLeft(column, row, cellType);
        int total = onTheTop + onTheBottom + 1;
        
        return total >= 4;
    }
    
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
    
    private boolean checkDiagonalBotRight(int column, int row, Cell cellType) {
        int onTheTop = countTopLeft(column, row, cellType);
        int onTheBottom = countBottomRight(column, row, cellType);
        int total = onTheTop + onTheBottom + 1;
        
        return total >= 4;
    }
    
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
    
}
