package game.ui;

import game.AgileSpellChecker;
import game.GuessingGame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

public class Game
{
    private void runUI(List<String> words)
    {
        boolean gameOver = false;

        while(!gameOver)
            gameOver = determineGameState(words);

        playAgain(words);
    }

    private void playAgain(List<String> words)
    {
        System.out.println("Would you like to play again? Enter Y to continue or N to exit.");

        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();

        while (!"Y".equalsIgnoreCase(response))
        {
            if ("N".equalsIgnoreCase(response))
            {
                System.out.println("Thank you for playing. Exiting..");
                System.exit(0);
            }
            else
            {
                System.out.println("Invalid response. Enter Y or N to continue");
                response = scanner.nextLine();
            }
        }

        runUI(words);
    }

    public boolean determineGameState(List<String> words)
    {
        System.out.println("Welcome to Guess Game!");

        GuessingGame guessingGame = new GuessingGame(new AgileSpellChecker());

        String wordToGuess = guessingGame.getWord(words, (int) System.nanoTime());
        String scrambledWord = guessingGame.scrambleWord(wordToGuess, (int) System.nanoTime());

        System.out.println("Enter a guess for the scrambled word: " + scrambledWord);

        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();

        while(!wordToGuess.equalsIgnoreCase(response))
        {
            System.out.println("Your score is: " + guessingGame.score(scrambledWord, response));
            System.out.println("Keep trying. Guess again: ");

            response = scanner.nextLine();
        }

        System.out.println("You have won!");
        System.out.println("The solution is " + wordToGuess);

        return true;
    }

    public List<String> getWordsFromFile() throws IOException
    {
       return Files.lines(Paths.get(System.getProperty("user.dir") + "/inputFile.txt")).collect(toList());
    }

    public static void main(String args[])
    {
        Game game = new Game();
        try
        {
            game.runUI(game.getWordsFromFile());
        }
        catch (IOException ex)
        {
            ex.getMessage();
            System.out.println("Directory does not have a file in which to read in words. Exiting.. ");
            System.exit(0);
        }
    }
}
