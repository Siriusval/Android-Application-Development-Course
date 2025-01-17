package fi.jamk.roomshoppinglist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import fi.jamk.roomshoppinglist.room.components.ShoppingListItem
import kotlinx.android.synthetic.main.dialog_ask_new_shopping_list_item.view.*

class AskShoppingListItemDialogFragment : DialogFragment() {


    // --- LISTENER ---
    // Use this instance of the interface to deliver action events
    private lateinit var mListener: AddDialogListener

    /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.
    * Each method passes the DialogFragment in case the host needs to query it. */
    interface AddDialogListener {
        fun onDialogPositiveClick(item: ShoppingListItem)
    }

    // Override the Fragment.onAttach() method to instantiate the AddDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the AddDialogListener so we can send events to the host
            mListener = context as AddDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement AddDialogListener")
            )
        }
    }

    // --- DIALOG ---
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            // Custom layout for dialog
            val customView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_ask_new_shopping_list_item, null)

            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setView(customView)
            builder.setTitle(R.string.dialog_title)

                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_ok) { dialog, _ ->
                    // create a ShoppingList item
                    val name = customView.nameEditText.text.toString()
                    val count = customView.countEditText.text.toString().toInt()
                    val price = customView.priceEditText.text.toString().toDouble()
                    val item = ShoppingListItem(0, name, count, price)
                    mListener.onDialogPositiveClick(item)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
                    // User cancelled the dialog
                    dialog.dismiss()

                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")


    }
}