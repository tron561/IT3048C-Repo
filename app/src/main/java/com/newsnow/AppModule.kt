package com.newsnow

import com.newsnow.MainViewModel
import com.newsnow.service.ArticleService
import com.newsnow.service.IArticleService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val appModule = module {
    viewModel { MainViewModel() }
}