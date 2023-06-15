package com.dicoding.saveplate.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.saveplate.R
import com.dicoding.saveplate.data.Recycle
import com.dicoding.saveplate.ui.recycle.RecycleDetailActivity

class ListRecycleAdapter(private val listInsights: ArrayList<Recycle>) : RecyclerView.Adapter<ListRecycleAdapter.ListViewHolder>() {


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        val tvSource: TextView = itemView.findViewById(R.id.tv_item_source)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.activity_list_recycle_adapter, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title, source, photo) = listInsights[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvTitle.text = title
        holder.tvSource.text = source
        val holderContext = holder.itemView.context
        holder.itemView.setOnClickListener {
            val moveObject = Intent(holderContext, RecycleDetailActivity::class.java)
            moveObject.putExtra(RecycleDetailActivity.DETAIL_RECYCLE, listInsights[holder.adapterPosition])
            holderContext.startActivity(moveObject)
        }
    }

    override fun getItemCount(): Int = listInsights.size
}