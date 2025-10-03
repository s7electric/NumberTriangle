import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.IntSupplier;

/**
 * This is the provided NumberTriangle class to be used in this coding task.
 *
 * Note: This is like a tree, but some nodes in the structure have two parents.
 *
 * The structure is shown below. Observe that the parents of e are b and c, whereas
 * d and f each only have one parent. Each row is complete and will never be missing
 * a node. So each row has one more NumberTriangle object than the row above it.
 *
 *                  a
 *                b   c
 *              d   e   f
 *            h   i   j   k
 *
 * Also note that this data structure is minimally defined and is only intended to
 * be constructed using the loadTriangle method, which you will implement
 * in this file. We have not included any code to enforce the structure noted above,
 * and you don't have to write any either.
 *
 *
 * See NumberTriangleTest.java for a few basic test cases.
 *
 * Extra: If you decide to solve the Project Euler problems (see main),
 *        feel free to add extra methods to this class. Just make sure that your
 *        code still compiles and runs so that we can run the tests on your code.
 *
 */
public class NumberTriangle {

    private int root;

    private NumberTriangle left;
    private NumberTriangle right;

    public NumberTriangle(int root) {
        this.root = root;
    }

    public void setLeft(NumberTriangle left) {
        this.left = left;
    }


    public void setRight(NumberTriangle right) {
        this.right = right;
    }

    public int getRoot() {
        return root;
    }


    /**
     * [not for credit]
     * Set the root of this NumberTriangle to be the max path sum
     * of this NumberTriangle, as defined in Project Euler problem 18.
     * After this method is called, this NumberTriangle should be a leaf.
     *
     * Hint: think recursively and use the idea of partial tracing from first year :)
     *
     * Note: a NumberTriangle contains at least one value.
     */
    public void maxSumPath() {
        // for fun [not for credit]:
    }


    public boolean isLeaf() {
        return right == null && left == null;
    }


    /**
     * Follow path through this NumberTriangle structure ('l' = left; 'r' = right) and
     * return the root value at the end of the path. An empty string will return
     * the root of the NumberTriangle.
     *
     * You can decide if you want to use a recursive or an iterative approach in your solution.
     *
     * You can assume that:
     *      the length of path is less than the height of this NumberTriangle structure.
     *      each character in the string is either 'l' or 'r'
     *
     * @param path the path to follow through this NumberTriangle
     * @return the root value at the location indicated by path
     *
     */
    public int retrieve(String path) {
        if (path.length() == 0) {
            return this.root;
        }
        char direction = path.charAt(0);
        if (direction == 'l' && this.left != null) {
            return this.left.retrieve(path.substring(1));
        } else if (direction == 'r' && this.right != null) {
            return this.right.retrieve(path.substring(1));
        }
        return -1;
    }


    /**
     * Recurse on the this NumberTriangle structure to fill out the branches.
     * This is a helper method for loadTriangle.
     */
    private void fillBranches(ArrayList<int[]> rows, int depth) throws StackOverflowError {
        // TODO implement this method
        IntSupplier columnSupplier = () -> {
            if (depth == 0) {
                return 0;
            } else {
                for (int i = 0; i < rows.get(depth).length; i++) {
                    if (this.root == rows.get(depth)[i]) {
                        return i;
                    }
                }
                return -1;
            }
        };
        int column = columnSupplier.getAsInt();

        // System.out.println("depth: " + depth + " root: " + this.root + "column: " + column);

        if (depth + 1 == rows.size() - 1) {
            return;
        }

        this.left = new NumberTriangle(rows.get(depth + 1)[column]);
        this.right = new NumberTriangle(rows.get(depth + 1)[column + 1]);

        this.left.fillBranches(rows, depth + 1);
        this.right.fillBranches(rows, depth + 1);
    }

    /** Read in the NumberTriangle structure from a file.
     *
     * You may assume that it is a valid format with a height of at least 1,
     * so there is at least one line with a number on it to start the file.
     *
     * See resources/input_tree.txt for an example NumberTriangle format.
     *
     * @param fname the file to load the NumberTriangle structure from
     * @return the topmost NumberTriangle object in the NumberTriangle structure read from the specified file
     * @throws IOException may naturally occur if an issue reading the file occurs
     */
    public static NumberTriangle loadTriangle(String fname) throws IOException {
        // open the file and get a BufferedReader object whose methods
        // are more convenient to work with when reading the file contents.
        InputStream inputStream = NumberTriangle.class.getClassLoader().getResourceAsStream(fname);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        // create arraylist of int arrays, one for each row of the triangle
        ArrayList<int[]> rows = new ArrayList<int[]>();

        // read each line of the file until there are no more lines
        String line = br.readLine();
        while (line != null) {
            String[] numberStrings = line.split(" ");

            // process the line and read the numbers into an int arraylist
            int[] numbersParsed = new int[numberStrings.length];
            for (int i = 0; i < numberStrings.length; i++) {
                numbersParsed[i] = Integer.parseInt(numberStrings[i]);
            }
            rows.add(numbersParsed);

            //read the next line
            line = br.readLine();
        }
        NumberTriangle top = new NumberTriangle(rows.get(0)[0]);
        br.close();
        top.fillBranches(rows, 0);
        return top;
    }

    public static void main(String[] args) throws IOException,StackOverflowError {

        NumberTriangle mt = NumberTriangle.loadTriangle("input_tree.txt");

        // [not for credit]
        // you can implement NumberTriangle's maxPathSum method if you want to try to solve
        // Problem 18 from project Euler [not for credit]
        mt.maxSumPath();

        // sample output from retrieve method
        System.out.println(mt.retrieve(""));
        System.out.println(mt.retrieve("l"));
        System.out.println(mt.retrieve("r"));
        System.out.println(mt.retrieve("ll"));
        System.out.println(mt.retrieve("lr"));
        System.out.println(mt.retrieve("rr"));
        System.out.println(mt.retrieve("lll"));
        System.out.println(mt.retrieve("llr"));
        System.out.println(mt.retrieve("lrr"));
        System.out.println(mt.retrieve("rrr"));
    }
}
