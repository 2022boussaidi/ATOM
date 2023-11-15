package net.thevpc.gaming.atom.examples.kombla.main.shared.model;

import java.util.Scanner;

/**
 * Created by vpc on 10/7/16.
 */
public class StartGameInfo {
    private int playerId;
    private int[][] maze;

    public StartGameInfo(int playerId, int[][] maze) {
        this.playerId = playerId;
        this.maze = maze;
    }


    public StartGameInfo readStartGameInfo(Scanner scanner) {

        int playerId = scanner.nextInt();
        int numRows = scanner.nextInt();
        int numCols = scanner.nextInt();
        int[][] maze = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                maze[i][j] = scanner.nextInt();
            }
        }

        // Créer et retourner l'objet StartGameInfo
        return new StartGameInfo(playerId, maze);
    }
    public int getPlayerId() {
        return playerId;
    }

    public int[][] getMaze() {
        return maze;
    }
}
