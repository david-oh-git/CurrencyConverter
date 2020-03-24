package io.audioshinigami.currencyconverter.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import io.audioshinigami.currencyconverter.R
import kotlinx.android.synthetic.main.exchange_view.view.currency_icon
import kotlinx.android.synthetic.main.exchange_view.view.currency_value

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

    init {
        View.inflate(context, R.layout.exchange_view, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.ExchangeView, 0 ,0)
            .apply {
                try {
                    // get attributes here
                    editCurrencyValue = getBoolean( R.styleable.ExchangeView_currency_editable, DEFAULT_EDIT_CURRENCY)
                    iconBorderColor = getColor(R.styleable.ExchangeView_icon_border_color, DEFAULT_ICON_BORDER_COLOR )

                }finally {
                    recycle()
                }
            }

        ( currency_icon.background as GradientDrawable )
            .apply {
                setStroke(4.dip, iconBorderColor)
            }

        currency_value.isEnabled = editCurrencyValue
    }// END init

}