package com.rool.progressbar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

class ProgressBar  : FrameLayout {

    private val progressLabel : TextView = TextView(context)
    private var currentProgress: Int = 0
    private var maxProgress: Int = 100

    private val progressBarBackground = View(context)

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attributeSet: AttributeSet
    ) : this(context, attributeSet, 0) {
        progressBarBackground.background = GradientDrawable().apply {
            setColor(Color.GREEN)
        }
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        progressLabel.layoutParams = params
        progressLabel.gravity = Gravity.END
        progressLabel.setTextColor(Color.RED)
        addView(progressLabel)
    }

    constructor(
        context: Context,
        attributeSet: AttributeSet,
        defStyle: Int
    ) : super(
        context,
        attributeSet,
        defStyle
    )

    fun setProgress(progress: Int) {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        if (currentProgress < maxProgress) {
            this.currentProgress += progress
            params.width = getProportionalProgress()
            progressLabel.text = currentProgress.toString()
        }
        progressBarBackground.layoutParams = params
        removeView(progressBarBackground)
        addView(progressBarBackground)
    }

    private fun getProportionalProgress() : Int =
        (currentProgress * this.width) / maxProgress

}