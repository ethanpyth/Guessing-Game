package com.xcelk.guessinggame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var viewModel: GameViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        viewModel.gameOver.observe(viewLifecycleOwner) { newValue ->
            if (newValue) {
                val action =
                    GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view?.findNavController()?.navigate(action)
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme{
                    Surface{
                        view?.let {
                            GameFragmentContent(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

@Composable
fun WordGuess(viewModel: GameViewModel){
    val display = viewModel.secretWordDisplay.observeAsState()
    display.value?.let {
        Text(text = it, letterSpacing = 0.1.em, fontSize = 36.sp)
    }
}

@Composable
fun WordInputField(letter: String, onClick: (String) -> Unit){
    TextField(value = letter, onValueChange = onClick, label = { Text(text = "Guess a letter")})
}

@Composable
fun IncorrectGuessesText(viewModel: GameViewModel){
    val incorrectgGuesses = viewModel.incorrectGuesses.observeAsState()
    incorrectgGuesses.value?.let {
        Text(text = stringResource(id = R.string.incorrect_guesses, it), textAlign = TextAlign.Left)
    }
}

@Composable
fun LivesLeftText(viewModel: GameViewModel){
    val livesLeft = viewModel.livesLeft.observeAsState()
    livesLeft.value?.let {
        Text(text = stringResource(id = R.string.lives_left), textAlign = TextAlign.Left)
    }
}

@Composable
fun GuessButton(label: String, onClick: () -> Unit){
    Button(onClick = onClick) {
        Text(text = label)
    }
}

@Composable
fun FinishGameButton(label: String, onClick: () -> Unit){
    Button(onClick = onClick) {
        Text(text = label)
    }
}

@Composable
fun GameFragmentContent(viewModel: GameViewModel){
    val guess = remember{ mutableStateOf("") }

    Column(modifier =Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        WordGuess(viewModel)
        IncorrectGuessesText(viewModel = viewModel)
        WordInputField(guess.value){ guess.value = it }
        LivesLeftText(viewModel = viewModel)
        Row {
            GuessButton(label = "Guess") {
                viewModel.makeGuess(guess.value.uppercase())
                guess.value = ""
            }
            FinishGameButton(label = "Finish Game") {
                viewModel.finishGame()
            }
        }
    }
}