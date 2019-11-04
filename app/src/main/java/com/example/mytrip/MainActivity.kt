package com.example.mytrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.bt_calcular) {
            handleCalculate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_calcular.setOnClickListener(this)
    }

    private fun handleCalculate() {
        if (isValid()) {
            try {
                //calculate * valores / autonomia
                val distance = edt_distance.text.toString().toFloat()
                val price = edt_price.text.toString().toFloat()
                val autonomia = edt_autominia.text.toString().toFloat()
                val result = ((distance * price) / autonomia)
                tv_result.setText("Total R$: ${result}")

            } catch (nfe: NumberFormatException) {
                Toast.makeText(this, getString(R.string.valores_validos), Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, getString(R.string.valores_validos), Toast.LENGTH_LONG).show()
        }
    }

    private fun isValid(): Boolean {
        return edt_distance.text.toString() != ""
                && edt_price.text.toString() != ""
                && edt_autominia.text.toString() != ""
                && edt_autominia.text.toString() != "0"
    }
}
