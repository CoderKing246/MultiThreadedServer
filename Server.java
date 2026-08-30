import java.io.*;
import java.net.*;
import java.util.concurrent.*;

class Server {

    public Server() {

        int port = 8080;

        // Thread pool
        ExecutorService threadPool =
            Executors.newFixedThreadPool(200);

        try {

            ServerSocket serverSocket =
                new ServerSocket(port);

            System.out.println("Server is running...");
            System.out.println("Waiting for client requests...");

            while (true) {

                // Accept client
                Socket socket = serverSocket.accept();

                // Submit client handling task to thread pool
                threadPool.submit(() -> {

                    System.out.println(
                        "Client connected: "
                        + socket.getInetAddress()
                    );

                    try (
                        socket;

                        BufferedReader bufferedReader =
                            new BufferedReader(
                                new InputStreamReader(
                                    socket.getInputStream()
                                )
                            );

                        PrintWriter printWriter =
                            new PrintWriter(
                                socket.getOutputStream(),
                                true
                            )
                    ) {

                        // Read client request
                        String request =
                            bufferedReader.readLine();

                        System.out.println(
                            "Client Request: " + request
                        );

                        // Send response
                        printWriter.println(
                            "Hello Client, I am Server. How are you?"
                        );

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}