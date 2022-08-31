package com.manukanagala.everydaycalculations.fragments

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.manukanagala.everydaycalculations.R
import com.manukanagala.everydaycalculations.databinding.FragmentEmiBinding
import java.text.NumberFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [EmiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmiFragment : Fragment() {

    private lateinit var binding: FragmentEmiBinding

    companion object {
        fun newInstance(): EmiFragment {
            return EmiFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.setTheme(R.style.Theme_Emi)
        // Inflate the layout for this fragment
        binding = FragmentEmiBinding.inflate(layoutInflater)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Alert Dialog

        MaterialAlertDialogBuilder(
            requireContext(),
            R.style.material_dialog
        )
            .setTitle(resources.getString(R.string.alert_title))
            .setMessage(resources.getString(R.string.info_message))
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                // Respond to positive button press
            }
            .show()


        //Add Click listener for the button
        binding.calculate.setOnClickListener {
            it.hideKeyboard()
            calculateEmi()
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
            binding.interestAmount.text = ""
            binding.emiAmount.text = ""
            binding.finalAmount.text = ""
        }
        return false
    }


    override fun onResume() {
        super.onResume()
        binding.loanAmountEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        binding.interestPercentageEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        binding.loanTenureEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    /**
     * This method calculates the emi values by capturing the data from Edit Text
     * Also, sets the text views to the interest, monthly emi and final amounts
     */
    private fun calculateEmi() {

        // Capture data from Edit Text
        val loanAmountEditText = binding.loanAmountEditText.text.toString()
        val loanAmount = loanAmountEditText.toDoubleOrNull()
        // Throw an error if the error value was entered
        if (loanAmount == null) {
            binding.loanAmountEditText.error = getString(R.string.enter_valid_value)
            binding.interestAmount.text = ""
            binding.finalAmount.text = ""
            binding.emiAmount.text = ""
            return
        }

        // Capture data from Edit Text
        val interestPercentageEditText = binding.interestPercentageEditText.text.toString()
        val interestPercentage = interestPercentageEditText.toDoubleOrNull()
        // Throw an error if the error value was entered
        if (interestPercentage == null) {
            binding.interestPercentageEditText.error = getString(R.string.enter_valid_value)
            binding.finalAmount.text = ""
            binding.interestAmount.text = ""
            binding.emiAmount.text = ""
            return
        }

        // Capture data from Edit Text
        val loanTenureEditText = binding.loanTenureEditText.text.toString()
        val loanTenure = loanTenureEditText.toDoubleOrNull()
        // Throw an error if the error value was entered
        if (loanTenure == null) {
            binding.loanTenureEditText.error = getString(R.string.enter_valid_value)
            binding.finalAmount.text = ""
            binding.interestAmount.text = ""
            binding.emiAmount.text = ""
            return
        }


        //EMI Formula - E = p*r*((1+r)^n/((1+r)^n -1))
        val i = interestPercentage
        val r = interestPercentage / 12 / 100
        val p = loanAmount
        val n = loanTenure


        // Calculate the Discount and Original Amount
        val rate = Math.pow((1 + r).toDouble(), n.toDouble())
        val amount = p * r * (rate / (rate - 1))
        val interestAmount = p * (i / 100) * (n / 12)
        val emiAmount = amount
        val finalAmount = p + interestAmount

        // Format the amount to the current currency
        val locale = Locale.getDefault()
        val formattedInterestAmount =
            NumberFormat.getCurrencyInstance(locale).format(interestAmount)
        val formattedEmiAmount = NumberFormat.getCurrencyInstance(locale).format(emiAmount)
        val formattedFinalAmount = NumberFormat.getCurrencyInstance(locale).format(finalAmount)

        // Set the text views to the formatted values
        binding.interestAmount.text = formattedInterestAmount
        binding.emiAmount.text = formattedEmiAmount
        binding.finalAmount.text = formattedFinalAmount

    }

    // This method hides the keyboard
    fun View.hideKeyboard() {
        val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(windowToken, 0)

    }

}
