package io.audioshinigami.currencyconverter.selectcurrency

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.audioshinigami.currencyconverter.data.Paper

/**
 * [BindingAdapter] for [PaperAdaptor] , sets the value for List<Paper>
 */


@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Paper>?){
    items?.let {
        (listView.adapter as PaperAdaptor).submitList(it)
    }
}