import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    static final int PORT = 8989;

    public static void main(String[] args) {

        String message = "бизнес, код";

        try (Socket socket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(message);
            System.out.println(in.readLine());

        } catch (IOException e) {
            System.out.println("Не могу подключиться к серверу");
            e.printStackTrace();
        }
    }
}
