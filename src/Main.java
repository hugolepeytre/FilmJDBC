// 
// Date : 23 Feb 2020
//
import java.util.Scanner;
import java.util.ArrayList;
import java.text.ParseException;

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
            try {
                switch(action) {
                case "1": 
                    System.out.println("Enter the ID of the person :");
                    String actorId = scanner.nextLine();
                    c.find_all_roles(Integer.parseInt(actorId));
                    break;
                case "2": 
                    System.out.println("Enter the ID of the person :");
                    actorId = scanner.nextLine();
                    c.find_all_movies(Integer.parseInt(actorId));
                    break;
                case "3": 
                    c.most_movies_per_genre();
                    break;
                case "4": 
                    Movie newM = parseMovie(scanner);
                    System.out.println("Enter the list of music IDs from the movie, in the following format : ID1,ID2,ID3,...,LastID");
                    ArrayList<Integer> musics = parseListIds(scanner);
                    System.out.println("Enter the list of genre IDs the movie fits in, in the following format : ID1,ID2,ID3,...,LastID");
                    ArrayList<Integer> genres = parseListIds(scanner);
                    System.out.println("Enter the list of actor IDs from the movie, in the following format : ID1,ID2,ID3,...,LastID");
                    ArrayList<Integer> actors = parseListIds(scanner);
                    System.out.println("Enter the actor's roles, one by one, ending each with ENTER");
                    int numRoles = actors.size();
                    ArrayList<String> roles = parseRoles(scanner, numRoles);
                    System.out.println("Enter the list of writer IDs from the movie, in the following format : ID1,ID2,ID3,...,LastID");
                    ArrayList<Integer> writers = parseListIds(scanner);
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
                    if (5 < rating || rating < 1) {
                        throw new ParseException("The rate should be between 1 and 5",0);
                    }
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
            catch (ParseException e) {
                System.out.println("Wrong argument format, please try again\nError : " + e.getMessage());
            }
        }
        scanner.close();
        c.closeConn();
        System.out.println("Exit program");
    }

    private static Movie parseMovie(Scanner s) throws ParseException {
        System.out.println("Enter the title of the movie : ");
        String tit = s.nextLine();
        System.out.println("Enter the ID of the publishing company : ");
        int cId = Integer.parseInt(s.nextLine());
        System.out.println("Enter the publication year : ");
        int pubYear = Integer.parseInt(s.nextLine());
        if (pubYear > 2020) {
            throw new ParseException("We can't see the future, wrong publication date !",0);
        }
        if (pubYear < 1895) {
            throw new ParseException("Cinema wasn't even a thing, choose another date please",0);
        }
        System.out.println("Enter the platform where the movie was published : ");
        String plat = s.nextLine();
        System.out.println("Enter the storyline : ");
        String story = s.nextLine();
        System.out.println("Was the movie published as video ? (Y/N) : ");
        String b = s.nextLine();
        boolean pubAsV;
        if (b.equals("Y")) {
            pubAsV = true;
        }
        else if (b.equals("N")) {
            pubAsV = false;
        }
        else {
            throw new ParseException("Invalid boolean", 0);
        }
        System.out.println("Enter the publication date (Format : YYYY-MM-DD) : ");
        String pubDate = s.nextLine();
        if(!checkPubDate(pubDate)) {
            throw new ParseException("Wrong format for the publication date",0);
        }
        System.out.println("How long is the movie ? (Format HH:MM:SS) : ");
        String l = s.nextLine();
        if(!checkLength(pubDate)) {
            throw new ParseException("Wrong format for the length of the movie",0);
        }
        return new Movie(cId, pubYear, tit, plat, story, pubAsV, pubDate, l);
    }

    private static ArrayList<Integer> parseListIds(Scanner s) {
        ArrayList<Integer> l = new ArrayList<>();
        String idList = s.nextLine();
        String[] ids = idList.split(",");
        for (String toParseId : ids) {
            l.add(Integer.parseInt(toParseId));
        }
        return l;
    }

    private static ArrayList<String> parseRoles(Scanner s, int numRoles) {
        ArrayList<String> r = new ArrayList<>();
        for (int i = 0; i < numRoles; i++) {
            r.add(s.nextLine());
        }
        return r;
    }
    
    private static boolean checkInBetween(int val, int lower, int upper) {
        return lower <= val && val < upper;
    }
    
    private static boolean checkPubDate(String date) {
        if (date.charAt(4) != '-' || date.charAt(7) != '-') {
            return false;
        } else if (!checkInBetween(Integer.parseInt(date.substring(0,4)), 1895, 2021)
                || !checkInBetween(Integer.parseInt(date.substring(5,6)), 1, 13)
                || !checkInBetween(Integer.parseInt(date.substring(8)), 1, 32)) {
            return false;
        }
        return true;
    }
    
    private static boolean checkLength(String l) {
        if (l.charAt(2) != ':' || l.charAt(5) != ':') {
            return false;
        } else if (!checkInBetween(Integer.parseInt(l.substring(0,2)), 0, 100)
                || !checkInBetween(Integer.parseInt(l.substring(3,5)), 0, 100)
                || !checkInBetween(Integer.parseInt(l.substring(6)), 0, 100)) {
            return false;
        }
        return true;
    }
}

