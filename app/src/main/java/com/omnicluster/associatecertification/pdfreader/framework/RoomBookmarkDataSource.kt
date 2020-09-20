package com.omnicluster.associatecertification.pdfreader.framework

import android.content.Context
import com.omnicluster.associatecertification.pdfreader.framework.db.BookmarkEntity
import com.omnicluster.associatecertification.pdfreader.framework.db.PDFReaderDatabase
import com.omnicluster.certification.core.pdfreader.data.BookmarkDataSource
import com.omnicluster.certification.core.pdfreader.domain.Bookmark
import com.omnicluster.certification.core.pdfreader.domain.Document

class RoomBookmarkDataSource(context: Context) : BookmarkDataSource {

    private val bookmarkDao = PDFReaderDatabase.getInstance(context).bookmarkDao()

    override suspend fun add(document: Document, bookmark: Bookmark) =
        bookmarkDao.addBookmark(
            BookmarkEntity(
                documentUri = document.url,
                page = bookmark.page
            )
        )

    override suspend fun read(document: Document): List<Bookmark> = bookmarkDao
        .getBookmarks(document.url).map { Bookmark(it.id, it.page) }

    override suspend fun remove(document: Document, bookmark: Bookmark) =
        bookmarkDao.removeBookmark(
            BookmarkEntity(id = bookmark.id, documentUri = document.url, page = bookmark.page)
        )
}