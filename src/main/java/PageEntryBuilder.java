public class PageEntryBuilder {
    private String pdfName;
    private int page;
    private int count;

    public PageEntryBuilder() {
        count = -1;
    }

    public PageEntryBuilder setPdfName(String pdfName) {
        this.pdfName = pdfName;
        return this;
    }

    public PageEntryBuilder setPage(int page) {
        if (page > 0) {
            this.page = page;
        }
        return this;
    }

    public PageEntryBuilder setCount(int count) {
        if (count >= 0) {
            this.count = count;
        }
        return this;
    }

    public PageEntry build() {
        if (pdfName == null || page == 0 || count == -1) {
            throw new IllegalStateException("Не хватает данных для создания объекта класса");
        }
        return new PageEntry(pdfName, page, count);
    }
}
