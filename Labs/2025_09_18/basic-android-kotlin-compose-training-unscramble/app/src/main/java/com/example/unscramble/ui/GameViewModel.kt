package com.example.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.update

data class GameUiState(
    val currentScrambledWord: String? = null,
    val currentWordCount: Int = 1,
    val score: Int = 0,
    val isGuessedWordWrong: Boolean = false,
    val isGameOver: Boolean = false
)

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    private lateinit  var availableWords: MutableList<String>
    private lateinit var currentWord: String

    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    var userGuess by mutableStateOf("")
        private set

    init {
        resetGame()
    }

    fun resetGame() {
        availableWords = allWords.toMutableList()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()

        tempWord.shuffle()

        while (String(tempWord) == word) {
            tempWord.shuffle()
        }

        return String(tempWord)
    }

    private fun pickRandomWordAndShuffle(): String? {
        if (availableWords.isEmpty()) {
            return null
        }

        val index = availableWords.indices.random()
        currentWord = availableWords.removeAt(index)

        return shuffleCurrentWord(currentWord)
    }

    private fun updateGameScore(updatedScore: Int) {
        if (_uiState.value.currentWordCount == MAX_NO_OF_WORDS) {
            _uiState.update { currentState ->
                currentState.copy(
                    score = updatedScore,
                    isGuessedWordWrong = false,
                    isGameOver = true
                )
            }
        } else{
            _uiState.update { currentState ->
                currentState.copy(
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc(),
                    score = updatedScore,
                    isGuessedWordWrong = false,
                )
            }
        }
    }

    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameScore(updatedScore)
        } else {
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }

        updateUserGuess("")
    }

    fun skipWord() {
        updateGameScore(_uiState.value.score)
        updateUserGuess("")
    }
}