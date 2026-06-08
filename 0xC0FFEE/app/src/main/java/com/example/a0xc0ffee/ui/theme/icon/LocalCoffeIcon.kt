package com.example.a0xc0ffee.ui.theme.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
val LocalCoffeeIcon: ImageVector
  get() {
    if (_local_cafe != null) {
      return _local_cafe!!
    }
    _local_cafe =
      ImageVector.Builder(
          name = "local_cafe",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.Companion.NonZero,
          ) {
            moveTo(4f, 21f)
            verticalLineTo(19f)
            horizontalLineTo(20f)
            verticalLineToRelative(2f)
            horizontalLineTo(4f)
            close()
            moveTo(8f, 17f)
            quadTo(6.35f, 17f, 5.18f, 15.83f)
            reflectiveQuadTo(4f, 13f)
            verticalLineTo(3f)
            horizontalLineTo(20f)
            quadToRelative(0.83f, 0f, 1.41f, 0.59f)
            reflectiveQuadTo(22f, 5f)
            verticalLineTo(8f)
            quadToRelative(0f, 0.82f, -0.59f, 1.41f)
            reflectiveQuadTo(20f, 10f)
            horizontalLineTo(18f)
            verticalLineToRelative(3f)
            quadToRelative(0f, 1.65f, -1.18f, 2.82f)
            reflectiveQuadTo(14f, 17f)
            horizontalLineTo(8f)
            close()
            moveTo(8f, 15f)
            horizontalLineToRelative(6f)
            quadToRelative(0.83f, 0f, 1.41f, -0.59f)
            reflectiveQuadTo(16f, 13f)
            verticalLineTo(5f)
            horizontalLineTo(6f)
            verticalLineToRelative(8f)
            quadToRelative(0f, 0.82f, 0.59f, 1.41f)
            reflectiveQuadTo(8f, 15f)
            close()
            moveTo(18f, 8f)
            horizontalLineToRelative(2f)
            verticalLineTo(5f)
            horizontalLineTo(18f)
            verticalLineTo(8f)
            close()
            moveTo(8f, 15f)
            quadTo(7.18f, 15f, 6.59f, 15f)
            reflectiveQuadTo(6f, 15f)
            horizontalLineTo(16f)
            quadToRelative(0f, 0f, -0.59f, 0f)
            reflectiveQuadTo(14f, 15f)
            horizontalLineTo(8f)
            close()
          }
        }
        .build()
    return _local_cafe!!
  }

private var _local_cafe: ImageVector? = null
