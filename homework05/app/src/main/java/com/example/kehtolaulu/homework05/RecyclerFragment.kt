package com.example.kehtolaulu.homework05

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast

class RecyclerFragment : Fragment() {
    companion object {
        fun newInstance(): RecyclerFragment {
            return RecyclerFragment()
        }
    }

    lateinit var studentAdapter: StudentAdapter
    lateinit var diff: StudentListDiffCallback
    lateinit var toolbar: Toolbar
    lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_recycler, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        progressBar = view.findViewById(R.id.progress_bar)
        progressBar.visibility = ProgressBar.INVISIBLE
        (activity as MainActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        val studentsRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        studentsRecyclerView.layoutManager = LinearLayoutManager(context)
        diff = StudentListDiffCallback()
        studentAdapter = StudentAdapter(diff)
        studentAdapter.submitList(StudentsHelper.list.toList().blockingGet())
        studentsRecyclerView.adapter = studentAdapter
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_sort_by_age -> {
                studentAdapter.submitList(studentAdapter.sortByAge(progressBar))
                Toast.makeText(context, "Age sorting", Toast.LENGTH_SHORT)
                        .show()
            }
            R.id.item_sort_in_alph -> {
                studentAdapter.submitList(studentAdapter.sortByAlpha(progressBar))
                Toast.makeText(context, "Alpha sorting", Toast.LENGTH_SHORT)
                        .show()
            }
        }
        return true
    }
}
