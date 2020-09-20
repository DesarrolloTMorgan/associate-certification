package com.omnicluster.associatecertification

import android.app.Application
import android.os.Build
import com.omnicluster.associatecertification.pdfreader.framework.InMemoryOpenDocumentDataSource
import com.omnicluster.associatecertification.pdfreader.framework.Interactors
import com.omnicluster.associatecertification.pdfreader.framework.PDFViewModelFactory
import com.omnicluster.associatecertification.pdfreader.framework.RoomBookmarkDataSource
import com.omnicluster.associatecertification.pdfreader.framework.RoomDocumentDataSource
import com.omnicluster.certification.core.pdfreader.data.BookmarkRepository
import com.omnicluster.certification.core.pdfreader.data.DocumentRepository
import com.omnicluster.certification.core.pdfreader.interactors.AddBookmark
import com.omnicluster.certification.core.pdfreader.interactors.AddDocument
import com.omnicluster.certification.core.pdfreader.interactors.GetBookmarks
import com.omnicluster.certification.core.pdfreader.interactors.GetDocuments
import com.omnicluster.certification.core.pdfreader.interactors.GetOpenDocument
import com.omnicluster.certification.core.pdfreader.interactors.RemoveBookmark
import com.omnicluster.certification.core.pdfreader.interactors.RemoveDocument
import com.omnicluster.certification.core.pdfreader.interactors.SetOpenDocument

class AssociateCertificationApp : Application() {

    override fun onCreate() {
        super.onCreate()
        injectPDFViewModelData()
    }

    private fun injectPDFViewModelData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val bookmarkRepository = BookmarkRepository(RoomBookmarkDataSource(this))
            val documentRepository = DocumentRepository(
                RoomDocumentDataSource(this),
                InMemoryOpenDocumentDataSource()
            )

            PDFViewModelFactory.inject(
                this,
                Interactors(
                    AddBookmark(bookmarkRepository),
                    GetBookmarks(bookmarkRepository),
                    RemoveBookmark(bookmarkRepository),
                    AddDocument(documentRepository),
                    GetDocuments(documentRepository),
                    RemoveDocument(documentRepository),
                    GetOpenDocument(documentRepository),
                    SetOpenDocument(documentRepository)
                )
            )
        }
    }
}