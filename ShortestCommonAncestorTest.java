
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import org.junit.Assert;
import org.junit.Test;

public class ShortestCommonAncestorTest {
    private ShortestCommonAncestor sca;
    private Queue<Integer> A = new Queue<Integer>();
    private Queue<Integer> B = new Queue<Integer>();

    // we will use digraph25 for testing
    public ShortestCommonAncestorTest() {
        In digraph25 = new In("digraph25.txt");
        Digraph G = new Digraph(digraph25);
        this.sca = new ShortestCommonAncestor(G);
    }

    @Test
    public void length() {
        int length1 = sca.length(17, 6); // 4, 2
        int length2 = sca.length(3, 5); // 4, 0
        int length3 = sca.length(21, 24); // 10, 0

        Assert.assertEquals(4, length1);
        Assert.assertEquals(4, length2);
        Assert.assertEquals(10, length3);
    }

    @Test
    public void ancestor() {
        int ancestor1 = sca.ancestor(17, 6);
        int ancestor2 = sca.ancestor(3, 5);
        int ancestor3 = sca.ancestor(21, 24);

        Assert.assertEquals(2, ancestor1);
        Assert.assertEquals(0, ancestor2);
        Assert.assertEquals(0, ancestor3);
    }

    @Test
    public void lengthSubset() {
        this.A.enqueue(13);
        this.A.enqueue(23);
        this.A.enqueue(24);
        this.B.enqueue(6);
        this.B.enqueue(16);
        this.B.enqueue(17);

        int lengthSubset = sca.lengthSubset(A, B);

        Assert.assertEquals(4, lengthSubset);
    }

    @Test
    public void ancestorSubset() {
        this.A.enqueue(13);
        this.A.enqueue(23);
        this.A.enqueue(24);
        this.B.enqueue(6);
        this.B.enqueue(16);
        this.B.enqueue(17);
        int ancestorSubset = sca.ancestorSubset(this.A, this.B);
        Assert.assertEquals(3, ancestorSubset);
    }

}
