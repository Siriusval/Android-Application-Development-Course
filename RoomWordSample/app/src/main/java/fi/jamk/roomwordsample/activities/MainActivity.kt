package fi.jamk.roomwordsample.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fi.jamk.roomwordsample.R
import fi.jamk.roomwordsample.WordListAdapter
import fi.jamk.roomwordsample.activities.NewWordActivity
import fi.jamk.roomwordsample.room.components.Word
import fi.jamk.roomwordsample.room.components.WordViewModel

/**
 * Main activity
 * Displays all words in DB
 *
 * .kt files creation order
 * Entity -> DAO -> Database, Repository -> ViewModel
 *
 * Use:
 * In ViewModel -> Repository(Database.getDao()), then repository.fnc()
 * Pository call DAO methods in background with coroutines
 *
 * LiveData is used in DAO, Repository & ViewModel
 */
class MainActivity : AppCompatActivity() {

    /**
     * View model to use for live update
     */
    private lateinit var wordViewModel: WordViewModel

    /**
     * Request code for startActivityForResult()
     * Identify the request to get intent back from second activity
     */
    private val newWordActivityRequestCode = 1

    /**
     * Function called when activity is started
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Recycler view, inflate with layout and link adapter
         */
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        /**
         * Link viewmodel with App, set observer to update on data change
         */
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })

        /**
         * Set up Floating Action Button to start new activity
         */
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, NewWordActivity::class.java)

            //Get intent back after second activity closes (Request code to indentify request)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    /**
     * Callback, get intent from second activity
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /**
         * If intent is the one we wait(request code) and is correct(Result_ok)
         * Create word and insert it into viewmodel
         */
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(0, it)
                wordViewModel.insert(word)
            }
        } else { //Else send toast on error
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
