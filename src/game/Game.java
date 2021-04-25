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
    public enum Cell {
        empty,
        p1,
        p2;
    }
    
    public enum GameState {
        playing,
        w1,
        w2,
        draw
    }
    
    private Cell[][] board;
    private GameState state;
    private int columns, rows;
    
    // creates an empty game board with 8 columns and 7 rows
    public Game() {
        board = new Cell[8][7];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = Cell.empty;
            }
        }
        state = GameState.playing;
        
        columns = board.length;
        rows = board[0].length;
    }
    
    // EXPERIMENTAL
    // creates an empty game board
    public Game(int c, int r) {
        board = new Cell[c][r];
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++) {
                board[i][j] = Cell.empty;
            }
        }
        state = GameState.playing;
        
        columns = board.length;
        rows = board[0].length;
    }
    
    public boolean columnHasSpace(int c) {
        for (int i = 0; i < getRows() - 1; i++) {
            if (getCell(c, i) == Cell.empty) {
                return true;
            }
        }
        return false;
    }
    
    public int getNextRow(int c) {
        int res = -1;
        while (getCell(c, res + 1) == Cell.empty) {
            res++;
        }
        return res;
    }
    
    public void addCircle(int c, boolean p) {
        if (p) { setCell(Cell.p1, c, getNextRow(c)); }
        else { setCell(Cell.p2, c, getNextRow(c)); }
    }
    
    public Cell getCell(int c, int r) { return board[c][r]; }
    
    public void setCell(Cell cell, int c, int r) { board[c][r] = cell; }
    
    public GameState getState() { return state; }
    
    public void setState(GameState s) { state = s; }
    
    public int getRows() { return rows; }
    
    public int getColumns() { return columns; }
}
