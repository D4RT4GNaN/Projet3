package main.java;

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
        moreOrLess.compareNumber(1,1, 1, 20);
        assertEquals("Bravo! Vous avez trouvez le nombre secret en seulement 1 tours\n", outContent.toString().replace("\r\n", "\n"));
    }

    @Test
    public void Given_Less_When_CompareNumber_Then_DisplayLessSentence () {
        moreOrLess.compareNumber(2,1, 1, 20);
        assertEquals("C'est moins\n", outContent.toString().replace("\r\n", "\n"));
    }

    @Test
    public void Given_More_When_CompareNumber_Then_DisplayMoreSentence () {
        moreOrLess.compareNumber(1,2, 1, 20);
        assertEquals("C'est plus\n", outContent.toString().replace("\r\n", "\n"));
    }

    @Test
    public void Given_Counter_When_CompareNumber_Then_DisplayCongratulateSentenceWithCorrectCounter () {
        moreOrLess.compareNumber(2,2, 3, 20);
        assertEquals("Bravo! Vous avez trouvez le nombre secret en seulement 3 tours\n", outContent.toString().replace("\r\n", "\n"));
    }

    @Test
    public void Given_OverCounter_When_runMoreOrLess_Then_DisplayFailedSentence () {
        moreOrLess.compareNumber(1,2, 20, 20);
        assertEquals("Perdu!! le nombre secret était 2\n", outContent.toString().replace("\r\n", "\n"));
    }
}