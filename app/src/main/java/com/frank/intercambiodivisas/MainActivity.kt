package com.frank.intercambiodivisas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.fredy.intercambiodivisas.R
import com.fredy.intercambiodivisas.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MonedaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val monedas = resources.getStringArray(R.array.monedas)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, monedas)
        binding.spinnerCurrency.setAdapter(adapter)

        viewModel = ViewModelProvider(this).get(MonedaViewModel::class.java)

        setupObservers()
        setupButtonClick()
    }

    private fun setupObservers() {
        viewModel.conversionResult.observe(this) { result ->
            binding.tvResult.text = result
        }
    }

    private fun setupButtonClick() {
        binding.btnConvert.setOnClickListener {
            val monto = binding.etAmount.text.toString().toDoubleOrNull()
            val moneda = binding.spinnerCurrency.text.toString()

            if (monto != null) {
                viewModel.convertir(monto, moneda)
            } else {
                binding.tvResult.text = "Ingresar número válido"
            }
        }
    }
}