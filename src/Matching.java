import java.util.LinkedList;

/**
 * Created by DarshanNarayana on 4/18/2016.
 */
public class Matching {

    static int matching(Graph g) {
        boolean isBipartite = checkBipartite(g);
        int msize = 0;
        if (!isBipartite) {
            return msize;
        }
        msize = greedyMaximalMatching(g);
        msize = findMaximalMatching(g, msize);
        return msize;
    }

    private static int findMaximalMatching(Graph g, int msize) {
        boolean noAugPath = false;
        while (!noAugPath) {
            noAugPath = true;
            LinkedList<Vertex> queue = new LinkedList<Vertex>();
            for (Vertex v : g) {
                v.seen = false;
                v.parent = null;

                if (v.mate == null && v.isOuter) {
                    v.seen = true;
                    queue.add(v);
                }
            }
            while (!queue.isEmpty()) {
                Vertex u = queue.remove();
                for (Edge e : u.Adj) {
                    Vertex v = e.otherEnd(u);
                    if (!v.seen) {
                        v.parent = u;
                        v.seen = true;
                        if (v.mate == null) {
                            msize =    processAugPath(v, msize);
                            noAugPath = false;
                            break;
                        } else {
                            Vertex x = v.mate;
                            x.seen = true;
                            x.parent = v;
                            queue.add(x);
                        }
                    }
                }
            }

        }


        return msize;
    }

    private static int processAugPath(Vertex v, int msize) {
        Vertex p = v.parent;
        Vertex x = p.parent;
        p.mate = v;
        v.mate = p;
        while (x != null) {
            Vertex nmx = x.parent;
            Vertex y = nmx.parent;
            x.mate = nmx;
            x.mate = nmx;
            x = y;
        }
        msize ++;

        return msize;

    }

    private static int greedyMaximalMatching(Graph g) {
        // Initialize all vertices mate to be null
        int msize = 0;
        for ( Vertex v : g) {
            v.mate = null;
        }

        for (Vertex v : g) {
            if (v.mate == null) {
                for (Edge e : v.Adj) {
                    if (e.otherEnd(v).mate == null) {
                        e.otherEnd(v).mate = v;
                        v.mate = e.otherEnd(v);
                        msize++;
                        break;
                    }
                }
            }
        }
        return msize;
    }

    private static boolean checkBipartite(Graph g) {
        runBFS(g);
        //TODO: improve this to avoid checking twice for every edge
        for (Vertex v : g) {
            for (Edge e : v.Adj) {
                if (e.otherEnd(v).isOuter == v.isOuter) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void runBFS(Graph g) {
        LinkedList<Vertex> queue = new LinkedList<Vertex>();
        for (Vertex v : g) {
            if (!v.seen) {
                queue.add(v);
                while (!queue.isEmpty()) {
                    Vertex current = queue.remove();
                    current.seen = true;
                    current.isOuter = true;
                    for (Edge e : current.Adj) {
                        Vertex inner = e.otherEnd(current);
                        inner.seen = true;
                        for ( Edge e1 : inner.Adj) {
                            if (!e1.otherEnd(inner).seen) {
                                queue.add(e1.otherEnd(inner));
                            }
                        }
                    }
                }
            }
        }
    }

}
