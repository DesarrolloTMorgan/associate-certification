package com.omnicluster.certification.core.pdfreader.data

import com.omnicluster.certification.core.pdfreader.domain.Document

interface OpenDocumentDataSource {

    fun setOpenDocument(document: Document)

    fun getOpenDocument(): Document
}