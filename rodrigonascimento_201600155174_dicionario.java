import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

class Node {
    public String word;
    public String synonyms;
    public Node left;
    public Node right;
    public int balance;
    public int height;

    public Node() {
        this.word = "?";
        this.synonyms = "-";
        this.left = null;
        this.right = null;
        this.balance = 0;
        this.height = 1;
    }

    public Node(String word, String synonyms) {
        this.word = word;
        this.synonyms = synonyms;
        this.left = null;
        this.right = null;
        this.balance = 0;
        this.height = 1;
    }

    /**
     * Calculates the node's balance factor
     */
    public void setBalance() {
        if (left != null && right != null) {
            balance = right.height - left.height;
        } else if (left != null) {
            balance = left.height * -1;
        } else if (right != null) {
            balance = right.height;
        } else {
            balance = 0;
        }
    }

    /**
     * Calculates the node's height
     */
    public void setHeight() {

        if (left != null && right != null) {

            if (left.height > right.height)
                height = left.height + 1;
            else
                height = right.height + 1;

        } else if (left != null) {

            height = left.height + 1;

        } else if (right != null) {

            height = right.height + 1;

        } else {

            height = 1;
        }
    }
}

public class rodrigonascimento_201600155174_dicionario {

    private static Node tree = null;

    /**
     * Inserts a new node into {@link #tree}.
     * @param newNode Node that will be added to the tree.
     */
    private static void insert(Node newNode) { 
        tree = insertToBinaryTree(tree, newNode); 
    }
       
    /**
     * Inserts a new node into a binary tree.
     * @param root      Root of the tree into which the node will be added.
     * @param newNode   Node that will be added to the tree.
     * @return The tree with the added node.
     */
    private static Node insertToBinaryTree(Node root, Node newNode) { 

        // If the tree is empty, return a new node
        if (root == null) { 
            root = newNode;
            return root; 
        } 

        // Otherwise, recur down the tree
        if (newNode.word.compareTo(root.word) <= 0) 
            root.left = insertToBinaryTree(root.left, newNode);
        else if (newNode.word.compareTo(root.word) > 0) 
            root.right = insertToBinaryTree(root.right, newNode);

        root.setHeight();
        root.setBalance();

        root = rotate(root);

        return root;
    }

    /**
     * If necessary, rotates the binary tree.
     * @param root Root of the binary tree.
     * @return Balanced tree.
     */
    private static Node rotate(Node root) {

        if (root.balance == 2) {

            if (root.right.balance == 1) {
                root = rotateLeft(root);
            } else if (root.right.balance == -1) {
                root = rotateRightLeft(root);
            }

        } else if (root.balance == -2) {

            if (root.left.balance == -1) {
                root = rotateRight(root);
            } else if (root.left.balance == 1) {
                root = rotateLeftRight(root);
            }

        }

        return root;
    }

    /**
     * Rotates the tree to the left.
     * @param root Root of the tree.
     * @return Rotated tree.
     */
    private static Node rotateLeft(Node root) {

        Node axis = root.right;
        root.right = axis.left;
        axis.left = root;
        root = axis;

        root.left.setHeight();
        root.left.setBalance();
        root.setHeight();
        root.setBalance();
        return root;
    }

    /**
     * Rotates the tree to the right.
     * @param root Root of the tree.
     * @return Rotated tree.
     */
    private static Node rotateRight(Node root) {

        Node axis = root.left;
        root.left = axis.right;
        axis.right = root;
        root = axis;

        root.right.setHeight();
        root.right.setBalance();
        root.setHeight();
        root.setBalance();

        return root;        
    }

    /**
     * Rotates the tree to the left and then to the right.
     * @param root Root of the tree.
     * @return Rotated tree.
     */
    private static Node rotateLeftRight(Node root) {
        root.left = rotateLeft(root.left);
        root = rotateRight(root);
        return root;
    }

    /**
     * Rotates the tree to the right and then to the left.
     * @param root Root of the tree.
     * @return Rotated tree.
     */
    private static Node rotateRightLeft(Node root) {
        root.right = rotateRight(root.right);
        root = rotateLeft(root);
        return root;
    }

    /**
     * Searchs a binary tree.
     * 
     * @param root      Root of the tree.
     * @param content   Content of the desired node.
     * @param foundNode Node found by the search.
     * @param fileName  Name of the file to which the output will be printed. 
     * @return Node where the content was found.
     * @throws FileNotFoundException
     */
    private static Node search(Node root, String content, Node foundNode, String fileName)
            throws FileNotFoundException {

        if (root == null) {
            return new Node();
        }

        if (content.compareTo(root.word) < 0) {

            writeToFile(fileName, root.word + "->");
            foundNode = search(root.left, content, foundNode, fileName);

        } else if (content.compareTo(root.word) > 0) {

            writeToFile(fileName, root.word + "->");
            foundNode = search(root.right, content, foundNode, fileName);

        } else {
            foundNode = root;
        }

        return foundNode;
    }

    /**
     * Processes the content of input and uses it to create a {@link Node}.
     * @param input Program's input.
     * @return {@link Node} with input's content.
     */
    private static Node processInputLine(String input) {
        int spaceIndex;
        String word;

        // Gets the word
        spaceIndex = input.indexOf(" ");
        word = input.substring(0, spaceIndex);
        input = input.substring(spaceIndex + 1);

        // Removes the number of synonyms
        spaceIndex = input.indexOf(" ");
        input = input.substring(spaceIndex + 1);
        
        // Gets the synonyms
        String synonyms = new String();
        synonyms = input.replace(" ", ",");

        return new Node(word, synonyms);
    }

    /**
     * Writes content to file.
     * @param fileName  Name of the file (with extension) to be writen.
     * @param content   Content to be writen on the file.
     * @throws FileNotFoundException
     */
    private static void writeToFile(String fileName, String content) throws FileNotFoundException {

        try(FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        
        try (FileInputStream inputStream = new FileInputStream(args[0])) {
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Gets the number of words to be inserted
            int n = Integer.parseInt(reader.readLine());

            // Adds words to the tree
            for (int i = 0; i < n; i++) {
                Node tempNode = processInputLine(reader.readLine());
                insert(tempNode);
            }

            // Gets the number of words to be searched
            n = Integer.parseInt(reader.readLine());

            // Searchs for the words on the tree
            for (int i = 0; i < n; i++) {
                
                writeToFile(args[1], "[");
                Node foundNode = search(tree, reader.readLine(), null, args[1]);
                writeToFile(args[1], foundNode.word + "]\n" + foundNode.synonyms);

                // Prints a line break only if the current line is not the final one
                if (i < n - 1)
                    writeToFile(args[1], "\n");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}