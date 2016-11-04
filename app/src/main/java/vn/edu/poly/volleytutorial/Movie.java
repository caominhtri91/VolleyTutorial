package vn.edu.poly.volleytutorial;


import java.util.Date;

public class Movie {
    private String urlPoster;
    private String title;
    private Date releaseDate;
    private Double rate;

    public Movie(String urlPoster, String title, Date releaseDate, Double rate) {
        this.urlPoster = urlPoster;
        this.title = title;
        this.releaseDate = releaseDate;
        this.rate = rate;
    }

    public Movie() {
    }

    public String getUrlPoster() {
        return urlPoster;
    }

    public void setUrlPoster(String urlPoster) {
        this.urlPoster = urlPoster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return urlPoster + "\n" + title + "\n" + releaseDate.toString() + "\n";
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
