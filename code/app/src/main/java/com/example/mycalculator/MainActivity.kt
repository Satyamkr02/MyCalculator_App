package com.example.mycalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var isOriginalColor = true // Variable to track the current color state

    private var tvInput: TextView? = null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvResult)


        val btnBackspace: Button = findViewById(R.id.btnBackspace)
        btnBackspace.setOnClickListener {
            onBackspace()
        }

        val squareButton: Button = findViewById(R.id.btnSquareOfx)
        squareButton.setOnClickListener {
            onSquareButtonClick(it)
        }

        val btnColorchange: Button = findViewById(R.id.btn_colorChange)
        btnColorchange.setOnClickListener {
            onColorChange()
        }

    }

    fun onColorChange() {
        // Get reference to the color you want to change
        val newColor = if (isOriginalColor) {
            // Change to the new color
            ContextCompat.getColor(this, R.color.accent_color2)
        } else {
            // Change back to the original color
            ContextCompat.getColor(this, R.color.accent_color)
        }

        // Get reference to the TextView you want to change color
        val tvInput: TextView = findViewById(R.id.tvResult)

        // Change the color of the text
//        tvInput.setTextColor(newColor)


        // Get reference to the Button you want to change color
        val btnColorChange: Button = findViewById(R.id.btn_colorChange)
        val btnCLR: Button = findViewById(R.id.btnCLR)
        val btnBackspace: Button = findViewById(R.id.btnBackspace)
        val btnPercentage: Button = findViewById(R.id.btnPercentage)
        val btnDivide: Button = findViewById(R.id.btnDivide)
        val btnMultiply: Button = findViewById(R.id.btnMultiply)
        val btnSubstract: Button = findViewById(R.id.btnSubstract)
        val btnAdd: Button = findViewById(R.id.btnAdd)
        val btn_Is_Equal_To: Button = findViewById(R.id.btn_Is_Equal_To)
        val btnSquareOfx: Button = findViewById(R.id.btnSquareOfx)

        btnCLR.setTextColor(newColor)
        btnBackspace.setTextColor(newColor)
        btnPercentage.setTextColor(newColor)
        btnDivide.setTextColor(newColor)
        btnMultiply.setTextColor(newColor)
        btnSubstract.setTextColor(newColor)
        btnAdd.setTextColor(newColor)
        btnSquareOfx.setTextColor(newColor)

        // Change the background color of the button
//        btn_colorChange.setBackgroundColor(newColor)
//        btn_Is_Equal_To.setBackgroundColor(newColor)

        // Toggle the color state
        isOriginalColor = !isOriginalColor

        // Change the shape of the button "btnColorChange"
        if (isOriginalColor) {
            btnColorChange.setBackgroundResource(R.drawable.btn_shape)
        } else {
            btnColorChange.setBackgroundResource(R.drawable.btn_shape2)
        }

        // Change the shape of the button "btnIsEqualsTo"
        btn_Is_Equal_To.setBackgroundResource(if (isOriginalColor) R.drawable.btn_shape else R.drawable.btn_shape2)

    }

    fun onDigit( view : View )
    {
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false

    }

    fun onClear(view : View)
    {
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View)
    {
        if( lastNumeric && !lastDot )
        {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true

        }
    }

    fun onOperator( view: View )
    {
        tvInput?.text?.let {

            if (lastNumeric && !isOperatorAdded(it.toString()))
            {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    private fun isOperatorAdded( value : String ) : Boolean
    {
        return if( value.startsWith("-"))
        {
            false
        }
        else
        {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onEqual(view : View)
    {
         if(lastNumeric)
         {
             var tvValue = tvInput?.text.toString()
             var prefix = ""
             try{
                 if(tvValue.startsWith("-")){
                     prefix = "-"
                     tvValue = tvValue.substring(1)           // to get rid of minus of "-99"  e.g -99  -- > 99
                 }
                 if(tvValue.contains("-"))                                   // MINUS
                 {
                     val splitValue = tvValue.split("-")
                     var one = splitValue[0]   // 99
                     var two = splitValue[1]   // 1

                     if(prefix.isNotEmpty())
                     {
                         one = prefix + one
                     }

                     var result = one.toDouble() - two.toDouble()
                     tvInput?.text = removeZeroAfterDot(result.toString())
                 }
                 else if(tvValue.contains("+"))                              // ADDITION
                 {
                     val splitValue = tvValue.split("+")
                     var one = splitValue[0]   // 99
                     var two = splitValue[1]   // 1

                     if(prefix.isNotEmpty())
                     {
                         one = prefix + one
                     }

                     var result = one.toDouble() + two.toDouble()
                     tvInput?.text = removeZeroAfterDot(result.toString())
                 }
                 else if(tvValue.contains("*"))                                // MULTIPLICATION
                 {
                     val splitValue = tvValue.split("*")
                     var one = splitValue[0]   // 99
                     var two = splitValue[1]   // 1

                     if(prefix.isNotEmpty())
                     {
                         one = prefix + one
                     }

                     var result = one.toDouble() * two.toDouble()
                     tvInput?.text = removeZeroAfterDot(result.toString())
                 }
                 else if(tvValue.contains("/"))                                     // DIVISION
                 {
                     val splitValue = tvValue.split("/")
                     var one = splitValue[0]   // 99
                     var two = splitValue[1]   // 1

                     if(prefix.isNotEmpty())
                     {
                         one = prefix + one
                     }

                     var result = one.toDouble() / two.toDouble()
                     tvInput?.text = removeZeroAfterDot(result.toString())
                 }



             }catch (e : ArithmeticException)
             {
                 e.printStackTrace()
             }


         }
    }

    private fun removeZeroAfterDot(result: String) : String
    {
        var value = result
        if(result.contains(".0"))
            value = result.substring(0 , result.length - 2 )

        return value
    }


    fun onBackspace() {
        val currentInput = tvInput?.text.toString()
        if (currentInput.isNotEmpty()) {
            val updatedInput = currentInput.substring(0, currentInput.length - 1)
            tvInput?.text = updatedInput
        }
    }

    fun onSquareButtonClick(view: View) {
        val inputTextView: TextView = findViewById(R.id.tvResult)
        val inputValue = inputTextView.text.toString().toDoubleOrNull()
        if (inputValue != null) {
            val result = inputValue * inputValue
            inputTextView.text = result.toString()
        }
    }

    fun onPercentageButtonClick(view: View) {
        val inputTextView: TextView = findViewById(R.id.tvResult)
        val inputValue = inputTextView.text.toString().toDoubleOrNull()
        if (inputValue != null) {
            val result = inputValue / 100
            inputTextView.text = result.toString()
        }
    }

}