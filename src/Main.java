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
            case "1": //TODO
                break;
            case "2": //TODO
                break;
            case "3": //TODO
                break;
            case "4": //TODO
                break;
            case "5": //TODO
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

