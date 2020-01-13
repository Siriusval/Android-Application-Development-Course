package fi.jamk.sumcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /* --- VARIABLES --- */
    private var oldNumber: Int = 0 //Number at the LEFT of the operator
    private var currentNumber: Int = 0 //Number at the RIGHT of the operator

    private var operator = "" //Operation to execute, between +,-,* and /


    /**
     * Execute operation between both numbers and show result
     */
    private fun doMath(){
        //cant divide per 0
        if(currentNumber ==0 && operator=="/")
        {
            currentNumber = 0
        }
        else {
            val sum = when(operator) {
                "+" -> oldNumber + currentNumber
                "-" -> oldNumber - currentNumber
                "*" -> oldNumber * currentNumber
                "/" -> oldNumber / currentNumber
                else -> currentNumber
            }
            currentNumber = sum
        }
        operator = ""
        textView.text = currentNumber.toString()


    }

    /**
     * Add a number, modify current number and show
     */
    fun numberInput(view: View) {
        // view is a button (pressed one) get text and convert to Int
        val digit = (view as Button).text.toString().toInt()

        //if only zero in text view
        if(textView.text.toString() == "0"){
            currentNumber = digit
            textView.text = digit.toString()
        }
        else{
            currentNumber = currentNumber*10+digit

            // append a new string to textView
            textView.append(digit.toString())
        }
    }

    /**
     * Add operator if possible, or change it
     */
    fun operator(view:View) {
        val text = textView.text.toString()
        val newOperator = (view as Button).text.toString()

        //if textView is not null
        if(text.isNotBlank()){

            //if last symbol is an operator, replace it
            if( text.last().toString().contains(Regex("[+\\-*\\/]"))) {
                textView.text = text.substring(0,text.length-1)
            }
            else {
                //else if operation waiting
                if(operator.isNotBlank()) {
                    doMath()
                }
                //be ready to input new number
                oldNumber = currentNumber
                currentNumber = 0
            }

            //add operator string after number
            operator = newOperator
            textView.append(operator)
        }

    }

    /**
     * Calcul the sum if possible
     */
    fun equals(view: View){
        val text = textView.text.toString()

        if(text.isNotBlank()) {
            if (!text.last().toString().contains(Regex("[+\\-*\\/]"))) {
                doMath()
            }
        }
    }

    /**
     * Reset calculator
     */
    fun cancel(view: View){
        oldNumber = 0
        currentNumber = 0
        operator=""

        textView.text = "0"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.text = "0"
    }
}
