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
import com.manukanagala.everydaycalculations.databinding.FragmentDiscountBinding
import java.text.NumberFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [DiscountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiscountFragment : Fragment() {
    private lateinit var binding: FragmentDiscountBinding

    companion object {
        fun newInstance(): DiscountFragment {
            return DiscountFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.setTheme(R.style.Theme_Discount)
        // Inflate the layout for this fragment
        binding = FragmentDiscountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Add Click listener for the button
        binding.calculate.setOnClickListener {
            it.hideKeyboard()
            calculateDiscount()
        }
    }


    override fun onResume() {
        super.onResume()
        binding.originalPriceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        binding.discountPercentageEditText.setOnKeyListener { view, keyCode, _ ->
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
            binding.discountAmount.text = ""
            binding.finalAmount.text = ""
        }
        return false
    }

    /**
     * This method calculates the discounted price by capturing the data from Edit Text
     * Also, sets the text views to the discounted and final amounts
     */
    private fun calculateDiscount() {

        //Hide Keyboard
        // Capture data from Edit Text
        val originalAmountInEditText = binding.originalPriceEditText.text.toString()
        val originalAmount = originalAmountInEditText.toDoubleOrNull()
        // Throw an error if the error value was entered
        if (originalAmount == null) {
            binding.originalPriceEditText.error = getString(R.string.enter_valid_value)
            binding.finalAmount.text = ""
            return
        }

        // Capture data from Edit Text
        val discountInEditText = binding.discountPercentageEditText.text.toString()
        val discountPercentage = discountInEditText.toDoubleOrNull()
        // Throw an error if the error value was entered
        if (discountPercentage == null) {
            binding.discountPercentageEditText.error = getString(R.string.enter_valid_value)
            binding.discountAmount.text = ""
            return
        }

        val discount = discountPercentage / 100

        // Calculate the Discount and Original Amount
        val discountAmount = discount * originalAmount
        val finalAmount = originalAmount - discountAmount

        // Format the amount to the current currency
        val locale = Locale.getDefault()
        val formattedDiscountAmount =
            NumberFormat.getCurrencyInstance(locale).format(discountAmount)
        val formattedFinalAmount = NumberFormat.getCurrencyInstance(locale).format(finalAmount)

        // Set the text views to the formatted values
        binding.discountAmount.text = formattedDiscountAmount
        binding.finalAmount.text = formattedFinalAmount

    }

    // This method hides the keyboard
    fun View.hideKeyboard() {
        val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(windowToken, 0)
    }
}