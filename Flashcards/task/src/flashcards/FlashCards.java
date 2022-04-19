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

    FlashCards(String[] args) {
        HashMap<String, String> argMap = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            argMap.put(args[i], args[i + 1]);
        }



    }

    private Scanner scanner = new Scanner(System.in);
    private final LinkedHashMap<String, String> cardMap = new LinkedHashMap<>();
    private final LinkedHashMap<String, String> keyMap = new LinkedHashMap<>();
    private final LinkedHashMap<String, Integer> errorMap = new LinkedHashMap<>();
    private final StringBuilder log = new StringBuilder();
    private Menu state;

    public void setMenu() {
        print("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
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

        cardMap.put(term, definition);
        keyMap.put(definition, term);
        errorMap.put(term, 0);
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
                errorMap.put(card[0], Integer.parseInt(card[2]));
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
                outputFile.printf("%s.%s.%d\n", card.getKey(), card.getValue(), errorMap.get(card.getKey()));
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
                    print(String.format("Print the definition of \"%s\":", entry.getKey()));
                    answer = userInput();

                    if (entry.getValue().equals(answer)) {
                        print("Correct!");
                    } else if (cardMap.containsValue(answer)) {
                        print(String.format(
                                "Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".\n",
                                entry.getValue(), keyMap.get(answer)));
                        errorMap.merge(entry.getKey(),1, Integer::sum);
                    } else {
                        print(String.format("Wrong. The right answer is \"%s\".\n", entry.getValue()));
                        errorMap.merge(entry.getKey(),1, Integer::sum);
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
        print("File name:");
        try (PrintWriter logFile = new PrintWriter(userInput())) {
            logFile.print(log);
            print("The log has been saved.\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printHardestCard() {
        StringBuilder singleOutput = new StringBuilder("The hardest card is ");
        StringBuilder multipleOutput = new StringBuilder("The hardest cards are ");
        ArrayList<String> cards = new ArrayList<>();
        int max = 0;

        for (var entry : errorMap.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
            }
        }

        int finalMax = max;
        errorMap.forEach((key, value) -> {
            if (value == finalMax && finalMax != 0) {
                cards.add(key);
            }
        });

        if (cards.size() == 0) {
            print("There are no cards with errors.\n");
        } else if (cards.size() > 1) {
            for (String card : cards) {
                multipleOutput.append("\"").append(card).append("\"").append(", ");
            }
            multipleOutput.append(String.format(". You have %d errors answering them.\n", finalMax));
            print(multipleOutput.toString());
        } else {
            singleOutput.append("\"").append(cards.get(0)).append("\"")
                    .append(String.format(". You have %d errors answering it.\n", finalMax));
            print(singleOutput.toString());
        }
    }

    public void resetStats() {
        errorMap.forEach((key, value) -> errorMap.put(key, 0));
        print("Card statistics have been reset.\n");
    }
}