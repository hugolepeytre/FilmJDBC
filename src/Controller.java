import java.sql.*;

public class Controller extends DBConnect {
    public Controller() {
        super();

    }

    public void find_all_roles(int actorId) {
        try {
            PreparedStatement s = connect.prepareStatement("SELECT ActorRole FROM Person NATURAL JOIN WorksOn NATURAL JOIN WorkDone NATURAL JOIN ARole WHERE WorkType = \"Actor\" AND PId = ?;");
            s.setInt(1, actorId);
            ResultSet res = s.executeQuery();
            while(res.next()) {
                System.out.println(String.format("Role : ", res.getString("ActorRole")));
            }
        }
        catch (SQLException e) {
            System.out.println("Error, couldn't find the roles" + e.getMessage());
        }
    }

    public void find_all_movies(int actorId) {
        
    }

    public void most_movies_per_genre() {
        
    }

    public void insert_new_movies() {
        // To add :
        // All movie attributes
        // The category
        // The publishing company
        // 
    }

    public void insert_review(int userId, String text) {
        
    }
}

// TODO :
// 1. Find the name of all roles a given actor performs.
// 2. Find the name of all movies a given actor appears in.
// 3. Find which company produces most movies within each genre (comedy, family, scifi, etc)
// 4. Insert a new movie with director, writers, actors and whatever belongs here.
// 5. Insert a review of an episode of a series.