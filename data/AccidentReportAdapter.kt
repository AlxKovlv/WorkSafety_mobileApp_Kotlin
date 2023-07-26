package com.example.taladapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.taladapp.R
import com.example.taladapp.data.AccidentReport
import com.example.taladapp.data.AppDatabase
import com.example.taladapp.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccidentReportAdapter(
    context: Context,
    reports: List<AccidentReport>,
    private val loggedInUsername: String
) : ArrayAdapter<AccidentReport>(context, R.layout.multi_lines, reports) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val report = getItem(position)!!
        val viewHolder: ViewHolder
        val view: View

        if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.multi_lines, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.lineB.text = "Location: ${report.location}"
        viewHolder.lineC.text = "Details: ${report.accidentDetails}"
        viewHolder.lineD.text = "Action taken: ${report.actionTaken}"
        viewHolder.lineE.text = "${report.date} ${report.time}"

        // Fetch the User who submitted the report asynchronously and set the TextView text
        val userDao = AppDatabase.getInstance(context).userDao()
        val scope = CoroutineScope(Dispatchers.Default)

        scope.launch {
            val user = userDao.getUser(report.username)
            withContext(Dispatchers.Main) {
                viewHolder.lineA.text = "Report ID: ${report.id}, Filed by: ${user?.username ?: loggedInUsername}"
            }
        }

        return view
    }

    private class ViewHolder(view: View) {
        val lineA: TextView = view.findViewById(R.id.line_a)
        val lineB: TextView = view.findViewById(R.id.line_b)
        val lineC: TextView = view.findViewById(R.id.line_c)
        val lineD: TextView = view.findViewById(R.id.line_d)
        val lineE: TextView = view.findViewById(R.id.line_e)
    }
}


