package com.omnicluster.certification.core.pdfreader.interactors

import com.omnicluster.certification.core.pdfreader.data.DocumentRepository
import com.omnicluster.certification.core.pdfreader.domain.Document

class SetOpenDocument(private val documentRepository: DocumentRepository) {
    operator fun invoke(document: Document) = documentRepository.setOpenDocument(document)
}