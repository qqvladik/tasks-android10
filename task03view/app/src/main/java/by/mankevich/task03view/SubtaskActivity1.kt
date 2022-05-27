package by.mankevich.task03view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SubtaskActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var layoutId: Int = R.layout.activity_subtask1
        when(intent.getStringExtra(MainActivity.EXTRA_SPINNER_ITEM)){
            "Austria" -> layoutId=R.layout.activity_austria
            "Poland" -> layoutId=R.layout.activity_poland
            "Italy" -> layoutId=R.layout.activity_italy
            "Colombia" -> layoutId=R.layout.activity_columbia
            "Madagascar" -> layoutId=R.layout.activity_madagascar
            "Thailand" -> layoutId=R.layout.activity_thailand
            "Denmark" -> layoutId=R.layout.activity_denmark
            "Switzerland" -> layoutId=R.layout.activity_switzerland
        }
        setContentView(layoutId)
        Log.d("SubtaskActivity1", intent.getStringExtra(MainActivity.EXTRA_SPINNER_ITEM).toString())
    }
}