package com.zaid.strategies;

import com.zaid.models.Board;

public class DrawAlgorithm {
    private int numberOfCellsFilled = 0;

    public boolean checkDraw(Board board) {
        numberOfCellsFilled++;
        return numberOfCellsFilled == board.getSize() * board.getSize();
    }
}
