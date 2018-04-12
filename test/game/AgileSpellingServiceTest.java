package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class AgileSpellingServiceTest
{
    SpellingService spellingService = new AgileSpellChecker();

    @BeforeEach
    public void setUp()
    {
        spellingService = new AgileSpellChecker();
    }

    @Test
    public void spellingServiceReturnsTrueForProperSpelling()
    {
        assertTrue(spellingService.isSpellingCorrect("dog"));
    }

    @Test
    public void spellingServiceReturnsFalseForImproperSpelling()
    {
        assertFalse(spellingService.isSpellingCorrect("dgo"));
    }

   @Test
   public void spellcheckerPropagatesNetworkFailure() throws IOException
   {
     AgileSpellChecker spellChecker = spy(AgileSpellChecker.class);
     String message = "Network failure";
     when(spellChecker.getResponseFromURL(anyString())).thenThrow(new IOException(message));
     
     assertThrows(RuntimeException.class, () -> spellChecker.isSpellingCorrect("whatever"), message);
   }
}
