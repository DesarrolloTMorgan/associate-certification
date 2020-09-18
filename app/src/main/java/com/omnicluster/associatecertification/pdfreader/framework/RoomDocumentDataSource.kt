package com.omnicluster.associatecertification.pdfreader.framework

import android.content.Context
import com.omnicluster.associatecertification.pdfreader.framework.db.DocumentEntity
import com.omnicluster.associatecertification.pdfreader.framework.db.PDFReaderDatabase
import com.omnicluster.certification.core.pdfreader.data.DocumentDataSource
import com.omnicluster.certification.core.pdfreader.domain.Document

class RoomDocumentDataSource(private val context: Context) : DocumentDataSource {

    private val documentDao = PDFReaderDatabase.getInstance(context).documentDao()

    override suspend fun add(document: Document) {
        val details = FileUtil.getDocumentDetails(context, document.url)
        documentDao.addDocument(
            DocumentEntity(document.url, details.name, details.size, details.thumbnail)
        )
    }

    override suspend fun readAll(): List<Document> = documentDao.getDocuments().map {
        Document(
            it.uri,
            it.title,
            it.size,
            it.thumbnailUri
        )
    }

    override suspend fun remove(document: Document) = documentDao.removeDocument(
        DocumentEntity(document.url, document.name, document.size, document.thumbnail)
    )
}