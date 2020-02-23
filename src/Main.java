// 
// Date : 23 Feb 2020
//
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("coucou");
        Controller c = new Controller();
        Scanner scanner = new Scanner(System.in);
        boolean continueUseCase = true;
        while(continueUseCase) {
            System.out.println("Enter use case : (1,2,3,4 or 5)");
            System.out.println("'Q' to quit");
            
            String action = scanner.nextLine();
            switch(action) {
            case "1": 
                System.out.println("Find the name of all roles a given actor performs");
                System.out.println("Enter the ID of the actor :");
                String actorName = scanner.nextLine();
                c.find_all_roles(Integer.parseInt(actorName)); //TODO handle not valid integer case
                break;
            case "2": 
                System.out.println("Find the name of all movies a given actor appears in");
                System.out.println("Enter the ID of the actor :");
                String actor = scanner.nextLine();
                c.find_all_movies(Integer.parseInt(actor));
                break;
            case "3": 
                System.out.println("Find which company produces most movies within each genre (comedy, family, scifi, etc)");
                c.most_movies_per_genre();
                break;
            case "4": 
                System.out.println("Insert a new movie with director, writers, actors and whatever belongs here.");
                c.insert_new_movies();
                break;
            case "5": //TODO
                System.out.println("Insert a review of an episode of a series");
                System.out.println("Enter the user ID :");
                String id = scanner.nextLine();
                System.out.println("Enter the review text :");
                String text = scanner.nextLine();
                c.insert_review(Integer.parseInt(id), text);
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
}

