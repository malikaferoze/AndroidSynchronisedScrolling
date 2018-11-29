package com.example.malik.synchronisedscrolling

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_block.view.*
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class Adapter(private val parentRV: RecyclerView): RecyclerView.Adapter<CustomViewHolder>() {

    var horizontalRecyclerViews = mutableListOf<RecyclerView>()
    var absoluteOffset: Int? = null
    //numberOfItems
    override fun getItemCount(): Int {
        return 25
    }

    fun matchOffset(offset: Int? = absoluteOffset) {
        offset?.let { offsetValue ->
            horizontalRecyclerViews.forEach { recyclerView ->
                val currentOffset = recyclerView.computeHorizontalScrollOffset()
                if (offsetValue != currentOffset) {
                    recyclerView.scrollBy(offsetValue-currentOffset, 0)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflator = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflator.inflate(R.layout.item_recycler_view, parent, false)
        return  CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.horizontalRecyclerView.layoutManager = LinearLayoutManager(holder.view.context, LinearLayoutManager.HORIZONTAL, false)

        val onTouchListener = object: RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(p0: RecyclerView, p1: MotionEvent) {
            }
            override fun onInterceptTouchEvent(p0: RecyclerView, p1: MotionEvent): Boolean {
                if (p1.action == MotionEvent.ACTION_UP) {
                    absoluteOffset = p0.computeHorizontalScrollOffset()
                    return true
                }
                return false
            }
            override fun onRequestDisallowInterceptTouchEvent(p0: Boolean) {
            }
        }

        val onScrollListener = object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val value = recyclerView.computeHorizontalScrollOffset()
                matchOffset(value)
            }
        }


        val child = BlockAdapter()
        holder.view.horizontalRecyclerView.tag = position
        holder.view.horizontalRecyclerView.clearOnScrollListeners()
        holder.view.horizontalRecyclerView.addOnItemTouchListener(onTouchListener)
        holder.view.horizontalRecyclerView.addOnScrollListener(onScrollListener)
        holder.view.horizontalRecyclerView.adapter = child
        horizontalRecyclerViews.add(holder.view.horizontalRecyclerView)
    }
}

class BlockAdapter(): RecyclerView.Adapter<CustomViewHolder>() {

    //numberOfItems
    override fun getItemCount(): Int {
        return 30
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflator = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflator.inflate(R.layout.item_block, parent, false)
        return  CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.block.setBackgroundColor(
                if (position % 2 == 0) {
                    Color.BLUE
                } else {
                    Color.RED
                }
        )
        holder.view.block.setTextColor(Color.WHITE)
        holder.view.block.text = position.toString()
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    init {
    }

}