package com.hermanek.moviebrowserdemo.util


/**
 * Created by jhermanek on 01.03.2022.
 */

interface SupportedLanguages {
    public val english: String
        get() = "en-US"

    val czech: String
        get() = "cs-CZ"

    val default: String
        get() = "en-US"

}