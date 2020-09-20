package com.omnicluster.certification.core.pdfreader.interactors

import com.omnicluster.certification.core.pdfreader.data.DocumentRepository

class GetOpenDocument(private val documentRepository: DocumentRepository) {
    operator fun invoke() = documentRepository.getOpenDocument()
}