package com.zaid;

import com.zaid.controllers.GameController;
import com.zaid.exceptions.InvalidInputException;
import com.zaid.exceptions.InvalidMoveException;
import com.zaid.exceptions.SameSymbolException;
import com.zaid.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvalidMoveException, SameSymbolException, InvalidInputException {
        Scanner scanner = new Scanner(System.in);

        List<Player> players = new ArrayList<>();

        System.out.print("Enter number of players: ");
        int numberOfPlayers = scanner.nextInt();
        System.out.println();

        for (int i = 1; i <= numberOfPlayers; i++) {
            System.out.println("Player " + i + "'s configurations --");

            System.out.print("Name: ");
            String name = scanner.next();

            System.out.print("Symbol: ");
            String symbol = scanner.next();

            System.out.print("Human (0) | Bot (1): ");
            int playerTypeInt = scanner.nextInt();
            PlayerType playerType = switch (playerTypeInt) {
                case 0 -> PlayerType.HUMAN;
                case 1 -> PlayerType.BOT;
                default -> throw new InvalidInputException("Invalid input");
            };

            if (playerType == PlayerType.BOT) {
                System.out.print("Bot difficulty level - Easy (0) | Medium (1) | Hard (2): ");
                int botDifficultyLevelInt = scanner.nextInt();
                BotDifficultyLevel botDifficultyLevel = switch (botDifficultyLevelInt) {
                    case 0 -> BotDifficultyLevel.EASY;
                    case 1 -> BotDifficultyLevel.MEDIUM;
                    case 2 -> BotDifficultyLevel.HARD;
                    default -> throw new InvalidInputException("Invalid input");
                };

                players.add(new Bot(name, new Symbol(symbol.charAt(0)), playerType, botDifficultyLevel));
            } else {
                players.add(new Player(name, new Symbol(symbol.charAt(0)), playerType));
            }

            System.out.println();
        }

        System.out.print("Enter dimension of board: ");
        int dimension = scanner.nextInt();
        System.out.println();

        GameController gameController = new GameController();

        Game game = gameController.startGame(dimension, players);
        gameController.printBoard(game);

        while (game.getGameState().equals(GameState.IN_PROGRESS)) {
            gameController.makeMove(game);
            gameController.printBoard(game);
        }

        if (gameController.checkState(game).equals(GameState.DRAW)) {
            System.out.println("Draw!");
        } else {
            System.out.println("Player " + gameController.getWinner(game).getName() + " is the winner!");
        }
    }
}