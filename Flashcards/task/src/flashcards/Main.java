package flashcards;

public class Main {
    public static void main(String[] args) {
        FlashCards cardDeck = new FlashCards(args);

        do {
            cardDeck.setMenu();
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