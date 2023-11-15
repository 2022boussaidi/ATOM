package net.thevpc.gaming.atom.examples.kombla.main.server.dal;

import jdk.internal.classfile.impl.ClassPrinterImpl;
import net.thevpc.gaming.atom.examples.kombla.main.shared.dal.ProtocolConstants;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MulticastMainServerDAO implements MainServerDAO{
    MulticastSocket serverSocket;

    private InetAddress groupAddress;
    private int groupPort = 9999;
    private boolean clientConnected = false;



    @Override
    public void start(MainServerDAOListener listener, AppConfig properties) {


        try {
            serverSocket = new MulticastSocket();
            groupAddress = InetAddress.getByName("localhost");

        } catch (Exception ex) {
            throw new RuntimeException("cannot launch server ", ex);
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                listen(listener);
            }
        }).start();

    }
    private void listen(MainServerDAOListener listener) {
        try {
            byte[] buffer = new byte[255];
            DatagramPacket p = new DatagramPacket(buffer, buffer.length);
            while (true) {

                serverSocket.receive(p);
                switch (p.getData()[0]){
                    case 0:
                        // Assuming onReceivePlayerJoined returns a StartGameInfo object
                        StartGameInfo startGameInfo = listener.onReceivePlayerJoined("Number One");
                        clientConnected = true;

                        new Thread(() -> {
                            try {
                                processClient(new ClientSession(p.getAddress(), p.getPort(), startGameInfo));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                                .start();
                        break;


                    new Thread(new Runnable() {

                            @Override
                            public void run() {
                                broadcast(startGameInfo);
                            }
                        }).start();
                        serverSocket.close();
                        break;

                }

            }



        } catch (Exception ex) {
            throw new RuntimeException("cannot launch server" , ex);
        }
    }

    public void broadcast(StartGameInfo s) {
        byte[] buffer = new byte[255]; //burst
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, groupAddress, groupPort);
        while (!clientConnected) {
            try {
                Thread.sleep(1000);
                // send the StartGameInfo
                serverSocket.send(packet);
            } catch (Exception ex) {
                throw new RuntimeException("Erroorrrr", ex);
            }
        }
//        System.out.println("STOP SENDING BURST : Client Already connected");

    }
    @Override
    public void sendModelChanged(DynamicGameModel dynamicGameModel) {

    }
    private class ClientSession {
        private int playerId;
        private Socket socket;
        private ObjectInputStream inputStream;
        private ObjectOutputStream outputStream;
        private Map<Integer, ClientSession> playerToSocketMap = new HashMap<>() ;

        public ClientSession(int playerId, Socket socket) throws IOException {
            this.playerId = playerId;
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        }


        public int getPlayerId() {
            return playerId;
        }

        public Socket getSocket() {
            return socket;
        }

        public ObjectInputStream getInputStream() {
            return inputStream;
        }

        public ObjectOutputStream getOutputStream() {
            return outputStream;
        }

        public void close() {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception according to your needs
            }
        }
    }
    private void processClient(ClientSession clientSession, MainServerDAOListener listener) {
        Map<Integer, ClientSession> playerToSocketMap = new HashMap<>();
        try {
            // Assuming you have an ObjectOutputStream in ClientSession
            ObjectOutputStream outputStream = clientSession.getOutputStream();

            while (true) {
                int command = clientSession.getInputStream().readInt();

                switch (command) {
                    case ProtocolConstants.CONNECT:
                        // Handle CONNECT command
                        String playerName = clientSession.getInputStream().readUTF();
                        listener.onPlayerConnect(playerName);

                        // Respond with StartGameInfo
                        StartGameInfo startGameInfo =new StartGameInfo();
                        writeStartGameInfo(outputStream, startGameInfo);

                        // Store ClientSession in playerToSocketMap
                        playerToSocketMap.put(clientSession.getPlayerId(), clientSession);
                        break;

                    case ProtocolConstants.LEFT:
                        // Handle LEFT command
                        listener.onReceiveMoveLeft(clientSession.playerId);
                        break;
                    case ProtocolConstants.RIGHT:
                        // Handle LEFT command
                        listener.onReceiveMoveRight(clientSession.playerId);
                        break;

                    case ProtocolConstants.DOWN:
                        // Handle LEFT command
                        listener.onReceiveMoveDown(clientSession.playerId);
                        break;
                    case ProtocolConstants.UP:
                        // Handle LEFT command
                        listener.onReceiveMoveUp(clientSession.playerId);
                        break;
                    default:
                    case ProtocolConstants.KO:
                        // Handle LEFT command
                        listener.onReceiveReleaseBomb(clientSession.playerId);
                        break;
                }
            }
        } catch (IOException ex) {
            // Handle IOException (e.g., client disconnected)
            ex.printStackTrace();
        } finally {
            // Clean up resources if needed
            playerToSocketMap.remove(clientSession.getPlayerId());
            clientSession.close();
        }
    }

    private void writeStartGameInfo(ObjectOutputStream outputStream, StartGameInfo startGameInfo) throws IOException {
        // Assuming StartGameInfo is Serializable
        outputStream.writeObject(startGameInfo);
        outputStream.flush();
    }
}