/*
 * MIT License
 *
 * Copyright (c) 2020  David Osemwota
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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