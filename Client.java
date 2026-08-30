import java.io.*;
import java.net.*;

public class Client {

    public Client() {

        try {

            // Client connects to server on localhost and port 8080
            Socket socket = new Socket("localhost", 8080);

            // Printing server address, server port and client port
            System.out.println(
                socket.getInetAddress() + " : " +
                socket.getPort() + " : " +
                socket.getLocalPort()
            );

            PrintWriter printWriter =
                new PrintWriter(socket.getOutputStream(), true);

            BufferedReader bufferedReader =
                new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                );

            // Client sends message to server
            printWriter.println(
                "Hello Server, I am Client. How are you?"
            );

            // Client reads response from server
            String response = bufferedReader.readLine();

            // Printing server response
            System.out.println("Server Response: " + response);

            // Closing resources
            bufferedReader.close();
            printWriter.close();
            socket.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        System.out.println("Client is running...");

        // Create 100 clients
        for (int i = 0; i < 1000000; i++) {

            Thread thread = new Thread(() -> {
                new Client();
            });

            thread.start();
        }
    }
}

