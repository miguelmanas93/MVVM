package com.miguel.ags.mvvmtodos.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import com.miguel.ags.mvvmtodos.R
import com.miguel.ags.mvvmtodos.ui.todos.TodosListActivity

class HomeFragment : Fragment() , View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val btn : Button = root.findViewById(R.id.button)

        btn.setOnClickListener(this)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button -> {
                val test = Intent(context, TodosListActivity::class.java)
                startActivityForResult(test , 0)            }
        }
    }
}
