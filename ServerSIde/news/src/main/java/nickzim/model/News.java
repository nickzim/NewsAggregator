package nickzim.model;


import java.time.LocalDateTime;

public class News {


    private String agency;
    private String title;
    private String link;
    private LocalDateTime pubDate;
    private String description;
    private String category;

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDateTime pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Название: " + title + " \n" +
                "Ссылка: " + link + " \n" +
                "Дата публикации : " + pubDate + " \n" +
                "Описание : " + description + " \n" +
                "Категория : " + category + " \n\n";
    }
}
