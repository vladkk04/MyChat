package com.arkul.mychat.ui.adapters.viewPager2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.R

class SelectAvatarAdapter(
    private val listOfImages: ArrayList<Int>
) : RecyclerView.Adapter<SelectAvatarAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_view_avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.avatar_item, parent, false)
        )
    }

    override fun getItemCount(): Int = listOfImages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(listOfImages[position])
    }
}