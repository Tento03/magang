package com.example.suitmedia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.suitmedia.databinding.ActivitySecondBinding
import dagger.hilt.android.AndroidEntryPoint

class SecondActivity : AppCompatActivity() {
    lateinit var binding:ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var nameFromFirstScreen = intent.getStringExtra(MainActivity.NAMA).toString()
        var nameFromThirdScreen=intent.getStringExtra(ThirdActivity.NAMETAG).toString()
        binding.tvFirstName.text= "$nameFromFirstScreen"
        binding.tvSelectedUser.text="Selected User Name:$nameFromThirdScreen"

        binding.btnChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}