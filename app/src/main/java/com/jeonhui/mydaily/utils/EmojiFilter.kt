package com.jeonhui.mydaily.utils

import android.text.InputFilter
import android.text.Spanned

class EmojiFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        for (i in start until end) {
            val src = source?.get(i) ?: return ""
            val type = Character.getType(src).toByte()
            if (type != Character.SURROGATE && type != Character.OTHER_SYMBOL) {
                return ""
            }
        }
        return source
    }
}