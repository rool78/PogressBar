package com.rool.progressbar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.rool.R
import com.rool.progressbar.listeners.OnProgressChangedListener

class ProgressBar : FrameLayout {

    var maxProgress: Int = 100

    var progress: Int = 0
        set(value) {
            field = value
            updateProgress()
            onProgressListener?.onProgressChanged(this.progress)
        }

    @ColorInt
    var colorProgress: Int = Color.MAGENTA

    @ColorInt
    var colorBackground: Int = Color.WHITE
        set(value) {
            field = value
            updateBackground()
        }

    var labelText: CharSequence? = ""
        set(value) {
            field = value
            this.progressLabel.text = value
        }

    @ColorInt
    var labelColor: Int = Color.BLACK
        set(value) {
            field = value
            progressLabel.setTextColor(labelColor)
        }

    var onProgressListener: OnProgressChangedListener? = null

    private val progressBar = View(context)
    private val progressLabel = TextView(context)

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attributeSet: AttributeSet
    ) : this(context, attributeSet, 0)

    constructor(
        context: Context,
        attributeSet: AttributeSet,
        defStyle: Int
    ) : super(
        context,
        attributeSet,
        defStyle
    ) {
        getAttrs(attributeSet, defStyle)
    }

    fun setOnProgressChangedListener(block: (Int) -> Unit) {
        this.onProgressListener = object : OnProgressChangedListener {
            override fun onProgressChanged(currentProgress: Int) {
                block(currentProgress)
            }
        }
    }

    private fun getAttrs(
        attributeSet: AttributeSet,
        defStyleAttr: Int
    ) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.ProgressBar, defStyleAttr, 0)
        try {
            setTypeArray(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun setTypeArray(typedArray: TypedArray) {
        this.labelText = typedArray.getString(R.styleable.ProgressBar_progressBar_labelText)
        this.labelColor =
            typedArray.getColor(R.styleable.ProgressBar_progressBar_colorLabelText, labelColor)
        this.colorProgress =
            typedArray.getColor(R.styleable.ProgressBar_progressBar_colorProgress, colorProgress)
        this.colorBackground =
            typedArray.getColor(R.styleable.ProgressBar_progressBar_colorBackground, colorBackground)
    }

    private fun updateProgress() {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        if (this.progress < maxProgress) {
            params.width = getProportionalProgress()
        }
        updateProgressBar(params)
        updateLabel()
    }

    private fun updateProgressBar(params: LayoutParams) {
        progressBar.background = GradientDrawable().apply {
            setColor(colorProgress)
        }
        progressBar.layoutParams = params
        removeView(progressBar)
        addView(progressBar)
    }

    private fun updateLabel() {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        progressLabel.layoutParams = params
        progressLabel.gravity = Gravity.END
        removeView(progressLabel)
        addView(progressLabel)
    }

    private fun getProportionalProgress(): Int =
        (progress * this.width) / maxProgress

    private fun updateBackground() {
        background = GradientDrawable().apply {
            setColor(ProgressBar@colorBackground)
        }
    }
}
