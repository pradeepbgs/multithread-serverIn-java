import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    // public Consumer<Socket> getConsumer(){
    //     return (clientSocket) -> {
    //         try {
    //             PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(),true);
    //             toClient.println("Hello from the server ");
    //             toClient.close();
    //             clientSocket.close();
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     };
    // }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void run () throws IOException{
        int port = 8080;
        ServerSocket socket = new ServerSocket(port);
        System.out.println("Server is running on port: "+port);
        while (true) {
            Socket acceptedSocket = socket.accept();
            // if wanna user getConsumer then can Thread(()->getconsumer().accept(acceptedSocket))
            Thread thread = new Thread(handleClient(acceptedSocket));
            thread.start();
        }
    }

    public Runnable handleClient(Socket clientSocket) {
        return () -> {
            try (PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
             Socket socket = clientSocket) { // Try-with-resources to ensure the socket is closed
            toClient.println("Hello from the server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    }
}
