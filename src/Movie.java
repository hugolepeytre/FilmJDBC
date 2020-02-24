public class Movie {
    int cId, publicationYear;
    String title, platform, storyline, publicationDate, mLength;
    boolean publishedAsVideo;
    Movie(int cId, int pubYear, String tit, String plat, String story, boolean pubAsV, String pubDate, String l) {
        this.cId = cId;
        publicationYear = pubYear;
        title = tit;
        platform = plat;
        publishedAsVideo = pubAsV;
        publicationDate = pubDate;
        mLength = l;
    }
}