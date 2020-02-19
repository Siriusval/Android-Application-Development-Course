package fi.jamk.roomwordsample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fi.jamk.roomwordsample.room.components.Word

/**
 * Adapter, populate the recyclerview
 */
class WordListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Word>() // Cached copy of words

    /**
     * Holder for each data item
     * Description of the view
     */
    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    /**
     * For each item, inflate with layout and return view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    /**
     * bind data to holder
     */
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.wordItemView.text = current.word
    }

    /**
     *
     */
    internal fun setWords(words: List<Word>) {//internal : visible on whole module (intellij or gradle)
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size
}