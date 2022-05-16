package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    private lateinit var result: TextView
//    private lateinit var newNumber : TextView
//    private val displayOperator by lazy(LazyThreadSafetyMode.NONE){findViewById<TextView>(R.id.operation)}

    private var operand1 :Double? = null
    private var pendingOperation = "="
    private var IS_NEGATIVE = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val button0 :Button = findViewById(R.id.button0)
//        val button1 :Button = findViewById(R.id.button1)
//        val button2 :Button = findViewById(R.id.button2)
//        val button3 :Button = findViewById(R.id.button3)
//        val button4 :Button = findViewById(R.id.button4)
//        val button5 :Button = findViewById(R.id.button5)
//        val button6 :Button = findViewById(R.id.button6)
//        val button7 :Button = findViewById(R.id.button7)
//        val button8 :Button = findViewById(R.id.button8)
//        val button9 :Button = findViewById(R.id.button9)
//        val buttonDot :Button = findViewById(R.id.buttonDot)
//
//        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
//        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
//        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
//        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
//        val buttonPlus = findViewById<Button>(R.id.buttonPlus)

        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }


        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        button0.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val opsListener = View.OnClickListener{v ->
            val op = (v as Button).text.toString()
            try{
                val value = newNumber.text.toString().toDouble()
                performOperation(op, value)
            }catch(e:NumberFormatException){
                newNumber.setText("")
            }
            pendingOperation = op
            operation.text = pendingOperation
        }

        negButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                if(!IS_NEGATIVE) {
                    newNumber.setText("-" + newNumber.text)
                    IS_NEGATIVE = true
                }
            }
        })


        buttonDivide.setOnClickListener(opsListener)
        buttonMultiply.setOnClickListener(opsListener)
        buttonMinus.setOnClickListener(opsListener)
        buttonPlus.setOnClickListener(opsListener)
        buttonEquals.setOnClickListener(opsListener)
    }

    private fun performOperation(operation: String, value :Double){
        if(operand1 == null || operand1!!.isNaN()){
            operand1 = value
        }else{

            if(pendingOperation == "="){
                pendingOperation = operation
            }

            when(pendingOperation){
                "=" ->   operand1 = value
                "/" ->  operand1 = if(value == 0.0){
                    Double.NaN
                }else{
                    operand1!! / value
                }
                "*" ->   operand1 = operand1!! * value
                "-" ->   operand1 = operand1!! - value
                "+" ->   operand1 = operand1!! + value
            }
        }

        result.setText(operand1.toString())
        newNumber.setText("")
        IS_NEGATIVE = false
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Hello","Hello from restart")
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        result.setText(savedInstanceState.getDouble("operand").toString())
        operand1 = savedInstanceState.getDouble("operand")
        pendingOperation = savedInstanceState.getString("pendingOperation").toString()
        operation.text = pendingOperation
        Log.d("pending Value",pendingOperation)
    }

    override fun onPause() {
        super.onPause()
        Log.d("Paused","Here is Paused!")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Stopped","Here is Stopped!")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(operand1 != null){
            outState.putDouble("operand", operand1!!)
        }
        outState.putString("pendingOperation", pendingOperation)
    }

}