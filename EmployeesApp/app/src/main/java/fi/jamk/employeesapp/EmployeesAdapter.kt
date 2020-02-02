package fi.jamk.employeesapp

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.employee_item.view.*
import org.json.JSONArray
import org.json.JSONObject

class EmployeesAdapter(private val employees: JSONArray) : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {

    /**
     * View Holder class to hold UI views
     * contains attributes for each individual views
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.nameTextView
        val titleTextView: TextView = view.titleTextView
        val emailTextView: TextView = view.emailTextView
        val phoneTextView: TextView = view.phoneTextView
        val departmentTextView: TextView = view.departmentTextView
        val portraitImageView : ImageView = view.portraitImageView

        // Add a item click listener
        init {
            itemView.setOnClickListener {
                // create an explicit intent
                val intent = Intent(view.context, EmployeeActivity::class.java)
                // add selected employee JSON as a string data
                intent.putExtra("employee",employees[adapterPosition].toString())
                // start a new activity
                view.context.startActivity(intent)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.employee_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return employees.length()
    }

    override fun onBindViewHolder(holder: EmployeesAdapter.ViewHolder, position: Int) {
        val employee : JSONObject = employees.getJSONObject(position)
        holder.nameTextView.text = "${employee["last_name"]} ${employee["first_name"]}"
        holder.titleTextView.text = "${employee["title"]}"
        holder.emailTextView.text = "${employee["email"]}"
        holder.phoneTextView.text = "${employee["phone"]}"
        holder.departmentTextView.text = "${employee["department"]}"

        val uri = employee["image"].toString()
        Glide.with(holder.portraitImageView.context).load(uri).into(holder.portraitImageView)


    }

}
