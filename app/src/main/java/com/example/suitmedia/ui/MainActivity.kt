package com.example.suitmedia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.suitmedia.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    companion object{
        const val NAMA="nama"
    }
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheck.setOnClickListener(){
            var nama=binding.etName.text.toString()
            var sentence=binding.etSentence.text.toString()
            var isPalindrome=isPalindrome(sentence)

            if (isPalindrome) {
                binding.tvResult.text = "isPalindrome"
                Toast.makeText(this@MainActivity,"isPalindrome",Toast.LENGTH_SHORT).show()
            } else {
                binding.tvResult.text = "not palindrome"
                Toast.makeText(this@MainActivity,"not palindrome",Toast.LENGTH_SHORT).show()
            }

            binding.btnNext.setOnClickListener(){
                Intent(this@MainActivity, SecondActivity::class.java).also {
                    it.putExtra(NAMA,nama)
                    startActivity(it)
                }
            }
        }

    }

    private fun isPalindrome(sentence: String):Boolean{
        val cleanString = sentence.replace("\\s".toRegex(), "").toLowerCase()
        val reversedString = cleanString.reversed()
        return cleanString == reversedString
    }
}