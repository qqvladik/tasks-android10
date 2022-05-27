package by.mankevich.task03view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    private lateinit var buttonSubtask1: Button
    private lateinit var buttonSubtask2: Button
    private lateinit var spinnerCountry: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonSubtask1 = findViewById(R.id.button_subtask1)
        buttonSubtask2 = findViewById(R.id.button_subtask2)
        spinnerCountry = findViewById(R.id.spinner_country)

        buttonSubtask1.setOnClickListener{
            val intent1 = Intent(this, SubtaskActivity1::class.java)
            intent1.putExtra(EXTRA_SPINNER_ITEM, spinnerCountry.selectedItem.toString())
            startActivity(intent1)
        }

        buttonSubtask2.setOnClickListener{
            val intent2 = Intent(this, SubtaskActivity2::class.java)
            startActivity(intent2)
        }
    }

    companion object{
        const val EXTRA_SPINNER_ITEM = "by.mankevich.task03view.extra.spinner_item"
    }
}