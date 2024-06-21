package com.arkul.mychat.ui.adapters.recyclerViews

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerView<T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {

    abstract val asyncListDiffer: AsyncListDiffer<T>



}