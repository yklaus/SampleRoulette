package com.example.sampleroulette

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.RotateAnimation
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class Roulette @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var rect = RectF()


    private val strokePaint = Paint()
    private val fillPaint = Paint()
    private val textPaint = Paint()

    var items = listOf<RouletteItem>()

    private val evenColor = Color.parseColor("#29CCFF")
    private val oddColor = Color.parseColor("#00ff00")

    init {
        strokePaint.apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 10f
            isAntiAlias = true
        }

        fillPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        textPaint.apply {
            color = Color.BLACK
            textSize = 30f
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val diameter = min(width, height) - strokePaint.strokeWidth
        rect.set(
            0f + strokePaint.strokeWidth / 2,
            0f + strokePaint.strokeWidth / 2,
            diameter,
            diameter
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val totalWeight = items.asSequence().map { it.weight }.sum()
        var startAngle = -90f

        items.forEachIndexed { index, (option, weight) ->
            val sweepAngle = (weight / totalWeight.toFloat()) * 360f
            fillPaint.color = if (index % 2 == 0) evenColor else oddColor
            canvas.drawArc(rect, startAngle, sweepAngle, true, fillPaint)

            drawOptionText(canvas, option, startAngle, sweepAngle)
            startAngle += sweepAngle
        }

        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, strokePaint)
    }

    private fun drawOptionText(canvas: Canvas, option: String, startAngle: Float, sweepAngle: Float) {
        val textRadius = rect.width() / 4  // Increase radius to move text outside the circle
        val angle = Math.toRadians((startAngle + sweepAngle / 2).toDouble()).toFloat()

        // Calculate the text position
        val x = rect.centerX() + textRadius * cos(angle) + 10
        val y = rect.centerY() + textRadius * sin(angle)

        // Save the canvas state
        val saveCount = canvas.save()

        // Rotate the canvas around the text position
        canvas.rotate(startAngle + sweepAngle / 2, x, y)

        // Draw the text aligned with the segment
        canvas.drawText(option, x, y, textPaint)

        // Restore the canvas to its previous state
        canvas.restoreToCount(saveCount)
    }

    private fun getAngleForOption(option: String): Float {
        val totalWeight = items.asSequence().map { it.weight }.sum()
        var startAngle = 0f

        items.forEach { (currentOption, weight) ->
            val sweepAngle = (weight / totalWeight.toFloat()) * 360f
            if (currentOption == option) {
                // Return the midpoint angle of the segment
                return (startAngle + sweepAngle / 2)
            }
            startAngle += sweepAngle
        }
        return 0f
    }

    fun rotateToOption(option: String) {
        val targetAngle = 0 - (getAngleForOption(option) + 360 * 10)

        val rotateAnimation = RotateAnimation(
            0f, targetAngle,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.duration = 2000
        rotateAnimation.fillAfter = true

        startAnimation(rotateAnimation)
    }
}

data class RouletteItem(val option:String, val weight: Int)