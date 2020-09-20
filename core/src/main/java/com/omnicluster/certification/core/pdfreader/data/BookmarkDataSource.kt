package com.omnicluster.certification.core.pdfreader.data

import com.omnicluster.certification.core.pdfreader.domain.Bookmark
import com.omnicluster.certification.core.pdfreader.domain.Document

interface BookmarkDataSource {

    suspend fun add(document: Document, bookmark: Bookmark)

    suspend fun read(document: Document): List<Bookmark>

    suspend fun remove(document: Document, bookmark: Bookmark)
}