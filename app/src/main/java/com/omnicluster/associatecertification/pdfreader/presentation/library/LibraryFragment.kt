package com.omnicluster.associatecertification.pdfreader.presentation.library

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.omnicluster.associatecertification.R
import com.omnicluster.associatecertification.databinding.FragmentLibraryBinding
import com.omnicluster.associatecertification.pdfreader.framework.PDFViewModelFactory
import com.omnicluster.associatecertification.pdfreader.presentation.OpenDocumentInterface
import com.omnicluster.associatecertification.pdfreader.presentation.reader.ReaderViewModel
import com.omnicluster.associatecertification.util.BaseFragment
import com.omnicluster.associatecertification.util.IntentUtil.READ_REQUEST_CODE
import com.omnicluster.associatecertification.util.IntentUtil.createOpenPDFIntent
import com.omnicluster.associatecertification.util.extensions.viewLifecycle
import com.omnicluster.certification.core.pdfreader.domain.Document

class LibraryFragment : BaseFragment(R.layout.fragment_library) {

    private val viewModel: LibraryViewModel by viewModels {
        PDFViewModelFactory
    }
    private val binding by viewLifecycle(FragmentLibraryBinding::bind)

    override fun setupView() {
        val adapter = DocumentsAdapter(glide = Glide.with(this)) {
            goToOpenDocument(it)
        }
        binding.rvDocuments.adapter = adapter
        viewModel.observableDocuments.observe(viewLifecycleOwner, Observer { adapter.update(it) })
        viewModel.loadDocuments()

        binding.fabAddDocument.setOnClickListener {
            startActivityForResult(createOpenPDFIntent(), READ_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri -> viewModel.addDocument(uri) }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun goToOpenDocument(document: Document) {
        findNavController().navigate(
            R.id.action_libraryFragment_to_readerFragment,
            ReaderViewModel.createArguments(document)
        )
    }
}