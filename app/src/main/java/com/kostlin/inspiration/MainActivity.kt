package com.kostlin.inspiration

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.kostlin.inspiration.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rows: Int = 8
    private var cols: Int = 10

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        for (r in 1..rows) {
            val tblRow = TableRow(this)
            val tv = TextView(this)
            tv.setBackgroundResource(R.drawable.bg)

            binding.tblLayout?.addView(tblRow)
            if (r >= 2)
                tv.text = "Участник " + (r - 1)
                tblRow.addView(tv)

            for (c in 1..cols) {
                val tv1 = TextView(this)
                val et = EditText(this)
                et.setBackgroundResource(R.drawable.bg)
                tv1.setBackgroundResource(R.drawable.bg)


                if (c in 2 until rows && r == 1) {
                    tv1.text = ((c - 1).toString())
                    tblRow.addView(tv1)
                }
//                if (c == 1 && r >= 2) {
//                    tv1.text = ((r - 1).toString())
//                    tblRow.addView(tv1)
//                }
                if (c <= cols - 2 && r >= 2) {
                    tblRow.addView(et)
                    //тут мы делаем Эдит Вью
                }
                //tv1.tag = "$c+$r"

            }
        }
    }
}


