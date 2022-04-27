package com.manukanagala.everydaycalculations

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.manukanagala.everydaycalculations.databinding.ActivityMainBinding
import java.text.NumberFormat

/**
 * This activity captures the input values from the user and calculates the discount
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Add Key Listeners for Edit text
        binding.originalPriceEditText.setOnKeyListener { view, keyCode, _ ->  handleKeyEvent(view, keyCode)}
        binding.discountPercentageEditText.setOnKeyListener { view, keyCode, _ ->  handleKeyEvent(view, keyCode)}

        //Add Click listener for the button
        binding.calculate.setOnClickListener { calculateDiscount() }
    }

    /**
     * This method closes the key board once the user clicks on the Enter button
     * Also resets the Text views once the user clicks on Delete button
     */
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean{
        if (keyCode == KeyEvent.KEYCODE_ENTER){

            // Hide the Keyboard once the user clicks on Enter button
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }

        // Reset the text view on Delete button click
        if (keyCode == KeyEvent.KEYCODE_DEL){
            binding.discountedAmount.text = ""
            binding.finalAmount.text = ""
        }
        return false
    }

    /**
     * This method calculates the discounted price by capturing the data from Edit Text
     * Also, sets the text views to the discounted and final amounts
     */
    private fun calculateDiscount(){

        // Capture data from Edit Text
        val originalAmountInEditText = binding.originalPriceEditText.text.toString()
        val originalAmount = originalAmountInEditText.toDoubleOrNull()
        // Throw an error if the error value was entered
        if (originalAmount == null){
            binding.originalPriceEditText.error = getString(R.string.enter_valid_value)
            binding.finalAmount.text = ""
            return
        }

        // Capture data from Edit Text
        val discountInEditText = binding.discountPercentageEditText.text.toString()
        val discountPercentage = discountInEditText.toDoubleOrNull()
        // Throw an error if the error value was entered
        if (discountPercentage == null){
            binding.discountPercentageEditText.error = getString(R.string.enter_valid_value)
            binding.discountedAmount.text = ""
            return
        }

        val discount = discountPercentage/100

        // Calculate the Discount and Original Amount
        val discountAmount = discount*originalAmount
        val finalAmount = originalAmount - discountAmount

        // Format the amount to the current currency
        val formattedDiscountAmount = NumberFormat.getCurrencyInstance().format(discountAmount)
        val formattedFinalAmount = NumberFormat.getCurrencyInstance().format(finalAmount)

        // Set the text views to the formatted values
        binding.discountedAmount.text = formattedDiscountAmount
        binding.finalAmount.text = formattedFinalAmount

    }
}