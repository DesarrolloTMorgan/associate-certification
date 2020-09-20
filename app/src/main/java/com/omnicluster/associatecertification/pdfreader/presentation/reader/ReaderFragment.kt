package com.omnicluster.associatecertification.pdfreader.presentation.reader

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.omnicluster.associatecertification.R
import com.omnicluster.associatecertification.databinding.FragmentReaderBinding
import com.omnicluster.associatecertification.pdfreader.framework.PDFViewModelFactory
import com.omnicluster.associatecertification.util.BaseFragment
import com.omnicluster.associatecertification.util.IntentUtil.READ_REQUEST_CODE
import com.omnicluster.associatecertification.util.IntentUtil.createOpenPDFIntent
import com.omnicluster.associatecertification.util.extensions.viewLifecycle
import com.omnicluster.certification.core.pdfreader.domain.Document

class ReaderFragment : BaseFragment(R.layout.fragment_reader) {

    private val binding by viewLifecycle(FragmentReaderBinding::bind)

    companion object {

        fun newInstance(document: Document) = ReaderFragment().apply {
            arguments = ReaderViewModel.createArguments(document)
        }
    }

    private val viewModel: ReaderViewModel by viewModels {
        PDFViewModelFactory
    }

    override fun setupView() {
        binding.tabBookmark.setOnClickListener { viewModel.toggleBookmark() }
        binding.tabLibrary.setOnClickListener { viewModel.toggleInLibrary() }
        binding.tabNextPage.setOnClickListener { viewModel.nextPage() }
        binding.tabPreviousPage.setOnClickListener { viewModel.previousPage() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = BookmarksAdapter {
            viewModel.openBookmark(it)
        }
        binding.rvBookmarks.adapter = adapter
        viewModel.document.observe(viewLifecycleOwner, Observer {
            if (it == Document.EMPTY) {
                // Show file picker action.
                startActivityForResult(createOpenPDFIntent(), READ_REQUEST_CODE)
            }
        })

        viewModel.bookmarks.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })

        viewModel.isBookmarked.observe(viewLifecycleOwner, Observer {
            val bookmarkDrawable = if (it) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border
            binding.tabBookmark.setCompoundDrawablesWithIntrinsicBounds(0, bookmarkDrawable, 0, 0)
        })

        viewModel.isInLibrary.observe(viewLifecycleOwner, Observer {
            val libraryDrawable = if(it) R.drawable.ic_library else R.drawable.ic_library_border
            binding.tabLibrary.setCompoundDrawablesRelativeWithIntrinsicBounds(0, libraryDrawable, 0, 0)
        })

        viewModel.currentPage.observe(viewLifecycleOwner, Observer { showPage(it) })
        viewModel.hasNextPage.observe(viewLifecycleOwner, Observer { binding.tabNextPage.isEnabled = it })
        viewModel.hasPreviousPage.observe(viewLifecycleOwner, Observer { binding.tabPreviousPage.isEnabled = it })

        if(arguments != null) {
            viewModel.loadArguments(arguments)
        } else {
            // Recreating fragment after configuration change, reopen current page so it can be rendered again.
            viewModel.reopenPage()
        }
    }

    private fun showPage(page: PdfRenderer.Page) {
        binding.ivPage.visibility = View.VISIBLE
        binding.tvPages.visibility = View.VISIBLE
        binding.tabPreviousPage.visibility = View.VISIBLE
        binding.tabNextPage.visibility = View.VISIBLE

        if (binding.ivPage.drawable != null) {
            (binding.ivPage.drawable as BitmapDrawable).bitmap.recycle()
        }

        val size = Point()
        activity?.windowManager?.defaultDisplay?.getSize(size)

        val pageWidth = size.x
        val pageHeight = page.height * pageWidth / page.width

        val bitmap = Bitmap.createBitmap(
            pageWidth,
            pageHeight,
            Bitmap.Config.ARGB_8888)

        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        binding.ivPage.setImageBitmap(bitmap)

        binding.tvPages.text = getString(
            R.string.page_navigation_format,
            page.index + 1,
            viewModel.renderer.value?.pageCount
        )

        page.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Process open file intent.
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri -> viewModel.openDocument(uri) }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}