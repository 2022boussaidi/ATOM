package net.thevpc.gaming.atom.examples.kombla.main.shared.model;

import net.thevpc.gaming.atom.model.DefaultPlayer;
import net.thevpc.gaming.atom.model.DefaultSprite;
import net.thevpc.gaming.atom.model.ModelPoint;
import net.thevpc.gaming.atom.model.Point;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
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

    public StartGameInfo() {

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
    public DefaultPlayer readPlayer(Socket socket) throws IOException {
            DefaultPlayer player = new DefaultPlayer();

            try (DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {
                // (a) Lire l'id et le nom du joueur
                int id = inputStream.readInt();
                String name = inputStream.readUTF();
                // (b) Lire les propriétés du joueur sous forme de Map
                Map<String, Object> properties = readProperties(inputStream);
                // Initialiser l'instance de DefaultPlayer avec les informations lues
                player.setId(id);
                player.setName(name);
                // Set each property individually
                for (Map.Entry<String, Object> entry : properties.entrySet()) {
                    player.setProperty(entry.getKey(), entry.getValue());
                }
            }
            return player;
        }
        private Map<String, Object> readProperties(DataInputStream inputStream) throws IOException {
            Map<String, Object> properties = new HashMap<>();

            // Lire le nombre de propriétés dans la Map
            int numProperties = inputStream.readInt();

            // Lire chaque paire clé-valeur
            for (int i = 0; i < numProperties; i++) {
                String key = inputStream.readUTF();
                Object value = inputStream.readUTF();
                properties.put(key, value);
            }
            return properties;
        }

    public DefaultSprite readSprite(Socket socket) throws IOException {
        DefaultSprite sprite = new DefaultSprite();

        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {
            // (a) Lire l'id, le kind, le name, la location, la direction et le playerId du sprite
            int id = inputStream.readInt();
            String kind = inputStream.readUTF();
            String name = inputStream.readUTF();
            Point location = readPoint(inputStream);
            int direction = inputStream.readInt();
            int playerId = inputStream.readInt();

            // (b) Lire les propriétés du sprite sous forme de Map
            Map<String, String> properties = readProperty(inputStream);

            // Initialiser l'instance de DefaultSprite avec les informations lues
            sprite.setId(id);
            sprite.setKind(kind);
            sprite.setName(name);
            sprite.setLocation((ModelPoint) location);
            sprite.setDirection(direction);
            sprite.setPlayerId(playerId);
            // Set each property individually
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                sprite.setProperty(entry.getKey(), entry.getValue());
            }
        }

        return sprite;
    }
    private Point readPoint(DataInputStream inputStream) throws IOException {
        int x = inputStream.readInt();
        int y = inputStream.readInt();
        return new Point();
    }

    private Map<String, String> readProperty(DataInputStream inputStream) throws IOException {
        Map<String, String> properties = new HashMap<>();

        // Lire le nombre de propriétés dans la Map
        int numProperties = inputStream.readInt();

        // Lire chaque paire clé-valeur
        for (int i = 0; i < numProperties; i++) {
            String key = inputStream.readUTF();
            String value = inputStream.readUTF();
            properties.put(key, value);
        }

        return properties;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int[][] getMaze() {
        return maze;
    }
}
