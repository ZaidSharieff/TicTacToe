package com.zaid.models;

import com.zaid.exceptions.InvalidMoveException;
import com.zaid.strategies.DrawAlgorithm;
import com.zaid.strategies.WinningAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private GameState gameState;
    private Player winner;
    private int nextPlayerMoveIndex;
    private final WinningAlgorithm winningAlgorithm;
    private final DrawAlgorithm drawAlgorithm;

    public Game(int dimension, List<Player> players) {
        this.board = new Board(dimension);
        this.players = players;
        this.moves = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
        this.winner = null;
        this.nextPlayerMoveIndex = 0;
        this.winningAlgorithm = new WinningAlgorithm();
        this.drawAlgorithm = new DrawAlgorithm();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getNextPlayerMoveIndex() {
        return nextPlayerMoveIndex;
    }

    public void setNextPlayerMoveIndex(int nextPlayerMoveIndex) {
        this.nextPlayerMoveIndex = nextPlayerMoveIndex;
    }

    public void printBoard() {
        this.board.printBoard();
    }

    private boolean validateMove(Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        boolean checkMoveInsideBoard = (row >= 0 && row < board.getSize() && col >= 0 && col < board.getSize());
        boolean checkCellEmpty = board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY);

        return checkMoveInsideBoard && checkCellEmpty;
    }

    public void makeMove() throws InvalidMoveException {
        Player currentPlayer = players.get(nextPlayerMoveIndex);

        System.out.print(currentPlayer.getName() + "'s turn: ");
        if (currentPlayer.getPlayerType().equals(PlayerType.BOT)) System.out.println();

        Move move = currentPlayer.makeMove(board);
        if (!validateMove(move)) {
            throw new InvalidMoveException("Invalid move made by " + currentPlayer.getName());
        }

        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        Cell cellToChange = board.getBoard().get(row).get(col);
        cellToChange.setPlayer(currentPlayer);
        cellToChange.setCellState(CellState.FILLED);

        Move finalMove = new Move(cellToChange, currentPlayer);
        moves.add(finalMove);
        nextPlayerMoveIndex = (nextPlayerMoveIndex + 1) % players.size();

        if (drawAlgorithm.checkDraw(board)) {
            gameState = GameState.DRAW;
        }

        if (winningAlgorithm.checkWinner(board, finalMove)) {
            gameState = GameState.ENDED;
            winner = currentPlayer;
        }
    }
}
