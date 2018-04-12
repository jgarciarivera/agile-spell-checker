package game;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class AgileSpellChecker implements SpellingService
{
  public boolean isSpellingCorrect(String word)
  {
    try
    {
      return getResponseFromURL(word).equals("true");
    }
    catch(Exception ex)
    {
      throw new RuntimeException(ex.getMessage());
    }
  }

   public String getResponseFromURL(String word) throws IOException
   {
     URL url = new URL("http://agile.cs.uh.edu/spell?check=" + word);
     Scanner scanner = new Scanner(url.openStream());
     
     return scanner.next();
   }
}