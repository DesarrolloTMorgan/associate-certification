package com.omnicluster.associatecertification.pdfreader.presentation.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.omnicluster.associatecertification.R
import com.omnicluster.associatecertification.databinding.ItemDocumentBinding
import com.omnicluster.associatecertification.util.StringUtils
import com.omnicluster.certification.core.pdfreader.domain.Document

class DocumentsAdapter(
    private val documents: MutableList<Document> = mutableListOf(),
    private val glide: RequestManager,
    private val itemClickListener: (Document) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDocumentBinding.inflate(inflater, parent, false)
        return LibraryItemViewHolder(binding, glide, itemClickListener)
    }

    override fun getItemCount() = documents.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? LibraryItemViewHolder)?.bind(documents[holder.adapterPosition])
    }

    fun update(newDocuments: List<Document>) {
        documents.clear()
        documents.addAll(newDocuments)
        notifyDataSetChanged()
    }

    class LibraryItemViewHolder(
        private val binding: ItemDocumentBinding,
        private val glide: RequestManager,
        private val itemClickListener: (Document) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(document: Document) {
            glide.load(document.thumbnail)
                .error(glide.load(R.drawable.preview_missing))
                .into(binding.ivPdfPreview)
            binding.tvPdfTitle.text = document.name
            binding.tvPdfSize.text = StringUtils.readableFileSize(document.size)
            itemView.setOnClickListener {
                itemClickListener.invoke(document)
            }
        }
    }
}