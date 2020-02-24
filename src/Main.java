// 
// Date : 23 Feb 2020
//
import java.util.Scanner;
import java.util.ArrayList;;

public class Main {
    public static void main(String[] args) {
        System.out.println("Heyy ;)");
        Controller c = new Controller();
        Scanner scanner = new Scanner(System.in);
        boolean continueUseCase = true;
        while(continueUseCase) {
            System.out.println("Enter use case : (1,2,3,4 or 5)");
            System.out.println("'Q' to quit");
            
            String action = scanner.nextLine();
            // TODO : Check validity of all arguments
            switch(action) {
            case "1": 
                System.out.println("Enter the ID of the actor :");
                String actorId = scanner.nextLine();
                c.find_all_roles(Integer.parseInt(actorId));
                break;
            case "2": 
                System.out.println("Enter the ID of the actor :");
                actorId = scanner.nextLine();
                c.find_all_movies(Integer.parseInt(actorId));
                break;
            case "3": 
                c.most_movies_per_genre();
                break;
            case "4": 
                Movie newM = parseMovie();
                System.out.println("Enter the list of music IDs from the movie, in the following format : ID1,ID2,ID3,...,LastID");
                ArrayList<Integer> musics = parseListIds();
                System.out.println("Enter the list of genre IDs the movie fits in, in the following format : ID1,ID2,ID3,...,LastID");
                ArrayList<Integer> genres = parseListIds();
                System.out.println("Enter the list of actor IDs from the movie, in the following format : ID1,ID2,ID3,...,LastID");
                ArrayList<Integer> actors = parseListIds();
                System.out.println("Enter the actor's roles, one by one, ending each with ENTER");
                int numRoles = actors.size();
                ArrayList<String> roles = parseRoles(numRoles);
                System.out.println("Enter the list of writer IDs from the movie, in the following format : ID1,ID2,ID3,...,LastID");
                ArrayList<Integer> writers = parseListIds();
                System.out.println("The ID of the director : ");
                int dirId = Integer.parseInt(scanner.nextLine());
                c.insert_new_movies(newM, musics, genres, actors, roles, writers, dirId);
                break;
            case "5": 
                System.out.println("Insert a review of an episode of a series");
                System.out.println("Enter the user's id :");
                int uid = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter the review text :");
                String text = scanner.nextLine();
                System.out.println("How would you rate the movie ? (1 to 5)");
                int rating = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter the movie's id : ");
                int movieId = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter the episode's id : ");
                int epId = Integer.parseInt(scanner.nextLine());
                c.insert_review(uid, text, epId, movieId, rating);
                break;
            case "Q": 
                continueUseCase = false;
                break;
            default: System.out.println("Not a valid command. Enter 1,2,3,4,5 or Q");
                break;
            }
            System.out.println("");
        }
        scanner.close();
        System.out.println("Exit program");
    }

    private static Movie parseMovie() {

    }

    private static ArrayList<Integer> parseListIds() {
        String idList = scanner.nextLine();
    }

    private static ArrayList<String> parseRoles(int numRoles) {

    }
}

