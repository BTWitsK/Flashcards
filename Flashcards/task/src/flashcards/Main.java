package flashcards;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlashCards cardDeck = new FlashCards();

        do {
            System.out.println("Input the action (add, remove, import, export, ask exit):");
            cardDeck.setMenu(scanner.nextLine());

            switch (cardDeck.getState()) {
                case ADD -> cardDeck.addCard();
                case REMOVE -> cardDeck.removeCard();
                case ASK -> cardDeck.getCards();
                case IMPORT -> cardDeck.importCards();
                case EXPORT -> cardDeck.exportCards();
                case LOG -> cardDeck.log();
                case HARDEST -> cardDeck.printHardestCard();
                case RESET -> cardDeck.resetStats();
                case EXIT -> System.out.println("Bye Bye!");
            }
        } while (cardDeck.isRunning());
    }
}