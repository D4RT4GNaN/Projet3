package main.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    Menu menu = new Menu();

    @Test
    public void Given_PlayerEnter1_When_AskingNumberForChoosingGame_Then_StartMoreOrLessStandard () {
        menu.askGame(1);
        assertEquals(1, menu.gameSelected);
    }

    @Test
    public void Given_PlayerEnter3_When_AskingNumberForChoosingGame_Then_StartMoreOrLessDev () {
        menu.askGame(3);
        assertEquals(3, menu.gameSelected);
    }
}