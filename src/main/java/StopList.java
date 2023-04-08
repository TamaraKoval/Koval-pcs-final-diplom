import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StopList {
    List<String> stopList;

    public StopList(File file) {
        stopList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopList.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isInStopList(String str) {
        return stopList.contains(str);
    }
}
