package com.example.malik.synchronisedscrolling

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this)

        val onScrollListener = object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                (recyclerView.adapter as? Adapter)?.matchOffset()
            }
        }

        verticalRecyclerView.addOnScrollListener(onScrollListener)
        verticalRecyclerView.adapter = Adapter(verticalRecyclerView)
    }
}
