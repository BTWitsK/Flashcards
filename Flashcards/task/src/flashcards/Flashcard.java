package flashcards;

class Flashcard {
    String term;
    String definition;

    Flashcard() {
        term = "purchase";
        definition = "buy";
    }

    @Override
    public String toString() {
        return String.format(""" 
                Card:
                %s
                Definition:
                %s
                """, term, definition);
    }
}
