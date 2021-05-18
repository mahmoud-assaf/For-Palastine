package com.example.fasteriskfb

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.fasteriskfb.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var convertedText=""
    private val letterMap=mapOf(
        "ب" to "ٮ",
        "ت" to "ٮ",
        "ث" to "ٮ",
        "ج" to "ح",
        "خ" to "ح",
        "ذ" to "د",
        "ز" to "ر",
        "ش" to "س",
        "ض" to "ص",
        "ظ" to "ط",
        "غ" to "ع",
        "ف" to "ڡ",
        "ق" to "ٯ",
        "ن" to "ں",
        "ة" to "ه",
        "ي" to "ى"
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.setIcon(R.mipmap.ic_launcher);
        setTitle(R.string.app_name)

        binding.convertButton.setOnClickListener {
             convertedText=binding.originalPost.text.toString()
            hidekeyBoard()
            if (convertedText.isNullOrEmpty()){
                Toast.makeText(this,getString(R.string.no_text), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            for (originalChar in letterMap.keys){
                convertedText=convertedText.replace(originalChar,letterMap[originalChar].toString(),true)
            }
            binding.convertedPost.text=convertedText
            binding.constraintLayout.transitionToEnd()


        }

        binding.buttonShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, convertedText)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)


        }
        binding.buttonCopy.setOnClickListener {
            val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData
                    .newPlainText("Converted Text", convertedText)
            clipboard.setPrimaryClip(clipData)
            Toast.makeText(this,getString(R.string.copied), Toast.LENGTH_LONG).show()

        }




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.about ->{
                        showAbout()
            }
        }
        return  super.onOptionsItemSelected(item)
    }


    fun showAbout(){
        val builder = MaterialAlertDialogBuilder(this)
        // dialog title
        builder.setTitle("Palastine")
        // drawable for dialog title
        builder.setIcon(R.mipmap.ic_launcher)
        // dialog message
        builder.setMessage(getString(R.string.abouttext))

        // dialog background color
        builder.background = ColorDrawable(
                Color.parseColor("#FEFEFA")
        )
        // alert dialog positive button
        builder.setPositiveButton(getString(R.string.ok)){ dialog, which->
            // do something on positive button click
        }
        // set dialog non cancelable
        builder.setCancelable(true)
        // finally, create the alert dialog and show it
        val dialog = builder.create()
        dialog.show()
    }
    fun hidekeyBoard(){
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}