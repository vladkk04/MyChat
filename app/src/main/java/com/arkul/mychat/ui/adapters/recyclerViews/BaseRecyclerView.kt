package com.arkul.mychat.ui.adapters.recyclerViews

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerView<T : BaseModel, VB : ViewBinding> :
    RecyclerView.Adapter<BaseRecyclerView.BaseViewHolder<T, VB>>() {

    private var onItemClickListener: OnItemClickListener<T>? = null

    fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        this.onItemClickListener = listener
    }


    private val diffCallback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return this@BaseRecyclerView.areItemsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return this@BaseRecyclerView.areContentsTheSame(oldItem, newItem)
        }

        override fun getChangePayload(oldItem: T, newItem: T): Any? {
            return this@BaseRecyclerView.getChangePayload(oldItem, newItem)
        }
    }

    private val listOfItems: AsyncListDiffer<T> by lazy { AsyncListDiffer(this, diffCallback) }
    val getItems: List<T>
        get() = listOfItems.currentList

    fun addItem(item: T) {
        val newList = listOfItems.currentList.toMutableList()
        newList.add(item)
        listOfItems.submitList(newList)
    }

    fun addItems(items: List<T>) {
        val newList = listOfItems.currentList.toMutableList()
        newList.addAll(items)
        listOfItems.submitList(newList)
    }

    fun setList(list: List<T>) {
        listOfItems.submitList(list)
    }

    fun clearList() {
        listOfItems.submitList(emptyList())
    }

    class BaseViewHolder<T: BaseModel, VB : ViewBinding> internal constructor(
        private val binding: VB,
        private val onItemClickListener: OnItemClickListener<T>?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T, bindAction: (T, VB) -> Unit) {
            bindAction(item, binding)

            binding.root.setOnClickListener {
                onItemClickListener?.onClick(item, bindingAdapterPosition)
            }
        }

        fun bindPayload(
            item: T,
            payload: MutableList<Any>,
            bindPayloadAction: (T, VB, MutableList<Any>) -> Unit
        ) {
            bindPayloadAction(item, binding, payload)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, VB> =
        BaseViewHolder(
            createBinding(
                LayoutInflater.from(parent.context),
                parent
            ),
            onItemClickListener()
        )

    override fun onBindViewHolder(
        holder: BaseViewHolder<T, VB>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.bindPayload(
                listOfItems.currentList[position],
                payloads
            ) { item, binding, payload ->
                bindPayload(item, binding, payload)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) =
        holder.bind(listOfItems.currentList[position]) { item, binding ->
            bind(item, binding)
        }

    override fun getItemCount(): Int = listOfItems.currentList.size

    protected open fun onItemClickListener(): OnItemClickListener<T>? = onItemClickListener

    protected open fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem.id == newItem.id

    protected open fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem

    protected open fun getChangePayload(oldItem: T, newItem: T): Any? = null

    protected open fun bindPayload(item: T, binding: VB, payloads: MutableList<Any>) {}

    protected abstract fun bind(item: T, binding: VB)

    protected abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup): VB
}
