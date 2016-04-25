import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by DarshanNarayana on 4/21/2016.
 */
public class LP5Lev1 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readGraph(in, false);   // read undirected graph from stream "in"

        int result = Matching.matching(g);
        if (result == 0) {
            System.out.println("G is not bipartite");
        } else {
            System.out.println(result);
        }

    }
}
