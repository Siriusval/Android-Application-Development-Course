package fi.jamk.roomwordsample.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import fi.jamk.roomwordsample.R

/**
 * Activity to add new word
 */
class NewWordActivity : AppCompatActivity() {

    /**
     * Text field
     */
    private lateinit var editWordView: EditText

    /**
     * On Create
     */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word) //inflate

        //get widgets
        editWordView = findViewById(R.id.edit_word)
        val button = findViewById<Button>(R.id.button_save)

        //add listener to button
        button.setOnClickListener {

            //send back intent with result
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWordView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish() //close activity
        }
    }

    /**
     * Static
     */
    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }


}