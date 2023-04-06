package com.kostlin.inspiration

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private var rows: Int = 7 // количество участников
    private var cols: Int = 6 // количество столбцов (включая столбец с номерами)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tableLayout = findViewById(R.id.tableLayout)
        createTable()
    }

    @SuppressLint("SetTextI18n")
    private fun createTable() {
        // Создаем строки и столбцы таблицы
        for (r in 0..rows) {
            val tableRow = TableRow(this)

            for (c in 0 until cols) {
                val textView = TextView(this)

                if (r == 0) {
                    // Заполняем первую строку таблицы номерами
                    textView.text = if (c == 0) " " else "$c"
                    textView.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                } else {
                    if (c == 0) {
                        // Заполняем первый столбец таблицы номерами участников
                        textView.text = "Участник " + r.toString()
                        textView.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                    } else {
                        // Заполняем таблицу EditText для ввода очков
                        val editText = EditText(this)
                        editText.hint = "0-5"
                        editText.inputType = android.text.InputType.TYPE_CLASS_NUMBER
                        editText.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                        tableRow.addView(editText)
                    }
                }
                tableRow.addView(textView)
            }
            tableLayout.addView(tableRow)
        }

        // Добавляем слушатель на все EditText в таблице
        for (tableRow in tableLayout.children) {
            if (tableRow is TableRow) {
                for (view in tableRow.children) {
                    if (view is EditText) {
                        view.setOnFocusChangeListener { _, hasFocus ->
                            if (!hasFocus) {
                                checkEditTextValue(view)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkEditTextValue(editText: EditText) {
        // Проверяем значение в EditText, если значение вне диапазона 0-5, то подсвечиваем красным и выводим Toast
        val value = editText.text.toString()
        if (value.isNotEmpty()) {
            val intValue = value.toInt()
            if (intValue < 0 || intValue > 5) {
                editText.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
                Toast.makeText(this, "Допустимые значения от 0 до 5", Toast.LENGTH_SHORT).show()
            } else {
                editText.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                calculateRowScore()
            }
        }
    }

    private fun calculateRowScore() {
        // Считаем сумму очков в строке
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

                // Добавляем TextView для вывода суммы очков в строке
                val textView = TextView(this)
                textView.text = sum.toString()
                textView.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                tableRow.addView(textView)
            }
        }
    }
}