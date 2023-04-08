import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();

        File stopFile = new File("stop-ru.txt");
        StopList stopList = new StopList(stopFile);

        File dir = new File("pdfs");
        BooleanSearchEngine searchEngine = new BooleanSearchEngine(dir, stopList);

        try (ServerSocket serverSocket = new ServerSocket(8989)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String wordForSearch = in.readLine();
                    List<PageEntry> resultOfSearch = searchEngine.search(wordForSearch);
                    if (resultOfSearch != null) {
                        resultOfSearch.sort(PageEntry::compareTo);
                        String jsonResult = gson.toJson(resultOfSearch);
                        out.println(jsonResult);
                    } else {
                        out.println("По вашему запросу ничего не найдено");
                    }
                } catch (IOException e) {
                    System.out.println("Не могу стартовать сервер");
                    e.printStackTrace();
                }
            }
        }
    }
}