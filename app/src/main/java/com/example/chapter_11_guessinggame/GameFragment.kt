package com.example.chapter_11_guessinggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.chapter_11_guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        viewModel.getIncorrectGuesses.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.incorrectGuesses.text = "Incorrect guesses $newValue"
//        })
//
//        viewModel.getLivesLeft.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.lives.text = "You have $newValue lives left"
//        })
//
//        viewModel.getSecretWordDisplay.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.word.text = newValue
//        })

        viewModel.getGameOver.observe(viewLifecycleOwner, Observer { newValue ->
            if (newValue) {
                val action =
                    GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        })

        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}