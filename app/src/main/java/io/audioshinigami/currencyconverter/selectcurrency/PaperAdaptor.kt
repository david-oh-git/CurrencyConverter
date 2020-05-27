package io.audioshinigami.currencyconverter.selectcurrency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.databinding.ListItemPaperBinding

class PaperAdaptor: ListAdapter<Paper, PaperAdaptor.ViewHolder>(
    PaperDiffCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind( getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemPaperBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->

                binding.paper?.let {
                    navigateBack(it.code, view)
                }
            }
        }

        private fun navigateBack(code: String,view: View){
            view.findNavController().popBackStack()
        }

        fun bind(_paper: Paper){
            with(binding){
                paper = _paper
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
            return oldItem.code == newItem.code
        }
    }
}