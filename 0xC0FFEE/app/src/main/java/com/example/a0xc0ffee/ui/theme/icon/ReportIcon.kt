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
val ReportIcon: ImageVector
  get() {
    if (_analytics != null) {
      return _analytics!!
    }
    _analytics =
      ImageVector.Builder(
          name = "analytics",
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
            moveTo(7f, 17f)
            horizontalLineTo(9f)
            verticalLineTo(12f)
            horizontalLineTo(7f)
            verticalLineToRelative(5f)
            close()
            moveToRelative(8f, 0f)
            horizontalLineToRelative(2f)
            verticalLineTo(7f)
            horizontalLineTo(15f)
            verticalLineTo(17f)
            close()
            moveToRelative(-4f, 0f)
            horizontalLineToRelative(2f)
            verticalLineTo(14f)
            horizontalLineTo(11f)
            verticalLineToRelative(3f)
            close()
            moveToRelative(0f, -5f)
            horizontalLineToRelative(2f)
            verticalLineTo(10f)
            horizontalLineTo(11f)
            verticalLineToRelative(2f)
            close()
            moveTo(5f, 21f)
            quadTo(4.18f, 21f, 3.59f, 20.41f)
            reflectiveQuadTo(3f, 19f)
            verticalLineTo(5f)
            quadTo(3f, 4.17f, 3.59f, 3.59f)
            reflectiveQuadTo(5f, 3f)
            horizontalLineTo(19f)
            quadToRelative(0.83f, 0f, 1.41f, 0.59f)
            reflectiveQuadTo(21f, 5f)
            verticalLineTo(19f)
            quadToRelative(0f, 0.82f, -0.59f, 1.41f)
            reflectiveQuadTo(19f, 21f)
            horizontalLineTo(5f)
            close()
            moveTo(5f, 19f)
            horizontalLineTo(19f)
            verticalLineTo(5f)
            horizontalLineTo(5f)
            verticalLineTo(19f)
            close()
            moveTo(5f, 5f)
            verticalLineTo(19f)
            verticalLineTo(5f)
            close()
          }
        }
        .build()
    return _analytics!!
  }

private var _analytics: ImageVector? = null
