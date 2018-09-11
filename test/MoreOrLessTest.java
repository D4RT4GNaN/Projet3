import main.java.MoreOrLess;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoreOrLessTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
    }

    MoreOrLess moreOrLess = new MoreOrLess();

    @Test
    public void Given_Congratulate_When_CompareNumber_Then_DisplayCongratulateSentence () {
        moreOrLess.compareNumber(1,1);
        assertEquals("Bravo! Vous avez trouvez le nombre secret\n", outContent.toString().replace("\r\n", "\n"));
    }

    @Test
    public void Given_Less_When_CompareNumber_Then_DisplayLessSentence () {
        moreOrLess.compareNumber(2,1);
        assertEquals("C'est moins\n", outContent.toString().replace("\r\n", "\n"));
    }

    @Test
    public void Given_More_When_CompareNumber_Then_DisplayMoreSentence () {
        moreOrLess.compareNumber(1,2);
        assertEquals("C'est plus\n", outContent.toString().replace("\r\n", "\n"));
    }
}
