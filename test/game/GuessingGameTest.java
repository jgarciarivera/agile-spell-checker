package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class GuessingGameTest
{
    GuessingGame guessingGame;
    String word;

    @BeforeEach
    void setUp()
    {
        word = "monkey";
        SpellingService spellingService = Mockito.mock(SpellingService.class);
        when(spellingService.isSpellingCorrect(anyString())).thenReturn(true);
        guessingGame = new GuessingGame(spellingService);
    }

    @Test
    void canary()
    {
        assertTrue(true);
    }

    @Test
    void scrambleWordReturnsAWordOfSameLength()
    {
        String scrambledWord = guessingGame.scrambleWord(word, 23);
      
        assertEquals(word.length(), scrambledWord.length());
    }

    @Test
    void scrambleWordReturnsAnEmptyStringIfSentAnEmptyString()
    {
        assertEquals("", guessingGame.scrambleWord("", 23));
    }

    @Test
    void scrambleWordReturnsWordWithSameLetters()
    {
        String sortedWord = Stream.of(word.split("")).sorted().collect(Collectors.joining(""));

        String scrambledWord = guessingGame.scrambleWord(word, 23);

        String sortedScrambledWord = Stream.of(scrambledWord.split("")).sorted().collect(Collectors.joining(""));

        assertEquals(sortedScrambledWord, sortedWord);
    }

    @Test
    void scrambleWordWithSameSeedReturnsSameScrambledWord()
    {
        String scrambledWordWithSameSeed = guessingGame.scrambleWord(word, 23);

        String sameScrambledWordWithSameSeed = guessingGame.scrambleWord(word, 23);

        assertEquals(scrambledWordWithSameSeed, sameScrambledWordWithSameSeed);
    }

    @Test
    void scrambleWordWithDifferentSeedReturnsDifferentScrambledWord()
    {
        String scrambledWordWithUniqueSeed = guessingGame.scrambleWord(word, 23);

        String sameScrambledWordWithUniqueSeed = guessingGame.scrambleWord(word, 24);

        assertNotEquals(scrambledWordWithUniqueSeed, sameScrambledWordWithUniqueSeed);
    }

    @Test
    void scoreAGuessReturnsAScoreForAGuess()
    {
        assertEquals(10, guessingGame.score("oekmny", "monkey"));
    }

    @Test
    void scoreAGuessReturnsNpScoreForAWrongGuess()
    {
        assertEquals(0, guessingGame.score("oekmny", "moon"));
    }

    @Test
    void scoreAGuessReturnsAZeroIfAnEmptyStringIsSentAsGuess()
    {
        assertEquals(0, guessingGame.score("", "monkey"));
    }

    @Test
    void scoreAGuessReturnsAScoreOfTwoForMatchingOneConsonant()
    {
        assertEquals(2, guessingGame.score("oekmny", "k"));
    }

    @Test
    void scoreAGuessReturnsAZeroIfNonMatchingStringIsSentAsGuess()
    {
        assertEquals(0, guessingGame.score("oekmny", "zaza"));
    }

    @Test
    void scoreAGuessReturnsAScoreOfOneForMatchingOneVowel()
    {
        assertEquals(1, guessingGame.score("oekmny", "o"));
    }

    @Test
    void scoreAGuessReturnsAScoreOfThreeForMatchingOneVowelOneConsonant()
    {
        assertEquals(3, guessingGame.score("oekmny", "on"));
    }

    @Test
    void scoreAGuessReturnsProperScoreGivenAMixedCase()
    {
        assertEquals(0, guessingGame.score("moon", "My"));
    }

    @Test
    void scoreAGuessReturnsExpectedScoreGivenLoweredCase()
    {
        assertEquals(0, guessingGame.score("moon", "my"));
    }

    @Test
    void scoreForCorrectSpelling()
    {
        assertEquals(10, guessingGame.score("oekmny", "monkey"));
    }

    @Test
    void scoreForWhenSpellingServiceReturnsFalseIsZero()
    {
        SpellingService spellingService = Mockito.mock(SpellingService.class);

        GuessingGame guessingGame = new GuessingGame(spellingService);

        when(spellingService.isSpellingCorrect(anyString())).thenReturn(false);

        assertEquals(0, guessingGame.score("oekmny", "money"));
    }

    @Test
    void scoreWhenCallToSpellCheckerFails()
    {
        SpellingService spellingService = Mockito.mock(SpellingService.class);
        String message = "Network failure";
        when(spellingService.isSpellingCorrect("money")).thenThrow(new RuntimeException(message));
        guessingGame = new GuessingGame(spellingService);

        assertThrows(RuntimeException.class, () -> guessingGame.score(word, "money"), message);
    }

    @Test
    void getWordReturnsRandomWordFromList()
    {
        assertEquals("monkey", guessingGame.getWord(Arrays.asList(word), 1));
    }

    @Test
    void getWordReturnsWordFromTheGivenList()
    {
        List<String> test = Arrays.asList("monkey", "fruit", "banana", "apple", "cosmopolitan");
        String wordFromTestList = guessingGame.getWord(test, 5);

        assertTrue(test.contains(wordFromTestList));
    }

    @Test
    void getWordReturnsSameWordWhenSeedIsSame()
    {
        String wordFromList = guessingGame.getWord(Arrays.asList("monkey", "fruit", "banana", "apple", "cosmopolitan"), 5);
        String wordFromListToCompare = guessingGame.getWord(Arrays.asList("monkey", "fruit", "banana", "apple", "cosmopolitan"), 5);

        assertEquals(wordFromList, wordFromListToCompare);
    }

    @Test
    void getWordReturnsDifferentWordWhenSeedIsDifferent()
    {
        String wordFromList = guessingGame.getWord(Arrays.asList("monkey", "fruit", "banana", "apple", "cosmopolitan"), 5);
        String wordFromListToCompare= guessingGame.getWord(Arrays.asList("monkey", "fruit", "banana", "apple", "cosmopolitan"), 6);

        assertNotEquals(wordFromList, wordFromListToCompare);
    }
}
