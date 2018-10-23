package com.antonijzelinski.spantastic

import android.content.Context
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.*
import android.util.TypedValue
import android.view.View

class Spantastic(val context: Context) {

    companion object {
        @JvmStatic
        val TAG = Spantastic::class.java.simpleName

        @JvmStatic
        fun spToPx(sp: Int, context: Context): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), context.resources.displayMetrics).toInt()
        }

        @JvmStatic
        fun make(context: Context): Spantastic {
            return Spantastic(context)
        }
    }

    private val spanList = mutableListOf<SpannableString>()

    private var spanType = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

    private val currentSpan
        get() = spanList.last()

    /**
     * Поочередно складывает все элементы массива [spanList] и возвращает результат
     * @return SpannableString
     */
    fun apply(): SpannableString {
        var completableSpan = SpannableString("");
        spanList.forEach {
            completableSpan = SpannableString(TextUtils.concat(completableSpan, it))
        }
        return completableSpan
    }

    /**
     * Добавляет в конец массива [spanList] новую строку над которой будет выполнено следующее преобразование.
     */
    fun add(@StringRes strId: Int): Spantastic {
        add(context.getString(strId))
        return this
    }

    /**
     * Добавляет в конец массива [spanList] новую строку над которой будет выполнено следующее преобразование
     */
    fun add(str: String): Spantastic {
        spanList.add(SpannableString(str))
        return this
    }

    /**
     * Добавляет символ "пробел" в конец массива [spanList].
     */
    fun space(): Spantastic {
        spanList.add(SpannableString(" "))
        return this
    }

    /**
     * Окрашивает последний элемент массива [spanList] в выбранный цвет.
     */
    fun color(@ColorRes colorId: Int): Spantastic {
        val intColor = ContextCompat.getColor(context, colorId)
        colorInt(intColor)
        return this
    }

    /**
     * Окрашивает последний элемент массива [spanList] в выбранный цвет.
     */
    fun colorInt(@ColorInt colorInt: Int): Spantastic {
        currentSpan.setSpan(ForegroundColorSpan(colorInt), 0, currentSpan.length, spanType)
        return this
    }

    /**
     * Добавляет нижнее подчеркивание последнему элементу массива [spanList] в выбранный цвет.
     */
    fun underline(): Spantastic {
        currentSpan.setSpan(UnderlineSpan(), 0, currentSpan.length, spanType)
        return this
    }

    /**
     * Меняет шрифт последнего элемента массива [spanList].
     */
    fun font(@FontRes fontId: Int): Spantastic {
        val typeface = ResourcesCompat.getFont(context, fontId)
        typeface?.let {
            currentSpan.setSpan(TypefaceSpantastic(typeface), 0, currentSpan.length, spanType)
        }
        return this
    }

    /**
     * Меняет абсолютный размер шрифта последнего элемента массива [spanList].
     * @param size размер шрифта
     * @param convertToSp Если true - конвертирует значение параметра [size] в sp, иначе интерпретирует параметр [size] как количество пикселей
     */
    fun absSize(size: Int, convertToSp: Boolean = true): Spantastic {
        if(convertToSp) {
            currentSpan.setSpan(AbsoluteSizeSpan(spToPx(size, context)), 0, currentSpan.length, spanType)
        } else {
            currentSpan.setSpan(AbsoluteSizeSpan(size), 0, currentSpan.length, spanType)
        }
        return this
    }

    /**
     * Меняет относительный размер шрифта последнего элемента массива [spanList].
     */
    fun relSize(fontId: Float): Spantastic {
        currentSpan.setSpan(RelativeSizeSpan(fontId), 0, currentSpan.length, spanType)
        return this
    }

    /**
     * Окрашивает цвет фона последнему элементу массива [spanList] в выбранный цвет.
     */
    fun background(@ColorRes colorId: Int): Spantastic {
        val intColor = ContextCompat.getColor(context, colorId)
        backgroundInt(intColor)
        return this
    }

    /**
     * Окрашивает цвет фона последнему элементу массива [spanList] в выбранный цвет.
     */
    fun backgroundInt(@ColorInt colorInt: Int): Spantastic {
        currentSpan.setSpan(BackgroundColorSpan(colorInt), 0, currentSpan.length, spanType)
        return this
    }

    /**
     * Задает стиль шрифта последнему элементу массива [spanList] в выбранный цвет.
     */
    fun style(style: Style): Spantastic {
        currentSpan.setSpan(StyleSpan(style.type), 0, currentSpan.length, spanType)
        return this
    }

    /**
     * Задает стиль шрифта последнему элементу массива [spanList] в выбранный цвет.
     */
    fun listener(action:() -> Unit): Spantastic {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                action()
            }
        }
        currentSpan.setSpan(clickableSpan, 0, currentSpan.length, spanType)
        return this
    }
    //TODO: Big First Sumbhol
}

