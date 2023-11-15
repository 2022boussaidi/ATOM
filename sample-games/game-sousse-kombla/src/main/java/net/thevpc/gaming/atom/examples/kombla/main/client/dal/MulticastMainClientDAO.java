package net.thevpc.gaming.atom.examples.kombla.main.client.dal;

import net.thevpc.gaming.atom.examples.kombla.main.server.dal.MainServerDAOListener;
import net.thevpc.gaming.atom.examples.kombla.main.shared.dal.ProtocolConstants;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class MulticastMainClientDAO implements MainClientDAO{
    private DatagramSocket socket;
    MulticastSocket clientSocket;

    private InetAddress groupAddress;
    private int groupPort = 9999;
    @Override
    public void start(MainClientDAOListener listener, AppConfig properties) {

    }

    @Override
    public StartGameInfo connect() {
        try {
            socket = new DatagramSocket();

            byte connect =0;
            byte[] buffer = new byte[255];
            byte[] sendData = {connect};
            DatagramPacket p =new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"),9999);
            p.setData(sendData);
            socket.send(p);
            //boolean connected = false;
            byte[] buffer1 = new byte[255];
            socket.receive(new DatagramPacket(buffer1, buffer1.length));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendMoveLeft() {
        try {
            byte moveLeft = ProtocolConstants.LEFT;
            byte[] sendData = {moveLeft};
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), 9999);
            socket.send(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void sendMoveRight() {
        try {
            byte moveLeft = ProtocolConstants.RIGHT;
            byte[] sendData = {moveLeft};
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), 9999);
            socket.send(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void sendMoveUp() {
        try {
            byte MoveUp = ProtocolConstants.UP;
            byte[] sendData = {MoveUp};
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), 9999);
            socket.send(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sendMoveDown() {
        try {
            byte MoveDown = ProtocolConstants.DOWN;
            byte[] sendData = {MoveDown};
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), 9999);
            socket.send(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sendFire() {
        try {
            byte Fire = ProtocolConstants.FIRE;
            byte[] sendData = {Fire};
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), 9999);
            socket.send(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void onLoopReceiveModelChanged(MainClientDAOListener listener) {
        try {
            byte[] buffer = new byte[1024];
            clientSocket = new MulticastSocket(groupPort);
            clientSocket.joinGroup(groupAddress);

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                clientSocket.receive(packet);

                // Deserialize the received object from the packet data
                ByteArrayInputStream byteStream = new ByteArrayInputStream(packet.getData());
                ObjectInputStream inputStream = new ObjectInputStream(byteStream);
                DynamicGameModel model = (DynamicGameModel) inputStream.readObject();

                // Notify the listener about the model change
                listener.onModelChanged(model);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error receiving multicast model", ex);
        } finally {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        }
    }
    }
