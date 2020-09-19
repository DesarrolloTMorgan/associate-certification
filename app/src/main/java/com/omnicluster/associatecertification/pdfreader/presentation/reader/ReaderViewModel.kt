package com.omnicluster.associatecertification.pdfreader.presentation.reader

import android.app.Application
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.omnicluster.associatecertification.pdfreader.framework.FileUtil.MODE_READ
import com.omnicluster.associatecertification.pdfreader.framework.Interactors
import com.omnicluster.associatecertification.pdfreader.framework.PDFViewModel
import com.omnicluster.certification.core.pdfreader.domain.Bookmark
import com.omnicluster.certification.core.pdfreader.domain.Document
import kotlinx.coroutines.launch
import java.io.IOException

class ReaderViewModel(application: Application, interactors: Interactors) :
    PDFViewModel(application, interactors) {

    companion object {
        private const val DOCUMENT_ARG = "document"

        fun createArguments(document: Document) = bundleOf(
            DOCUMENT_ARG to document
        )
    }

    val document = MutableLiveData<Document>()

    val bookmarks = MediatorLiveData<List<Bookmark>>().apply {
        addSource(document) { document ->
            viewModelScope.launch {
                postValue(interactors.getBookmarks(document))
            }
        }
    }

    val currentPage = MediatorLiveData<PdfRenderer.Page>()

    val hasPreviousPage: LiveData<Boolean> = Transformations.map(currentPage) {
        it.index > 0
    }

    val hasNextPage: LiveData<Boolean> = Transformations.map(currentPage) {
        renderer.value?.let { renderer -> it.index < renderer.pageCount - 1 }
    }

    val isBookmarked = MediatorLiveData<Boolean>().apply {
        addSource(document) { value = isCurrentPageBookmarked() }
        addSource(currentPage) { value = isCurrentPageBookmarked() }
        addSource(bookmarks) { value = isCurrentPageBookmarked() }
    }

    val isInLibrary: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(document) { document ->
            viewModelScope.launch {
                postValue(isInLibrary(document))
            }
        }
    }

    val renderer = MediatorLiveData<PdfRenderer>().apply {
        addSource(document) {
            try {
                val pdfRenderer =
                    getFileDescriptor(Uri.parse(it.url))?.let { it1 -> PdfRenderer(it1) }
                value = pdfRenderer
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getFileDescriptor(uri: Uri) =
        app.contentResolver.openFileDescriptor(uri, MODE_READ)

    private fun isCurrentPageBookmarked() =
        bookmarks.value?.any { it.page == currentPage.value?.index } == true

    private suspend fun isInLibrary(document: Document) =
        interactors.getDocuments().any { it.url == document.url }

    fun loadArguments(arguments: Bundle?) {
        if (arguments == null) {
            return
        }

        currentPage.apply {
            addSource(renderer) { renderer ->
                viewModelScope.launch {
                    val document = document.value

                    if (document != null) {
                        val bookmarks = interactors.getBookmarks(document).lastOrNull()?.page ?: 0
                        postValue(renderer.openPage(bookmarks))
                    }
                }
            }
        }

        val documentFromArguments = (arguments.get(DOCUMENT_ARG) as? Document) ?: Document.EMPTY

        val lastOpenDocument = interactors.getOpenDocument()

        document.value = when {
            documentFromArguments != Document.EMPTY -> documentFromArguments
            documentFromArguments == Document.EMPTY && lastOpenDocument != Document.EMPTY -> lastOpenDocument
            else -> Document.EMPTY
        }

        document.value?.let { interactors.setOpenDocument(it) }
    }

    fun openDocument(uri: Uri) {
        document.value = Document(uri.toString(), "", 0, "")
        document.value?.let { interactors.setOpenDocument(it) }
    }

    fun openBookmark(bookmark: Bookmark) {
        openPage(bookmark.page)
    }

    private fun openPage(page: Int) = renderer.value?.let {
        currentPage.value = it.openPage(page)
    }

    fun nextPage() = currentPage.value?.let { openPage(it.index.plus(1)) }

    fun previousPage() = currentPage.value?.let { openPage(it.index.minus(1)) }

    fun reopenPage() = openPage(currentPage.value?.index ?: 0)

    fun toggleBookmark() {
        val currentPage = currentPage.value?.index ?: return
        val document = document.value ?: return
        val bookmark = bookmarks.value?.firstOrNull { it.page == currentPage }

        viewModelScope.launch {
            if (bookmark == null) {
                interactors.addBookmark(document, Bookmark(page = currentPage))
            } else {
                interactors.deleteBookmark(document, bookmark)
            }

            bookmarks.postValue(interactors.getBookmarks(document))
        }
    }

    fun toggleInLibrary() {
        val document = document.value ?: return
        viewModelScope.launch {
            if (isInLibrary.value == true) {
                interactors.removeDocument(document)
            } else {
                interactors.addDocument(document)
            }

            isInLibrary.postValue(isInLibrary(document))
        }
    }
}