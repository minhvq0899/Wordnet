import org.junit.Assert;
import org.junit.Test;

public class OutcastTest {
    private WordNet wordNet;
    private Outcast outCast;
    private String[] words = {"water", "soda", "bed", "orange_juice", "milk", "apple_juice", "tea", "coffee"};

    public OutcastTest(){
        this.wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        this.outCast = new Outcast(this.wordNet);
    }

    @Test
    public void outcast() {
        String blackSheep = this.outCast.outcast(this.words);
        Assert.assertEquals("bed", blackSheep);
    }
}
