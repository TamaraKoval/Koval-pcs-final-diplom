import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private Map<String, List<PageEntry>> wordsCount;

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        wordsCount = new HashMap<>();

        if (pdfsDir.isDirectory()) {
            for (File pdf : Objects.requireNonNull(pdfsDir.listFiles())) {
                PdfDocument doc = new PdfDocument(new PdfReader(pdf));
                for (int i = 1; i <= doc.getNumberOfPages(); ++i) {
                    PdfPage page = doc.getPage(i);
                    String text = PdfTextExtractor.getTextFromPage(page);
                    String[] words = text.split("\\P{IsAlphabetic}+");

                    Map<String, Integer> freqs = new HashMap<>();
                    for (String word : words) {
                        if (word.isEmpty()) {
                            continue;
                        }
                        word = word.toLowerCase();
                        freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                    }

                    for (String word : freqs.keySet()) {
                        PageEntry currentPageEntry = new PageEntry(pdf.getName(), i, freqs.get(word));
                        List<PageEntry> currentList;
                        if (wordsCount.containsKey(word)) {
                            currentList = wordsCount.get(word);
                        } else {
                            currentList = new ArrayList<>();
                        }
                        currentList.add(currentPageEntry);
                        wordsCount.put(word, currentList);
                    }
                }
                doc.close();
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        String searchWord = word.toLowerCase();
        return wordsCount.get(searchWord);
    }
}
