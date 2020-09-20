package com.omnicluster.certification.core.pdfreader.interactors

import com.omnicluster.certification.core.pdfreader.data.BookmarkRepository
import com.omnicluster.certification.core.pdfreader.domain.Bookmark
import com.omnicluster.certification.core.pdfreader.domain.Document

class RemoveBookmark(private val bookmarkRepository: BookmarkRepository) {
    suspend operator fun invoke(document: Document, bookmark: Bookmark) =
        bookmarkRepository.removeBookmark(document, bookmark)
}