package changheng;

import static org.junit.Assert.*;

import org.junit.Test;

public class TennisGameTest {
    @Test
    public void score_test() {
        assertEquals(givenScore(0, 0), "Love All");
    }

    @Test
    public void socre_1_test() {
        assertEquals(givenScore(0, 1), "Love Fifteen");
    }

    @Test
    public void socre_2_test() {
        assertEquals(givenScore(1, 1), "Fifteen All");
    }

    @Test
    public void socre_3_test() {
        assertEquals(givenScore(2, 1), "Thirty Fifteen");
    }

    @Test
    public void socre_4_test() {
        assertEquals(givenScore(3, 1), "Forty Fifteen");
    }

    @Test
    public void socre_5_test() {
        assertEquals(givenScore(4, 0), "Ace win");
    }

    @Test
    public void socre_6_test() {
        assertEquals(givenScore(3, 3), "Deuce");
    }

    @Test
    public void socre_7_test() {
        assertEquals(givenScore(4, 4), "Deuce");
    }

    @Test
    public void socre_8_test() {
        assertEquals(givenScore(4, 3), "Ace adv");
    }

    @Test
    public void socre_9_test() {
        assertEquals(givenScore(5, 3), "Ace win");
    }

    private TennisGame givenGame(int a, int b) {
        TennisGame game = new TennisGame(a, b);
        game.setPlayerA("Ace");
        game.setPlayerB("Ron");
        return game;
    }

    private String givenScore(int a, int b) {
        return givenGame(a, b).score();
    }
}
