package com.zaid.controllers;

import com.zaid.exceptions.InvalidMoveException;
import com.zaid.exceptions.SameSymbolException;
import com.zaid.models.*;

import java.util.HashSet;
import java.util.List;

public class GameController {
    public Game startGame(int dimension, List<Player> players) throws SameSymbolException {
        HashSet<Character> symbolMap = new HashSet<>();
        for (Player player : players) {
            if (symbolMap.contains(player.getSymbol().getCharacter())) {
                throw new SameSymbolException("Two or more players cannot have the same symbol");
            }
            symbolMap.add(player.getSymbol().getCharacter());
        }
        return new Game(dimension, players);
    }

    public void makeMove(Game game) throws InvalidMoveException {
        game.makeMove();
    }

    public GameState checkState(Game game) {
        return game.getGameState();
    }

    public Player getWinner(Game game) {
        return game.getWinner();
    }

    public void printBoard(Game game) {
        game.printBoard();
    }
}
