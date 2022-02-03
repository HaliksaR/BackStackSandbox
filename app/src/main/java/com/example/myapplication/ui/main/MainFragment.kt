package com.example.myapplication.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlin.random.Random

class MainFragment : Fragment() {

    companion object {

        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private val number: String by lazy { requireNotNull(arguments?.getString("number")) }
    private val created: Int by lazy { requireNotNull(arguments?.getInt("created")) }
    private val fragments: Int by lazy { requireNotNull(arguments?.getInt("fragments")) }

    private val color =
        Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))

    fun putArgs(number: String, created: Int, fragments: Int) {
        arguments = Bundle().apply {
            putString("number", number)
            putInt("created", created)
            putInt("fragments", fragments)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("MainFragment", "$number onCreateView")
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("MainFragment", "$number onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ConstraintLayout>(R.id.main).setBackgroundColor(color)

        view.findViewById<TextView>(R.id.textView).text = """
            current $number
            created $created
            fragments $fragments
        """.trimIndent()

        view.findViewById<Button>(R.id.button).setOnClickListener {
            (activity as MainActivity).add()
        }
        view.findViewById<Button>(R.id.button2).setOnClickListener {
            (activity as MainActivity).replace()
        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (activity as MainActivity).pop()
                }
            })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("MainFragment", "$number onActivityCreated")
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.number = number
    }

    override fun onDestroyView() {
        Log.d("MainFragment", "$number onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("MainFragment", "$number onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("MainFragment", "$number onDetach")
        super.onDetach()
    }
}