package com.tonynowater.mypokedex.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.tonynowater.core.executeAfter
import com.tonynowater.core.utils.LinearSpaceItemDecorator
import com.tonynowater.mypokedex.R
import com.tonynowater.mypokedex.databinding.ItemTypeBinding

class TypeAdapter : ListAdapter<String?, TypeHolder>(TypeDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeHolder {
        return TypeHolder(
            ItemTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TypeHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object TypeDiff : DiffUtil.ItemCallback<String?>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class TypeHolder(private val bindings: ItemTypeBinding) : RecyclerView.ViewHolder(bindings.root) {

    fun bind(item: String?) {
        bindings.executeAfter {
            typeName = item
        }
    }
}

@BindingAdapter("types")
fun bindTypes(view: RecyclerView, types: List<String>?) {

    view.adapter = TypeAdapter()
    view.layoutManager = FlexboxLayoutManager(view.context).apply {
        flexDirection = FlexDirection.ROW
        flexWrap = FlexWrap.WRAP
        justifyContent = JustifyContent.CENTER
    }
    view.addItemDecoration(
        LinearSpaceItemDecorator(
            LinearSpaceItemDecorator.HORIZONTAL, view.context.resources.getDimensionPixelSize(
                R.dimen.margin_padding_size_small
            )
        )
    )

    (view.adapter as? TypeAdapter)?.apply {
        if (types == null) {
            submitList(listOf<String?>(null))
        } else {
            submitList(types)
        }
    }
}
