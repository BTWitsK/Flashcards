package flashcards;
import java.io.*;
import java.util.*;

public class FlashCards {
    enum Menu {
        ADD("add"),
        REMOVE("remove"),
        IMPORT("import"),
        EXPORT("export"),
        ASK("ask"),
        EXIT("exit");

        final String userChoice;

        Menu(String userChoice) {
            this.userChoice = userChoice;
        }
    }

    private Scanner scanner = new Scanner(System.in);
    private final LinkedHashMap<String, String> cardMap = new LinkedHashMap<>();
    private final LinkedHashMap<String, String> keyMap = new LinkedHashMap<>();
    private Menu state;

    public void setMenu(String input) {
        for (Menu state : Menu.values()) {
            if(state.userChoice.equals(input)) {
                this.state = state;
            }
        }
    }

    public Menu getState() {
        return state;
    }

    public boolean isRunning() {
        return state != Menu.EXIT;
    }

    public void addCard() {
        System.out.println("The card:");
        String term = scanner.nextLine();
        if (cardMap.containsKey(term)) {
            System.out.printf("The card \"%s\" already exists.\n\n", term);
            return;
        }

        System.out.println("The definition of the card:");
        String definition = scanner.nextLine();
        if (cardMap.containsValue(definition)) {
            System.out.printf("The definition \"%s\" already exists.\n\n", definition);
            return;
        }

        cardMap.put(term , definition);
        keyMap.put(definition, term);
        System.out.printf("The pair (\"%s\":\"%s\") has been added.\n\n", term, definition);
    }

    public void removeCard() {
        System.out.println("Which card?");
        String card = scanner.nextLine();

        if (null == cardMap.remove(card)) {
            System.out.printf("Can't remove \"%s\": there is no such card.\n\n", card);
        } else {
            System.out.println("The card has been removed.");
        }
    }

    public void importCards() {
        System.out.println("File name:");
        int cardCount = 0;
        try {
            scanner = new Scanner(new FileReader(scanner.nextLine()));
            while (scanner.hasNext()) {
                String[] card = scanner.nextLine().split("\\.");
                cardMap.put(card[0], card[1]);
                keyMap.put(card[1], card[0]);
                cardCount++;
            }
            System.out.printf("%d cards have been loaded.\n\n", cardCount);
            scanner = new Scanner(System.in);

        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public void exportCards() {
        System.out.println("File name:");
        try (PrintWriter outputFile = new PrintWriter(scanner.nextLine())) {
            int cardCount = 0;
            for (var card : cardMap.entrySet()) {
                outputFile.printf("%s.%s\n", card.getKey(), card.getValue());
                cardCount++;
            }
            System.out.printf("%d cards have been saved.\n\n", cardCount);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getCards() {
        System.out.println("How many times to ask?");
        int rounds = Integer.parseInt(scanner.nextLine());
        int i = 0;
        String answer;

        do {
            for (var entry : cardMap.entrySet()) {
                if (i < rounds) {
                    System.out.printf("Print the definition of \"%s\":\n", entry.getKey());
                    answer = scanner.nextLine();

                    if (entry.getValue().equals(answer)) {
                        System.out.println("Correct!");
                    } else if (cardMap.containsValue(answer)) {
                        System.out.printf("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".\n",
                                entry.getValue(), keyMap.get(answer));
                    } else {
                        System.out.printf("Wrong. The right answer is \"%s\".\n", entry.getValue());
                    }
                }
                i++;
            }
        } while (i < rounds);
    }
}