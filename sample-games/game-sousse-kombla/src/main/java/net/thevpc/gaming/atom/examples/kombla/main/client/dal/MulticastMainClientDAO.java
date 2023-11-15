package net.thevpc.gaming.atom.examples.kombla.main.client.dal;

import net.thevpc.gaming.atom.examples.kombla.main.shared.dal.ProtocolConstants;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;

import java.net.*;

public class MulticastMainClientDAO implements MainClientDAO{
    private DatagramSocket socket;
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
            byte moveLeft = ProtocolConstants.UP;
            byte[] sendData = {moveLeft};
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), 9999);
            socket.send(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sendMoveDown() {
        try {
            byte moveLeft = ProtocolConstants.DOWN;
            byte[] sendData = {moveLeft};
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), 9999);
            socket.send(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sendFire() {
        try {
            byte moveLeft = ProtocolConstants.FIRE;
            byte[] sendData = {moveLeft};
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), 9999);
            socket.send(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}