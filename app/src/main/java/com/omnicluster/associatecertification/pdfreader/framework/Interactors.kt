package com.omnicluster.associatecertification.pdfreader.framework

import com.omnicluster.certification.core.pdfreader.interactors.AddBookmark
import com.omnicluster.certification.core.pdfreader.interactors.AddDocument
import com.omnicluster.certification.core.pdfreader.interactors.GetBookmarks
import com.omnicluster.certification.core.pdfreader.interactors.GetDocuments
import com.omnicluster.certification.core.pdfreader.interactors.GetOpenDocument
import com.omnicluster.certification.core.pdfreader.interactors.RemoveBookmark
import com.omnicluster.certification.core.pdfreader.interactors.RemoveDocument
import com.omnicluster.certification.core.pdfreader.interactors.SetOpenDocument

data class Interactors(
    val addBookmark: AddBookmark,
    val getBookmarks: GetBookmarks,
    val deleteBookmark: RemoveBookmark,
    val addDocument: AddDocument,
    val getDocuments: GetDocuments,
    val removeDocument: RemoveDocument,
    val getOpenDocument: GetOpenDocument,
    val setOpenDocument: SetOpenDocument
)