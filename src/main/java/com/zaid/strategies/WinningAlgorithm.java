package com.zaid.strategies;

import com.zaid.models.Board;
import com.zaid.models.Move;

import java.util.HashMap;

public class WinningAlgorithm {
    HashMap<Integer, HashMap<Character, Integer>> rowMaps = new HashMap<>();
    HashMap<Integer, HashMap<Character, Integer>> colMaps = new HashMap<>();
    HashMap<Character, Integer> leftDiagonalMap = new HashMap<>();
    HashMap<Character, Integer> rightDiagonalMap = new HashMap<>();

    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Character character = move.getPlayer().getSymbol().getCharacter();

        boolean checkRowsAndCols = validateAxes(board, row, character, rowMaps) || validateAxes(board, row, character, colMaps);
        boolean checkLeftDiagonal = row == col && validateDiagonal(character, board, leftDiagonalMap);
        boolean checkRightDiagonal = row + col == board.getSize() - 1 && validateDiagonal(character, board, rightDiagonalMap);

        return checkRowsAndCols || checkLeftDiagonal || checkRightDiagonal;
    }

    private boolean validateAxes(Board board, int axisIndex, Character character, HashMap<Integer, HashMap<Character, Integer>> axesMap) {
        if (!axesMap.containsKey(axisIndex)) {
            axesMap.put(axisIndex, new HashMap<>());
        }
        HashMap<Character, Integer> currAxisMap = axesMap.get(axisIndex);

        if (!currAxisMap.containsKey(character)) {
            currAxisMap.put(character, 1);
        } else {
            currAxisMap.put(character, currAxisMap.get(character) + 1);
        }

        return currAxisMap.get(character) == board.getSize();
    }

    private boolean validateDiagonal(Character character, Board board, HashMap<Character, Integer> diagonalMap) {
        if (!diagonalMap.containsKey(character)) {
            diagonalMap.put(character, 1);
        } else {
            diagonalMap.put(character, diagonalMap.get(character) + 1);
        }

        return diagonalMap.get(character) == board.getSize();
    }
}
