package edu.zsk.terraquest.help;

public class HelpItem {
    private int id;
    private String title;
    private String content;

    public HelpItem(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
}
