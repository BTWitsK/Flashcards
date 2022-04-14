package flashcards;
import java.util.*;

class Flashcard {
    private static final Scanner scanner = new Scanner(System.in);
    String term;
    String definition;
    private final String wrong;

    Flashcard(String term, String definition) {
        this.term = term;
        this.definition = definition;
        wrong = String.format("Wrong. The right answer is \"%s\"",this.definition);
    }

    public void isCorrect(){
        System.out.printf("Print the definition of \"%s\":\n", this.term);
        System.out.println(this.definition.equals(scanner.nextLine()) ? "Correct!" : wrong);
    }
}
