import java.sql.*;
import java.util.ArrayList;

public class Controller extends DBConnect {
    private static int nextMovieId, nextCRId, nextWDId;
    public Controller() {
        super();
        try {
            PreparedStatement getNumMovies = connect.prepareStatement("SELECT MAX(MovieId) FROM Movie;");
            PreparedStatement getNumCR = connect.prepareStatement("SELECT MAX(CRId) FROM CommentOrReview;");
            PreparedStatement getNumWDone = connect.prepareStatement("SELECT MAX(WorkId) FROM WorkDone;");
            ResultSet r = getNumMovies.executeQuery();
            if (r.next()) {
                nextMovieId = r.getInt(1) + 1;
            }
            else {
                System.out.println("No movies found in Database");
                nextMovieId = 1;
            }
            r = getNumCR.executeQuery();
            if (r.next()) {
                nextCRId = r.getInt(1) + 1;
            }
            else {
                System.out.println("No comments or reviews found in Database");
                nextCRId = 1;
            }
            r = getNumWDone.executeQuery();
            if (r.next()) {
                nextWDId = r.getInt(1) + 1;
            }
            else {
                System.out.println("No work done found in Database");
                nextWDId = 1;
            }
            System.out.println(String.format("Number of movies : %d, number of coms : %d, number of woks : %d", nextMovieId, nextCRId, nextWDId));
        }
        catch (SQLException e) {
            System.out.println("Error, couldn't get the number of movies and ids : " + e.getMessage());
        }
    }

    public void find_all_roles(int actorId) {
        try {
            PreparedStatement s = connect.prepareStatement("SELECT ActorRole FROM Person NATURAL JOIN WorkDone NATURAL JOIN ARole WHERE WorkType = \"Actor\" AND PId = (?);");
            s.setInt(1, actorId);
            ResultSet res = s.executeQuery();
            if (!res.next()) {
                System.out.println("This person has never acted in any movies");
            }
            else {
                do {
                    System.out.println(String.format("Role : %s\n", res.getString("ActorRole")));
                } while(res.next());
            }
        }
        catch (SQLException e) {
            System.out.println("Error, couldn't find the roles : " + e.getMessage());
        }
    }

    public void find_all_movies(int actorId) {
        try {
            PreparedStatement s = connect.prepareStatement("SELECT Title FROM Person NATURAL JOIN WorkDone NATURAL JOIN WorkedOnMovie NATURAL JOIN Movie WHERE WorkType = \"Actor\" AND PId = (?);");
            s.setInt(1, actorId);
            ResultSet res = s.executeQuery();
            if (!res.next()) {
                System.out.println("This person has never acted in any movies");
            }
            else {
                do {
                    System.out.println(String.format("Movie : %s\n", res.getString("Title")));
                } while(res.next());
            }
        }
        catch (SQLException e) {
            System.out.println("Error, couldn't find the movies : " + e.getMessage());
        }
    }

    public void most_movies_per_genre() {
        try {
            PreparedStatement s = connect.prepareStatement("SELECT CName, CategoryName, MAX(TotalPubMovies) as Tot "
                                                         + "FROM (SELECT CName, CategoryName, COUNT(*) as TotalPubMovies "
                                                         + "FROM PublishingCompany NATURAL JOIN Movie NATURAL JOIN HasGenre NATURAL JOIN Category "
                                                         + "GROUP BY CId, CategoryId) AS tmp_name "
                                                         + "GROUP BY CategoryName;");
            ResultSet r = s.executeQuery();
            while(r.next()) {
                System.out.println(String.format("%s published %d %s movies\n", r.getString("CName"), r.getInt("Tot"), r.getString("CategoryName")));
            }
        }
        catch (SQLException e) {
            System.out.println("An error occured : " + e.getMessage());
        }

    }

    public void insert_new_movies(Movie m, ArrayList<Integer> musics, ArrayList<Integer> genres, ArrayList<Integer> actors, ArrayList<String> roles, ArrayList<Integer> writers, int director) {
        Savepoint s1 = null;
        try {
            s1 = connect.setSavepoint();
        }
        catch (SQLException e2) {
            System.out.println("Critical error : couldn't rollback after failed statements");
        }
        int previousMovieId = nextMovieId;
        int previousWDId = nextWDId;
        try {
            PreparedStatement addMov = connect.prepareStatement("INSERT INTO Movie VALUES ((?), (?), (?), (?), (?), (?), (?), (?), (?));");
            addMov.setInt(1, nextMovieId);
            addMov.setString(2, m.title);
            addMov.setString(3, m.platform);
            addMov.setString(4, m.mLength);
            addMov.setString(5, m.publicationDate);
            addMov.setInt(6, m.publicationYear);
            addMov.setString(7, m.storyline);
            addMov.setBoolean(8, m.publishedAsVideo);
            addMov.setInt(9, m.cId);
            addMov.executeUpdate();

            PreparedStatement addMusic = connect.prepareStatement("INSERT INTO IsIn VALUES ((?), (?));");
            for (int i : musics) {
                addMusic.setInt(1, i);
                addMusic.setInt(2, nextMovieId);
                addMusic.executeUpdate();
            }

            PreparedStatement addGenres = connect.prepareStatement("INSERT INTO HasGenre VALUES ((?), (?));");
            for (int i : genres) {
                addGenres.setInt(1, nextMovieId);
                addGenres.setInt(2, i);
                addGenres.executeUpdate();
            }

            PreparedStatement addWriters1 = connect.prepareStatement("INSERT INTO WorkDone VALUES ((?), (?), (?));");
            PreparedStatement addWriters2 = connect.prepareStatement("INSERT INTO WorkedOnMovie VALUES ((?), (?));");
            for (int i : writers) {
                addWriters1.setInt(1, nextWDId);
                addWriters1.setString(2, "Writer");
                addWriters1.setInt(3, i);
                addWriters1.executeUpdate();
                addWriters2.setInt(1, nextMovieId);
                addWriters2.setInt(2, nextWDId);
                addWriters2.executeUpdate();
                nextWDId += 1;
            }

            PreparedStatement addActors1 = connect.prepareStatement("INSERT INTO WorkDone VALUES ((?), (?), (?));");
            PreparedStatement addActors2 = connect.prepareStatement("INSERT INTO WorkedOnMovie VALUES ((?), (?));");
            PreparedStatement addActors3 = connect.prepareStatement("INSERT INTO ARole VALUES ((?), (?));");
            for (int j = 0; j < actors.size(); j++) {
                addActors1.setInt(1, nextWDId);
                addActors1.setString(2, "Actor");
                addActors1.setInt(3, actors.get(j));
                addActors1.executeUpdate();
                addActors2.setInt(1, nextMovieId);
                addActors2.setInt(2, nextWDId);
                addActors2.executeUpdate();
                addActors3.setInt(1, nextWDId);
                addActors3.setString(2, roles.get(j));
                addActors3.executeUpdate();
                nextWDId += 1;
            }

            PreparedStatement addDirector1 = connect.prepareStatement("INSERT INTO WorkDone VALUES ((?), (?), (?));");
            PreparedStatement addDirector2 = connect.prepareStatement("INSERT INTO WorkedOnMovie VALUES ((?), (?));");
            addDirector1.setInt(1, nextWDId);
            addDirector1.setString(2, "Director");
            addDirector1.setInt(3, director);
            addDirector1.executeUpdate();
            addDirector2.setInt(1, nextMovieId);
            addDirector2.setInt(2, nextWDId);
            addDirector2.executeUpdate();
            nextWDId += 1;

            nextMovieId += 1;
            connect.commit();
        }
        catch (SQLException e) {
            try {
                connect.rollback(s1);
            }
            catch (SQLException e2) {
                System.out.println("Critical error : couldn't rollback after failed statements");
            }
            nextMovieId = previousMovieId;
            nextWDId = previousWDId;
            System.out.println("Couldn't insert movie : " + e.getMessage());
        }
    }

    public void insert_review(int userId, String text, int episodeId, int movieId, int grade) {
        Savepoint s1 = null;
        try {
            s1 = connect.setSavepoint();
        }
        catch (SQLException e2) {
            System.out.println("Critical error : couldn't rollback after failed statements");
        }
        int oldCRId = nextCRId;
        try {
            PreparedStatement createReview = connect.prepareStatement("INSERT INTO CommentOrReview VALUES ((?), (?), (?));");
            PreparedStatement createRating = connect.prepareStatement("INSERT INTO ReviewRatings VALUES ((?), (?));");
            PreparedStatement createRelationship = connect.prepareStatement("INSERT INTO ReviewedEpisode VALUES ((?), (?), (?));");

            createReview.setInt(1, nextCRId);
            createReview.setString(2, text);
            createReview.setInt(3, userId);

            createRating.setInt(1, nextCRId);
            createRating.setInt(2, grade);

            createRelationship.setInt(1, nextCRId);
            createRelationship.setInt(2, episodeId);
            createRelationship.setInt(3, movieId);

            createReview.executeUpdate();
            createRating.executeUpdate();
            createRelationship.executeUpdate();
            nextCRId += 1;
            
            connect.commit();
        }
        catch (SQLException e) {
            try {
                connect.rollback(s1);
            }
            catch (SQLException e2) {
                System.out.println("Critical error : couldn't rollback after failed statements");
            }
            nextCRId = oldCRId;
            System.out.println("Error while inserting review : " + e.getMessage());
        }
    }
}
