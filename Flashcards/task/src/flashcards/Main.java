package flashcards;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Flashcard> cardList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the number of cards:");
        int cards = Integer.parseInt(scanner.nextLine());

        for (int i = 1; i <= cards; i++) {
            System.out.printf("Card #%d\n", i);
            String term = scanner.nextLine();
            System.out.printf("The definition for card #%d:\n", i);
            String definition = scanner.nextLine();
            cardList.add(new Flashcard(term, definition));
        }
        cardList.forEach(Flashcard::isCorrect);
    }
}
