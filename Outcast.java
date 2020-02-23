import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet){
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns){
        if (nouns.length < 2) throw new IllegalArgumentException("The nouns array has less than 2 nouns");

        int maxDis = 0;
        int dis;
        int outcaseIndex = 0;
        for (int i = 0; i < nouns.length; i++) {
            if (!this.wordNet.isNoun(nouns[i])) {
                throw new IllegalArgumentException("There is a noun that is not in Wordnet");
            } else {
                dis = 0;
                for (int k = 0; k < nouns.length; k++) {
                    dis += this.wordNet.distance(nouns[i], nouns[k]);
                }
                if (dis > maxDis) {
                    maxDis = dis;
                    outcaseIndex = i;
                }
            }
        }

        return nouns[outcaseIndex];
    }

    // test client (see below)
    public static void main(String[] args){
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}
