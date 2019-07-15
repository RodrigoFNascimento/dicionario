class Node {
    public String word;
    public String[] synonyms;
    public Node left;
    public Node right;

    public Node() {
        this.word = null;
        this.synonyms = null;
        this.left = null;
        this.right = null;
    }

    public Node(String word, String[] synonyms) {
        this.word = word;
        this.synonyms = synonyms;
        this.left = null;
        this.right = null;
    }
}

public class rodrigonascimento_201600155174_dicionario {

    private static Node tree = null;

    /**
     * Processes the content of input and uses it to create a {@link Node}.
     * @param input Program's input.
     * @return {@link Node} with input's content.
     */
    private static Node processInput(String input) {
        int spaceIndex;
        String word;
        int synonymsArrayLength;

        spaceIndex = input.indexOf(" ");
        word = input.substring(0, spaceIndex);
        input = input.substring(spaceIndex + 1);

        spaceIndex = input.indexOf(" ");
        synonymsArrayLength = Integer.parseInt(input.substring(0, spaceIndex));
        input = input.substring(spaceIndex + 1);
        
        String[] synonymsArray = new String[synonymsArrayLength];
        for (int i = 0; i < synonymsArrayLength; i++) {
            synonymsArray[i] = input.substring(0, spaceIndex);
            input = input.substring(spaceIndex + 1);
        }

        return new Node(word, synonymsArray);
    }

    public static void main(String[] args) {
        
    }
}