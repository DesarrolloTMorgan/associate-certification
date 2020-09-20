package com.omnicluster.certification.core.pdfreader.interactors

import com.omnicluster.certification.core.pdfreader.data.DocumentRepository
import com.omnicluster.certification.core.pdfreader.domain.Document

class RemoveDocument(private val documentRepository: DocumentRepository) {
    suspend operator fun invoke(document: Document) = documentRepository.removeDocument(document)
}