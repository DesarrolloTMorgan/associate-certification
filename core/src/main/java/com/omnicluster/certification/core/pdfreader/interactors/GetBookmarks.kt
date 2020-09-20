package com.omnicluster.certification.core.pdfreader.interactors

import com.omnicluster.certification.core.pdfreader.data.BookmarkRepository
import com.omnicluster.certification.core.pdfreader.domain.Document

class GetBookmarks(private val bookmarkRepository: BookmarkRepository) {
    suspend operator fun invoke(document: Document) = bookmarkRepository.getBookmarks(document)
}