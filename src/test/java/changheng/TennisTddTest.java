package changheng;

import static org.junit.Assert.*;

import org.junit.Test;

public class TennisTddTest {
    @Test
    public void test_love_all() {
        assertEquals("Love All", givenTennis().score());
    }

    @Test
    public void test_forteen_love() {
        TennisTdd game = givenTennis();
        game.addScoreA();
        assertEquals("Fifteen Love", game.score());
    }

    @Test
    public void test_thirty_love() {
        TennisTdd game = givenTennis();
        game.addScoreA();
        game.addScoreA();
        assertEquals("Thirty Love", game.score());
    }

    @Test
    public void test_forty_love() {
        TennisTdd game = givenTennis();
        game.addScoreA();
        game.addScoreA();
        assertEquals("Thirty Love", game.score());
    }

    @Test
    public void test_fifteen_all() {
        TennisTdd game = givenTennis();
        game.addScoreA();
        game.addScoreB();
        assertEquals("Fifteen All", game.score());
    }

    @Test
    public void test_thirty_all() {
        TennisTdd game = givenTennis();
        game.addScoreA();
        game.addScoreA();
        game.addScoreB();
        game.addScoreB();
        assertEquals("Thirty All", game.score());
    }

    @Test
    public void test_deuce() {
        TennisTdd game = givenTennis();
        game.addScoreA();
        game.addScoreA();
        game.addScoreA();
        game.addScoreB();
        game.addScoreB();
        game.addScoreB();
        assertEquals("Deuce", game.score());
    }

    @Test
    public void test_a_adv() {
        TennisTdd game = givenTennis();
        game.addScoreA();
        game.addScoreA();
        game.addScoreA();
        game.addScoreA();
        game.addScoreB();
        game.addScoreB();
        game.addScoreB();
        assertEquals("A adv", game.score());
    }

    @Test
    public void test_b_adv() {
        TennisTdd game = givenTennis();
        game.addScoreA();
        game.addScoreA();
        game.addScoreA();
        game.addScoreB();
        game.addScoreB();
        game.addScoreB();
        game.addScoreB();
        assertEquals("B adv", game.score());
    }

    @Test
    public void test_a_win() {
        TennisTdd game = givenTennis();
        game.addScoreA();
        game.addScoreA();
        game.addScoreA();
        game.addScoreA();
        assertEquals("A win", game.score());
    }

    @Test
    public void test_5_3_a_win() {
        TennisTdd game = givenTennis();
        game.addScoreA();
        game.addScoreA();
        game.addScoreA();
        game.addScoreA();
        game.addScoreA();
        game.addScoreB();
        game.addScoreB();
        game.addScoreB();
        assertEquals("A win", game.score());
    }

    public TennisTdd givenTennis() {
        return new TennisTdd();
    }
    public TennisTdd givenTennis(int a, int b) {
        return new TennisTdd(a, b);
    }
}
