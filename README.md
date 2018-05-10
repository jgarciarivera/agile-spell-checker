# guessing-game
A console program in which the user receives a scrambled word, guesses the answer, and the game connects to a spell checker web service returning a score.

The application reads a text file of words, randomly selects one, and then scrambles it. A user receives the shuffled word and interacts with the console to guess and enter the corresponding unscrambled word. The program then connects to a web URL wherein the spelling is checked. If the word does not exist a user receives no points. Otherwise, a correctly spelled word is given one point for each correct vowel and two for each correct consonant. 
