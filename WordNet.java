// Minh Q. Vu

import edu.princeton.cs.algs4.*;

public class WordNet {
    private Digraph diGraph;
    private ST<String, Bag<Integer>> synsetIndex = new ST<String, Bag<Integer>>();
    private ST<Integer, String> synsetID = new ST<Integer, String>(); // id of every synset
    private ShortestCommonAncestor SCA;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException("Input is null");

        In synset = new In(synsets);
        In hyper = new In(hypernyms);

        // construct a diGraph
        while(synset.hasNextLine()){
            // this array will contain one line of synset
            String[] synsetOneLine = synset.readLine().split(",");
            // this array will contain different nouns in that line
            String[] nouns = synsetOneLine[1].split(" ");

            for (String n : nouns){
                Bag<Integer> id = new Bag<Integer>();
                // if the ST has already had that noun, get the bag of integer of that noun
                if (this.synsetIndex.contains(n)) id = this.synsetIndex.get(n);
                id.add(Integer.parseInt(synsetOneLine[0]));
                this.synsetIndex.put(n, id);
            }
            // assign the id to that synset
            this.synsetID.put(Integer.parseInt(synsetOneLine[0]), synsetOneLine[1]);
        }

        // initialize a new digraph with V = the size of our id ST
        this.diGraph = new Digraph(this.synsetID.size());

        // add edges
        while (hyper.hasNextLine()) {
            String[] relation = hyper.readLine().split(",");

            for (int i = 1; i < relation.length; i++) {
                this.diGraph.addEdge(Integer.parseInt(relation[0]), Integer.parseInt(relation[i]));
            }
        }

        this.SCA = new ShortestCommonAncestor(diGraph);
    }

    // all WordNet nouns
    public Iterable<String> nouns(){
        return this.synsetIndex;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if (word == null) throw new IllegalArgumentException("String argument is null");
        return this.synsetIndex.contains(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2){
        if (noun1 == null || noun2 == null) throw new IllegalArgumentException("String argument is null");
        if (!isNoun(noun1) || !isNoun(noun2)) throw new IllegalArgumentException("Argument is not a Word Net noun");
        Bag<Integer> A = this.synsetIndex.get(noun1);
        Bag<Integer> B = this.synsetIndex.get(noun2);
        // get the id of the ancestor
        int scaID = this.SCA.ancestorSubset(A, B);
        // convert that id into string
        return this.synsetID.get(scaID);
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2){
        if (noun1 == null || noun2 == null) throw new IllegalArgumentException("String argument is null");
        if (!isNoun(noun1) || !isNoun(noun2)) throw new IllegalArgumentException("Argument is not a Word Net noun");
        Bag<Integer> A = this.synsetIndex.get(noun1);
        Bag<Integer> B = this.synsetIndex.get(noun2);

        return this.SCA.lengthSubset(A, B);
    }

    // unit testing (required)
    public static void main(String[] args){
        WordNet test = new WordNet(args[0], args[1]);
        StdOut.println("SCA: " + test.sca("punk", "waif") + ". Distance: " + test.distance("punk", "waif"));
    }

}
