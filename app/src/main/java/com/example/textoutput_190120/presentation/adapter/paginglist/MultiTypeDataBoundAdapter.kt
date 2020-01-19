package com.example.textoutput_190120.presentation.adapter.paginglist

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil


abstract class MultiTypeDataBoundAdapter<T, V : ViewDataBinding> (diffUtil: DiffUtil.ItemCallback<T>) : BaseDataBoundPagingAdapter<T, V>(diffUtil) {

    fun isEmpty(): Boolean = itemCount == 0

    override fun bindItem(holder: DataBoundViewHolder<V>, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty() || payloads.contains(DiffCallbackBaseCardModel.CONTENT)) {
            holder.binding.setVariable(BR.data, getItem(position))
        }
    }

    override fun getItemCount() = currentList?.size ?: 0
}
