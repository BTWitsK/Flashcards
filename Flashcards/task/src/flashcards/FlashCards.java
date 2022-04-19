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
        EXIT("exit"),
        LOG("log"),
        HARDEST("hardest card"),
        RESET("reset stats");

        final String userChoice;

        Menu(String userChoice) {
            this.userChoice = userChoice;
        }
    }

    private Scanner scanner = new Scanner(System.in);
    private final LinkedHashMap<String, String> cardMap = new LinkedHashMap<>();
    private final LinkedHashMap<String, String> keyMap = new LinkedHashMap<>();
    private final StringBuilder log = new StringBuilder();
    private Menu state;

    public void setMenu() {
        print("Input the action (add, remove, import, export, ask exit):\")");
        String input = userInput();

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
        print("The card:");
        String term = userInput();
        if (cardMap.containsKey(term)) {
            print(String.format("The card \"%s\" already exists.\n", term));
            return;
        }

        print("The definition of the card:");
        String definition = userInput();
        if (cardMap.containsValue(definition)) {
            print(String.format("The definition \"%s\" already exists.\n", definition));
            return;
        }

        cardMap.put(term , definition);
        keyMap.put(definition, term);
        print(String.format("The pair (\"%s\":\"%s\") has been added.\n", term, definition));
    }

    public void removeCard() {
        print("Which card?");
        String card = userInput();

        if (null == cardMap.remove(card)) {
            print(String.format("Can't remove \"%s\": there is no such card.\n", card));
        } else {
            print("The card has been removed.");
        }
    }

    public void importCards() {
        print("File name:");
        int cardCount = 0;
        try {
            scanner = new Scanner(new FileReader(userInput()));
            while (scanner.hasNext()) {
                String[] card = userInput().split("\\.");
                cardMap.put(card[0], card[1]);
                keyMap.put(card[1], card[0]);
                cardCount++;
            }
            print(String.format("%d cards have been loaded.\n", cardCount));
            scanner = new Scanner(System.in);

        } catch (IOException e) {
            print("File not found");
        }
    }

    public void exportCards() {
        print("File name:");
        try (PrintWriter outputFile = new PrintWriter(userInput())) {
            int cardCount = 0;
            for (var card : cardMap.entrySet()) {
                outputFile.printf("%s.%s\n", card.getKey(), card.getValue());
                cardCount++;
            }
            print(String.format("%d cards have been saved.\n", cardCount));

        } catch (IOException e) {
            print(e.getMessage());
        }
    }

    public void getCards() {
        print("How many times to ask?");
        int rounds = Integer.parseInt(userInput());
        int i = 0;
        String answer;

        do {
            for (var entry : cardMap.entrySet()) {
                if (i < rounds) {
                    print(String.format("Print the definition of \"%s\":\n", entry.getKey()));
                    answer = userInput();

                    if (entry.getValue().equals(answer)) {
                        print("Correct!");
                    } else if (cardMap.containsValue(answer)) {
                        print(String.format("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".\n",
                                entry.getValue(), keyMap.get(answer)));
                    } else {
                        print(String.format("Wrong. The right answer is \"%s\".\n", entry.getValue()));
                    }
                }
                i++;
            }
        } while (i < rounds);
    }

    public String userInput() {
        String input = scanner.nextLine();
        log.append(input);
        return input;
    }

    public void print(String input) {
        String output = String.format("%s\n", input);
        log.append(output);
        System.out.print(output);
    }

    public void log() {
        System.out.println("File name:");
        try (PrintWriter logFile = new PrintWriter(scanner.nextLine())) {
            logFile.print(log);
            System.out.println("The log has been saved.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printHardestCard() {
        //TODO: implement
    }

    public void resetStats() {
        //TODO: implement
    }
}