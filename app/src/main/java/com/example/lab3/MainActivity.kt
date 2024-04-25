package com.example.lab3

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable


class MainActivity : AppCompatActivity() {
    var resultTv: TextView? = null
    var solutionTv: TextView? = null
    var buttonDivide: AppCompatButton? = null
    var buttonMultiply: AppCompatButton? = null
    var buttonPlus: AppCompatButton? = null
    var buttonMinus: AppCompatButton? = null
    var buttonEquals: AppCompatButton? = null
    var button0: AppCompatButton? = null
    var button1: AppCompatButton? = null
    var button2: AppCompatButton? = null
    var button3: AppCompatButton? = null
    var button4: AppCompatButton? = null
    var button5: AppCompatButton? = null
    var button6: AppCompatButton? = null
    var button7: AppCompatButton? = null
    var button8: AppCompatButton? = null
    var button9: AppCompatButton? = null
    var buttonAC: AppCompatButton? = null
    var buttonDot: AppCompatButton? = null
    var buttonplusminus: AppCompatButton? = null
    private var expressionToCalculate: String = ""
    var IsOperatorLast: Boolean = false
    var equal_operator: String = ""
    var lastOperand: String = ""
    var lastOperator: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultTv = findViewById<TextView>(R.id.textview)
        solutionTv = findViewById<TextView>(R.id.solution)
        assignId(buttonDivide, R.id.button_division)
        assignId(buttonMultiply, R.id.button_multiply)
        assignId(buttonPlus, R.id.button_plus)
        assignId(buttonMinus, R.id.button_minus)
        assignId(buttonEquals, R.id.button_equals)
        assignId(button0, R.id.button_0)
        assignId(button1, R.id.button_1)
        assignId(button2, R.id.button_2)
        assignId(button3, R.id.button_3)
        assignId(button4, R.id.button_4)
        assignId(button5, R.id.button_5)
        assignId(button6, R.id.button_6)
        assignId(button7, R.id.button_7)
        assignId(button8, R.id.button_8)
        assignId(button9, R.id.button_9)
        assignId(buttonAC, R.id.button_ac)
        assignId(buttonDot, R.id.button_comma)
        assignId(buttonplusminus, R.id.button_plus_minus)

    }



    fun assignId(btn: AppCompatButton?, id: Int) {
        var btn = btn
        btn = findViewById(id)
        btn.setOnClickListener { onClick(btn) }
    }

    fun onClick(button: AppCompatButton?) {
        val buttonText = button!!.text.toString()
        if (buttonText == "AC") {
            expressionToCalculate = ""
            resultTv!!.text = "0"
            equal_operator = ""
            lastOperand = ""
            lastOperator = ""
        }
        else if (buttonText == "=") {
            if (equal_operator == "=") {
                expressionToCalculate += lastOperator + lastOperand
                Log.d("MyApp", "Button equal twice pressed. expressionToCalculate: $expressionToCalculate")
                val repeatedResult = getResult(expressionToCalculate)
                if (repeatedResult != "Err") {
                    val formatedResult = formatResult(repeatedResult.toDouble())
                    resultTv!!.text = formatedResult
                }
            } else {
                solutionTv!!.text = expressionToCalculate
                val finalResult = getResult(expressionToCalculate)
                if (finalResult != "Err" && finalResult != "Infinity" && finalResult != "-Infinity") {
                    val formatedResult = formatResult(finalResult.toDouble())
                    resultTv!!.text = formatedResult
                }else if (finalResult == "Infinity" || finalResult == "-Infinity") {
                    resultTv!!.text = "Ошибка"
                }
                expressionToCalculate = finalResult
            }
        }
        else if (buttonText == "+/-") {
            expressionToCalculate = "-$expressionToCalculate"
            resultTv!!.text = "-${resultTv!!.text}"

        }
        else {
            expressionToCalculate += buttonText
            solutionTv!!.text = expressionToCalculate
            if (hasOperators(buttonText)) {
                resultTv!!.text = resultTv!!.text
                IsOperatorLast = true
                lastOperator = buttonText

            }else {
                lastOperand = buttonText
                if (startsWithZero(resultTv!!.text)){
                    resultTv!!.text = buttonText
                }else if (IsOperatorLast) {
                    resultTv!!.text = buttonText
                    IsOperatorLast = false
                }
                else {
                    resultTv!!.text = "${resultTv!!.text}$buttonText"
                }

            }

        }
        equal_operator = buttonText
        Log.d("MyApp", "Button pressed. expressionToCalculate: $expressionToCalculate")
    }

    fun getResult(data: String?): String {
        return try {
            val context = Context.enter()
            context.optimizationLevel = -1
            val scriptable: Scriptable = context.initStandardObjects()
            var finalResult =
                context.evaluateString(scriptable, data, "Javascript", 1, null).toString()
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "")
            }
            finalResult
        } catch (e: Exception) {
            "Err"
        }
    }
    fun hasOperators(input: String): Boolean {
        val regex = Regex("[*/+-]")
        return regex.containsMatchIn(input)
    }

    fun startsWithZero(input: CharSequence): Boolean {
        return input.isNotEmpty() && input[0] == '0'
    }
    private fun formatResult(result: Double): String {
        return if (result % 1 == 0.0) {
            String.format("%.0f", result)
        } else {
            String.format("%.5f", result)
        }
    }
}







