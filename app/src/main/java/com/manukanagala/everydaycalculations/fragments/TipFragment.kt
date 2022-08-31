package com.manukanagala.everydaycalculations.fragments

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.manukanagala.everydaycalculations.R
import com.manukanagala.everydaycalculations.databinding.FragmentTipBinding
import java.text.NumberFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [TipFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TipFragment : Fragment() {
    private lateinit var binding: FragmentTipBinding

    companion object {
        fun newInstance(): TipFragment {
            return TipFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.setTheme(R.style.Theme_Tip)
        // Inflate the layout for this fragment
        binding = FragmentTipBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Add Click listener for the button
        binding.calculate.setOnClickListener {
            it.hideKeyboard()
            calculateTipAmount()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.originalAmountEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        binding.tipPercentageEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    /**
     * This method closes the key board once the user clicks on the Enter button
     * Also resets the Text views once the user clicks on Delete button
     */
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {

        // Reset the text view on Delete button click
        if (keyCode == KeyEvent.KEYCODE_DEL || keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_CLEAR) {
            // Set the text views to the formatted values
            // Set the text views to the formatted values
            binding.tipAmount.text = ""
            binding.finalAmount.text = ""
        }
        return false
    }

    /**
     * This method calculates the discounted price by capturing the data from Edit Text
     * Also, sets the text views to the discounted and final amounts
     */
    private fun calculateTipAmount() {

        // Capture data from Edit Text
        val originalAmountInEditText = binding.originalAmountEditText.text.toString()
        val originalAmount = originalAmountInEditText.toDoubleOrNull()
        // Throw an error if the error value was entered
        if (originalAmount == null) {
            binding.originalAmountEditText.error = getString(R.string.enter_valid_value)
            binding.finalAmount.text = ""
            return
        }

        // Capture data from Edit Text
        val discountInEditText = binding.tipPercentageEditText.text.toString()
        val discountPercentage = discountInEditText.toDoubleOrNull()
        // Throw an error if the error value was entered
        if (discountPercentage == null) {
            binding.tipPercentageEditText.error = getString(R.string.enter_valid_value)
            binding.tipAmount.text = ""
            return
        }

        val tip = discountPercentage / 100

        // Calculate the Discount and Original Amount
        val tipAmount = tip * originalAmount
        val finalAmount = originalAmount + tipAmount

        // Format the amount to the current currency
        val locale = Locale.getDefault()
        val formattedDiscountAmount = NumberFormat.getCurrencyInstance(locale).format(tipAmount)
        val formattedFinalAmount = NumberFormat.getCurrencyInstance(locale).format(finalAmount)

        // Set the text views to the formatted values
        binding.tipAmount.text = formattedDiscountAmount
        binding.finalAmount.text = formattedFinalAmount

    }

    // This method hides the keyboard
    fun View.hideKeyboard() {
        val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(windowToken, 0)
    }

}