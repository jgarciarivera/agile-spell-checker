package game;

import java.util.*;

import static java.util.stream.Collectors.*;
import java.util.stream.Stream;

public class GuessingGame
{
    SpellingService spellingService;

    public GuessingGame (SpellingService service)
    {
        spellingService = service;
    }

    public String scrambleWord(String wordToScramble, int seed)
    {
        List<String> letters = Stream.of(wordToScramble.split("")).collect(toList());
        Collections.shuffle(letters, new Random(seed));
        return String.join("", letters);
    }

    public int score(String scrambledWord, String guess)
    {
        if(!spellingService.isSpellingCorrect(guess))
            return 0;

        String guessLowerCase = guess.toLowerCase();

        Map<String, Long> frequencyOfLettersInGuess = Stream.of(guessLowerCase.split(""))
          .collect(groupingBy(letter -> letter, counting()));

        Map<String, Long> frequencyOfLettersInWord = Stream.of(scrambledWord.split(""))
          .collect(groupingBy(letter -> letter, counting()));

        if(frequencyOfLettersInGuess.keySet().stream()
          .filter(letter -> frequencyOfLettersInGuess.get(letter) > frequencyOfLettersInWord.computeIfAbsent(letter, key -> 0L))
          .count() > 0) return 0;

        List<String> VOWELS = Arrays.asList("a", "e", "i", "o", "u");

        return Stream.of(guessLowerCase.split(""))
          .mapToInt(letter -> VOWELS.contains(letter) ? 1 : 2)
          .sum();
    }

    public String getWord(List<String> words, int seed)
    {
        int index = new Random(seed).nextInt(words.size());
        return words.get(index);
    }
}