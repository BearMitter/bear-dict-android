package site.notfound.navigation_try;

import java.util.List;

/**
 * Created by lenovo on 2018/3/22.
 */

public class News {

    String title;
    String author;
    String published;
    String url;
    String image;
    List<String> videos;
    List<String> keywords;
    String description;
    String body;

    @Override
    public String toString() {
        return "News [title=" + title + ", author=" + author + ", published=" + published + ", url=" + url + ", image="
                + image + ", videos=" + videos + ", keywords=" + keywords + ", description=" + description + ", body="
                + body + "]";
    }

    public String getSimpleString() {
        return "News [title=" + title + ", author=" + author + ", published=" + published + ", url=" + url + ", image="
                + image + ", videos=" + videos + ", keywords=" + keywords + ", description=" + description +"]";
    }

    public News() {
    }

    public News(String title, String author, String published, String url, String image, List<String> videos,
                List<String> keywords, String description, String body) {
        super();
        this.title = title;
        this.author = author;
        this.published = published;
        this.url = url;
        this.image = image;
        this.videos = videos;
        this.keywords = keywords;
        this.description = description;
        this.body = body;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getPublished() {
        return published;
    }
    public String getUrl() {
        return url;
    }
    public String getImage() {
        return image;
    }
    public List<String> getVideos() {
        return videos;
    }
    public List<String> getKeywords() {
        return keywords;
    }
    public String getDescription() {
        return description;
    }
    public String getBody() {
        return body;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setPublished(String published) {
        this.published = published;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setVideos(List<String> videos) {
        this.videos = videos;
    }
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setBody(String body) {
        this.body = body;
    }

}
