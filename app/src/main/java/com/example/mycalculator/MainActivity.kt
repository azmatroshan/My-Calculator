package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    private var lastDot = false
    private var lastDigit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun digitAppend(view: View) {
        lastDigit = true
        tvInput?.append((view as Button).text)
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        lastDigit = false
        lastDot = false
    }
    fun onEqual(view: View) {
        if(lastDigit){
            var firstNegative = false
            var value = tvInput?.text.toString()
            if(value.startsWith("-")){
                firstNegative = true
                value = value.substring(1)
            }
            try {
                if(value.contains("-")){
                    val parts = value.split("-")
                    val first = when(firstNegative){
                        true -> -(parts[0].toDouble())
                        else -> parts[0].toDouble()
                    }
                    val second = parts[1].toDouble()
                    tvInput?.text = (first-second).toString()
                }
                else if(value.contains("+")){
                    val parts = value.split("+")
                    val first = when(firstNegative){
                        true -> -(parts[0].toDouble())
                        else -> parts[0].toDouble()
                    }
                    val second = parts[1].toDouble()
                    (second + first).toString().also { tvInput?.text = it }
                }
                else if(value.contains("×")){
                    val parts = value.split("×")
                    val first = when(firstNegative){
                        true -> -(parts[0].toDouble())
                        else -> parts[0].toDouble()
                    }
                    val second = parts[1].toDouble()
                    (first*second).toString().also { tvInput?.text = it }
                }
                else if(value.contains("÷")){
                    val parts = value.split("÷")
                    val first = when(firstNegative){
                        true -> -(parts[0].toDouble())
                        else -> parts[0].toDouble()
                    }
                    val second = parts[1].toDouble()
                    (first/second).toString().also { tvInput?.text = it }
                }
                else if(value.contains("%")){
                    val parts = value.split("%")
                    val first = when(firstNegative){
                        true -> -(parts[0].toDouble())
                        else -> parts[0].toDouble()
                    }
                    val second = parts[1].toDouble()
                    ((first*second)/100).toString().also { tvInput?.text = it }
                }
            }
            catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
        tvInput?.text = removeDot(tvInput?.text.toString())
    }
    private fun removeDot(value: String) : String{
        var result = value
        if (value.contains(".0") && value.substring(value.length-2)==".0") {
            result = value.substring(0, value.length-2)
        }
        return result
    }
    fun onErase(view: View) {
        tvInput?.text  = tvInput?.text?.dropLast(1)
        val s = tvInput?.text.toString()
        if(s.isEmpty()){
            lastDigit = false
            lastDot = false
        }
        if(s.isNotEmpty()){
            if(!s.contains("."))
                lastDot = false
            for(i in 0..9){
                val r = i.toString()
                if(s.contains(r)){
                    lastDigit = true
                    break
                }
            }
        }
    }
    fun onOperator(view: View){
        tvInput?.text.let {
            if(lastDigit && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastDigit = false
                lastDot = false
            }
        }
    }
    fun onDecimal(view: View) {
        if(lastDigit && !lastDot){
            tvInput?.append(".")
            lastDigit = false
            lastDot = true
        }
        else{
            Toast.makeText(this,"Invalid Operation",Toast.LENGTH_SHORT).show()
        }
    }
    private fun isOperatorAdded(value : String): Boolean{
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("÷")
                    || value.contains("×")
                    || value.contains("+")
                    || value.contains("-")
                    || value.contains("%")
        }
    }
}
