package Assignment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client extends NetworkEntity {
    private BufferedReader userInputReader;

    public Client(String address, int port) {
        super(address, port);
        userInputReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void handleConnection() {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    
            // Start a thread to handle incoming messages from the server
            new Thread(() -> {
                try {
                    String serverMessage;
                    while (socket.isConnected() && (serverMessage = input.readUTF()) != null) {
                        System.out.println("Server: " + serverMessage);
                    }
                } catch (IOException e) {
                    // Check if the socket is closed
                    if (!socket.isConnected()) {
                        System.out.println("Socket closed. Exiting thread.");
                    } else {
                        // System.out.println("Error reading server message: " + e.getMessage());
                        
                    }
                    closeConnections();
                }
            }).start();
    
            // Send messages to the server
            String userInput;
            while (true) {
                System.out.println("Enter a message to send to the server (type 'Over' to stop):\n");
                userInput = userInputReader.readLine();
                if (userInput.equalsIgnoreCase("Over")) {
                    break;  // Exit the loop if user inputs "Over"
                }
                out.writeUTF(userInput);
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("Error handling connection: " + e.getMessage());
            closeConnections();
        } finally {
            closeConnections();
        }
    }
    
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 5000);
        client.handleConnection();
    }
}
