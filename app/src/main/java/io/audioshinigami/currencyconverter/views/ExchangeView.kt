/*
 * MIT License
 *
 * Copyright (c) 2020  David Osemwota
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.audioshinigami.currencyconverter.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import io.audioshinigami.currencyconverter.R
import kotlinx.android.synthetic.main.exchange_view.view.*

class ExchangeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    companion object {
        private const val DEFAULT_EDIT_CURRENCY = false
        private const val DEFAULT_ICON_BORDER_COLOR = Color.BLACK
    }
    // convert int to dp
    private val Int.dip: Int
        get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat() , resources.displayMetrics).toInt()

    // to enable/disable currency edittext
    var editCurrencyValue = DEFAULT_EDIT_CURRENCY
        set(value) {
            field = value
            currency_value.isEnabled = field
        }

    // color of icon boundary
    var iconBorderColor = DEFAULT_ICON_BORDER_COLOR

    // text value
    var text = ""
        set(value) {
            field = value
            currency_code.text = field
        }

    init {
        View.inflate(context, R.layout.exchange_view, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.ExchangeView, 0 ,0)
            .apply {
                try {
                    // get attributes here
                    editCurrencyValue = getBoolean( R.styleable.ExchangeView_currency_editable, DEFAULT_EDIT_CURRENCY)
                    iconBorderColor = getColor(R.styleable.ExchangeView_icon_border_color, DEFAULT_ICON_BORDER_COLOR )
                    text = getString(R.styleable.ExchangeView_text) ?: ""

                }finally {
                    recycle()
                }
            }

        currency_code.text = text

        currency_value.isEnabled = editCurrencyValue

        ( currency_code.background as GradientDrawable )
            .apply {
                setStroke(3.dip, iconBorderColor)
            }


    }// END init

}