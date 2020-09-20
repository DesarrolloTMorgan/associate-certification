package com.omnicluster.associatecertification.pdfreader.presentation

import com.omnicluster.certification.core.pdfreader.domain.Document

interface OpenDocumentInterface {

    fun openDocument(document: Document)
}