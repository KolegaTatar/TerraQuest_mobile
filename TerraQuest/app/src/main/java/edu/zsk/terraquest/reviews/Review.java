package edu.zsk.terraquest.reviews;

public class Review {
    private String stars;
    private String title;
    private String content;
    private String authorDate;

    public Review(String stars, String title, String content, String authorDate) {
        this.stars = stars;
        this.title = title;
        this.content = content;
        this.authorDate = authorDate;
    }

    public String getStars() { return stars; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthorDate() { return authorDate; }
}
