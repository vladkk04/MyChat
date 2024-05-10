package com.arkul.mychat.ui.adapters.viewPager2

import android.content.res.TypedArray
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.getIntegerOrThrow
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.R

class SelectAvatarAdapter(
    private val listOfImages: TypedArray
) : RecyclerView.Adapter<SelectAvatarAdapter.ViewHolder>() {
    override fun getItemCount(): Int = listOfImages.length()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_view_avatar)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.avatar_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(listOfImages.getResourceId(position, -1))
    }


}