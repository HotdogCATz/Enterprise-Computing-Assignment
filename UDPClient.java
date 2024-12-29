import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 9876;

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress serverIP = InetAddress.getByName(serverAddress);

            // Send an empty request to the server
            byte[] sendBuffer = new byte[1];
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverIP, port);
            clientSocket.send(sendPacket);

            // Receive the response from the server
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(receivePacket);

            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server response: " + serverResponse);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
