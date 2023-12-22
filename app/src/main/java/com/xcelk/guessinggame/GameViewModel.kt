package com.xcelk.guessinggame
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val words = listOf("Android", "Activity", "Fragment")
    val secretWord = words.random().uppercase()
    var secretWordDisplay = ""
    var correctGuesses = ""
    var incorrectGuesses = ""
    var livesLeft = 8

    init {
        secretWordDisplay = deriveSecretWordDisplay()
    }

    public fun deriveSecretWordDisplay() : String {
        var display  = ""
        secretWord.forEach{
            display += checkLetter(it.toString())
        }
        return display
    }

    public fun checkLetter(str: String) = when (correctGuesses.contains(str)){
        true -> str
        false -> "_"
    }

    public fun makeGuess(guess: String){
        if(guess.length == 1){
            if(secretWord.contains(guess)){
                correctGuesses += guess
                secretWordDisplay = deriveSecretWordDisplay()
            } else {
                incorrectGuesses += "$guess "
                livesLeft--
            }
        }
    }

    public fun isWon() = secretWord.equals(secretWordDisplay, true)
    public fun isLost() = livesLeft <= 0

    public fun wonLostMessage() : String{
        var message = ""
        if (isWon()) message = "You Won!"
        else if (isLost()) message = "You Lost!"
        message += "The word was $secretWord."
        return message
    }
}