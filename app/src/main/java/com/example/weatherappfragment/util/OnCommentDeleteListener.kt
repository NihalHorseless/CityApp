package com.example.weatherappfragment.util

import com.example.weatherappfragment.model.Comment


interface OnCommentDeleteListener {
    fun onCommentDelete(comment: Comment)
}