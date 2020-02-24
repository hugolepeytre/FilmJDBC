import java.sql.Date;
import java.sql.Time;

public class Movie {
    int cId, publicationYear;
    String title, platform, storyline;
    boolean publishedAsVideo;
    Time mLength; 
    Date publicationDate;
    Movie(int cId, int pubYear, String tit, String plat, String story, boolean pubAsV, Date pubDate, Time l) {
        this.cId = cId;
        publicationYear = pubYear;
        title = tit;
        platform = plat;
        publishedAsVideo = pubAsV;
        publicationDate = pubDate;
        mLength = l;
    }
}