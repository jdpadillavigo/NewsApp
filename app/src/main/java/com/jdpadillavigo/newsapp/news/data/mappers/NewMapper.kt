package com.jdpadillavigo.newsapp.news.data.mappers

import com.jdpadillavigo.newsapp.news.data.networking.dto.NewDto
import com.jdpadillavigo.newsapp.news.data.networking.dto.SourceDto
import com.jdpadillavigo.newsapp.news.domain.New
import com.jdpadillavigo.newsapp.news.domain.Source

fun NewDto.toNew(): New {
    return New(
        source = (source ?: SourceDto()).toSource(),
        author = author.orEmpty(),
        title = title.orEmpty(),
        description = description.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        content = content.orEmpty()
    )
}

fun SourceDto.toSource(): Source {
    return Source(
        id = id,
        name = name
    )
}