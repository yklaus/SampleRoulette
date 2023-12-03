package com.example.sampleroulette

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class InvertedTriangle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val trianglePath = Path()
    private var trianglePaint = Paint()

    private val triangleSize = 60f

    init {
        trianglePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED  // Set the color of the triangle
            style = Paint.Style.FILL  // Fill the triangle
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Clear the old path
        trianglePath.reset()

        // Define the new path for the inverted triangle
        // Starting point (top of the triangle)
        trianglePath.moveTo((width / 2f) - 30, 10f)
        // Line to bottom left of the triangle
        trianglePath.lineTo((width / 2f) + 30, 10f)
        // Line to bottom right of the triangle
        trianglePath.lineTo(width / 2f, 10f + triangleSize)
        // Close the path to form a triangle
        trianglePath.close()

        canvas.drawPath(trianglePath, trianglePaint)
    }
}