package flashcards;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class FlashCards {
    enum Menu {
        ADD("add"),
        REMOVE("remove"),
        IMPORT("import"),
        EXPORT("export"),
        ASK("ask"),
        EXIT("exit");

        String userChoice;

        Menu(String userChoice) {
            this.userChoice = userChoice;
        }
    }

    private Scanner scanner = new Scanner(System.in);
    private LinkedHashMap<String, String> cardMap = new LinkedHashMap<>();
    private LinkedHashMap<String, String> keyMap = new LinkedHashMap<>();
    Menu state;
    FileReader inputFile;
    PrintWriter outputFile;

    public void setMenu(String input) {
        //TODO: implement menu
    }

    public Menu currentState() {
        return state;
    }

    public void addCard() {
        //TODO: implement add
    }

    public void removeCard() {
        //TODO: implement remove
    }

    public void importCards() {
        //TODO: implement import
    }

    public void exportCards() {
        //TODO: implement export
    }

    public void getCards() {
        //TODO: implement ask
    }
}
