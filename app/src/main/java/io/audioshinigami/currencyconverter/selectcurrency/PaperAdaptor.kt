package io.audioshinigami.currencyconverter.selectcurrency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.databinding.ListItemPaperBinding

class PaperAdaptor(
    private val itemClickListener: (String) -> Unit
): ListAdapter<Paper, PaperAdaptor.ViewHolder>(
    PaperDiffCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind( getItem(position), itemClickListener)
    }

    class ViewHolder(
        private val binding: ListItemPaperBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(_paper: Paper, _clickListener: (String) -> Unit){
            with(binding){
                paper = _paper
                clickListener = _clickListener
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPaperBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    private class PaperDiffCallBack: DiffUtil.ItemCallback<Paper>(){

        override fun areItemsTheSame(oldItem: Paper, newItem: Paper): Boolean {
            return oldItem.paperId == newItem.paperId
        }

        override fun areContentsTheSame(oldItem: Paper, newItem: Paper): Boolean {
            return oldItem == newItem
        }
    }
}