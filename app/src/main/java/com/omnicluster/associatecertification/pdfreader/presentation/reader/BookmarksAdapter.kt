package com.omnicluster.associatecertification.pdfreader.presentation.reader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omnicluster.associatecertification.R
import com.omnicluster.associatecertification.databinding.ItemBookmarkBinding
import com.omnicluster.certification.core.pdfreader.domain.Bookmark

class BookmarksAdapter(
    private val bookmarks: MutableList<Bookmark> = mutableListOf(),
    private val itemClickListener: (Bookmark) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookmarkBinding.inflate(inflater, parent, false)
        return BookmarkItemViewHolder(binding, itemClickListener)
    }

    override fun getItemCount() = bookmarks.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BookmarkItemViewHolder)?.bind(bookmarks[holder.adapterPosition])
    }

    class BookmarkItemViewHolder(
        private val binding: ItemBookmarkBinding,
        private val itemClickListener: (Bookmark) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bookmark: Bookmark) {
            binding.tvBookmarkName.text = itemView.resources.getString(
                R.string.page_bookmark_format,
                bookmark.page
            )
            itemView.setOnClickListener { itemClickListener.invoke(bookmark) }
        }
    }

    fun update(newBookmarks: List<Bookmark>) {
        bookmarks.clear()
        bookmarks.addAll(newBookmarks)
        notifyDataSetChanged()
    }
}