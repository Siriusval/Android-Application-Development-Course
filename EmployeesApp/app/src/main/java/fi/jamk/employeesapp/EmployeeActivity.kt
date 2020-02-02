package fi.jamk.employeesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.employee_item.*
import org.json.JSONObject

class EmployeeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        // get data from intent
        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            val employeeString = bundle!!.getString("employee")
            val employee = JSONObject(employeeString)
            //Toast.makeText(this, "Name is $name", Toast.LENGTH_LONG).show()

            nameTextView.text = "${employee["last_name"]} ${employee["first_name"]}"
            titleTextView.text = "${employee["title"]}"
            emailTextView.text = "${employee["email"]}"
            phoneTextView.text = "${employee["phone"]}"
            departmentTextView.text = "${employee["department"]}"

            val uri = employee["image"].toString()
            Glide.with(portraitImageView.context).load(uri).into(portraitImageView)
        }
    }
}
