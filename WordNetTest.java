import org.junit.Assert;
import org.junit.Test;


public class WordNetTest {
    private WordNet test;
    public WordNetTest(){
        this.test = new WordNet("synsets.txt", "hypernyms.txt");
    }

    @Test
    public void sca() {
        String ancestor = this.test.sca("punk", "waif");
        Assert.assertEquals("juvenile juvenile_person", ancestor);
    }

    @Test
    public void distance() {
        int distance = this.test.distance("punk", "waif");
        Assert.assertEquals(4, distance);
    }
}
