package flashcards;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, String> cardMap = new LinkedHashMap<>();
        LinkedHashMap<String, String> keyMap = new LinkedHashMap<>();

        System.out.println("Input the number of cards:");
        int cards = Integer.parseInt(scanner.nextLine());
        String term;
        String definition;
        String answer;

        for (int i = 1; i <= cards; i++) {
            System.out.printf("Card #%d:\n", i);
            term = scanner.nextLine();

            while (cardMap.containsKey(term)) {
                System.out.printf("The term \"%s\" already exists. Try again:\n", term);
                term = scanner.nextLine();
            }

            System.out.printf("The definition for card #%d:\n", i);
            definition = scanner.nextLine();

            while (cardMap.containsValue(definition)) {
                System.out.printf("The definition \"%s\" already exists. Try again:\n", definition);
                definition = scanner.nextLine();
            }
            cardMap.put(term, definition);
            keyMap.put(definition, term);
        }

        for (var entry : cardMap.entrySet()) {
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
    }
}
