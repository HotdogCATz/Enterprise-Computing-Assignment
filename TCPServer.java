import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        int port = 1667;
        System.out.println("Server\nWaiting for client connection at port number " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                // Handle each client in a new thread
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            while (true) {
                // first number
                out.println("enter number 1 (to end just press enter):");
                String input1 = in.readLine();
                if (input1 == null || input1.trim().isEmpty()) {
                    break; // Disconnect if no input
                }

                int number1;
                try {
                    number1 = Integer.parseInt(input1);
                } catch (NumberFormatException e) {
                    out.println("Invalid input. Please enter a valid number.");
                    continue;
                }

                // second number
                out.println("enter number 2 (to end just press enter):");
                String input2 = in.readLine();
                if (input2 == null || input2.trim().isEmpty()) {
                    break; // Disconnect if no input
                }

                int number2;
                try {
                    number2 = Integer.parseInt(input2);
                } catch (NumberFormatException e) {
                    out.println("Invalid input. Please enter a valid number.");
                    continue;
                }

                // Calculate and send the result
                int result = number1 + number2;
                out.println("The result is " + result);
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
