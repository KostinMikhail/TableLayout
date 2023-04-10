package com.kostlin.inspiration

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private var rows: Int = 7
    private var cols: Int = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tableLayout = findViewById(R.id.tableLayout)
        createTable()
    }

    @SuppressLint("SetTextI18n")
    private fun createTable() {

        for (r in 0..rows) {
            val tableRow = TableRow(this)

            for (c in 0 until cols) {
                val textView = TextView(this)
                if (r == 0) {
                    textView.text = if (c == 0) " " else "$c"
                    textView.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                } else {
                    if (c == 0) {
                        textView.text = "Участник " + r.toString()
                        textView.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                    } else {
                        val editText = EditText(this)
                        editText.hint = "0-5"
                        editText.inputType = android.text.InputType.TYPE_CLASS_NUMBER
                        editText.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                        editText.setBackgroundResource(R.drawable.bg)
                        tableRow.addView(editText)
                    }
                }
                tableRow.addView(textView)
            }
            tableLayout.addView(tableRow)
        }

        for (tableRow in tableLayout.children) {
            if (tableRow is TableRow) {
                for (view in tableRow.children) {
                    if (view is EditText) {
                        view.setOnClickListener {
                            valueCheckup(view)
                        }
                    }
                }
            }
        }
    }

    private fun valueCheckup(editText: EditText) {
        val value = editText.text.toString()
        if (value.isNotEmpty()) {
            val intValue = value.toInt()
            if (intValue < 0 || intValue > 5) {
                editText.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
                Toast.makeText(this, "Допустимые значения от 0 до 5", Toast.LENGTH_SHORT).show()
            } else {
                editText.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                calculate()
            }
        }
    }

    private fun calculate() {
        for (tableRow in tableLayout.children) {
            if (tableRow is TableRow && tableRow.childCount > 1) {
                var sum = 0
                for (view in tableRow.children) {
                    if (view is EditText) {
                        if (view.text.toString().isNotEmpty()) {
                            sum += view.text.toString().toInt()
                        }
                    }
                }

                val textView = TextView(this)
                textView.text = sum.toString()
                textView.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                tableRow.removeView(textView)
                tableRow.addView(textView)
            }
        }
    }
}