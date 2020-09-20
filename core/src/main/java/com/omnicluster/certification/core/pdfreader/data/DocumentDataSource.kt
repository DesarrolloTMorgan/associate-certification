package com.omnicluster.certification.core.pdfreader.data

import com.omnicluster.certification.core.pdfreader.domain.Document

interface DocumentDataSource {

    suspend fun add(document: Document)

    suspend fun readAll(): List<Document>

    suspend fun remove(document: Document)
}