package com.omnicluster.associatecertification.pdfreader.framework

import com.omnicluster.certification.core.pdfreader.data.OpenDocumentDataSource
import com.omnicluster.certification.core.pdfreader.domain.Document

class InMemoryOpenDocumentDataSource : OpenDocumentDataSource {

    private var openDocument: Document = Document.EMPTY

    override fun setOpenDocument(document: Document) {
        openDocument = document
    }

    override fun getOpenDocument() = openDocument
}