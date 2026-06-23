package com.example.a0xc0ffee.ui.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.TextFieldColors
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val BackgroundColor = Color(0xFFFCF7D0)
val PrimaryColor = Color(0xFF7CD164)
val FadedPrimaryColor = Color(0xAA7CD164)
val SecondaryColor = Color(0xFF2EB8AC)
val FadedSecondaryColor = Color(0xAA2EB8AC)
val TextColor = Color(0xFFFAFAFA)
val FadedTextColor = Color(0xAAFAFAFA)
val AccentColor = Color(0xFF2F2E30)
val FadedAccentColor = Color(0xAA2F2E30)
val ErrorColor = Color(0xFFE84B2C)
val FadedErrorColor = Color(0xAAE84B2C)
val WarningColor = Color(0xFFE8922C)
val FadedWarningColor = Color(0xAAE8922C)

val CheckboxThemedColors = CheckboxColors (
    TextColor,
    PrimaryColor,
    PrimaryColor,
    BackgroundColor,
    PrimaryColor,
    PrimaryColor,
    PrimaryColor,
    PrimaryColor,
    PrimaryColor,
    ErrorColor,
    ErrorColor,
    ErrorColor
)

val SegmentedButtonThemedColors = SegmentedButtonColors(
    activeContainerColor = SecondaryColor,
    activeContentColor = TextColor,
    activeBorderColor = BackgroundColor,
    inactiveContainerColor = FadedSecondaryColor,
    inactiveContentColor = FadedTextColor,
    inactiveBorderColor = BackgroundColor,
    disabledActiveContainerColor = PrimaryColor,
    disabledActiveContentColor = PrimaryColor,
    disabledActiveBorderColor = BackgroundColor,
    disabledInactiveContainerColor = PrimaryColor,
    disabledInactiveContentColor = PrimaryColor,
    disabledInactiveBorderColor = BackgroundColor
)

val ButtonThemedColors = ButtonColors(
    SecondaryColor,
    TextColor,
    ErrorColor,
    WarningColor
)

val TextFieldThemedColors = TextFieldColors(
    focusedTextColor = PrimaryColor,
    unfocusedTextColor = PrimaryColor,
    disabledPlaceholderColor = PrimaryColor,
    focusedPlaceholderColor = FadedSecondaryColor,
    unfocusedPlaceholderColor = PrimaryColor,
    errorPlaceholderColor = ErrorColor,
    errorTextColor = ErrorColor,
    cursorColor = PrimaryColor,
    disabledTextColor = ErrorColor,
    focusedContainerColor = BackgroundColor,
    unfocusedContainerColor = BackgroundColor,
    disabledContainerColor = BackgroundColor,
    errorContainerColor = BackgroundColor,
    errorCursorColor = ErrorColor,
    focusedIndicatorColor = PrimaryColor,
    unfocusedIndicatorColor = PrimaryColor,
    disabledIndicatorColor = PrimaryColor,
    errorIndicatorColor = ErrorColor,
    focusedLeadingIconColor = FadedTextColor,
    unfocusedLeadingIconColor = FadedTextColor,
    disabledLeadingIconColor = FadedTextColor,
    errorLeadingIconColor = FadedTextColor,
    focusedTrailingIconColor = FadedTextColor,
    unfocusedTrailingIconColor = FadedTextColor,
    disabledTrailingIconColor = FadedTextColor,
    errorTrailingIconColor = FadedTextColor,
    focusedLabelColor = PrimaryColor,
    unfocusedLabelColor = PrimaryColor,
    disabledLabelColor = PrimaryColor,
    errorLabelColor = ErrorColor,
    focusedSupportingTextColor = FadedTextColor,
    unfocusedSupportingTextColor = FadedTextColor,
    disabledSupportingTextColor = FadedTextColor,
    errorSupportingTextColor = FadedTextColor,
    focusedPrefixColor = FadedTextColor,
    unfocusedPrefixColor = FadedTextColor,
    disabledPrefixColor = FadedTextColor,
    errorPrefixColor = FadedTextColor,
    focusedSuffixColor = FadedTextColor,
    unfocusedSuffixColor = FadedTextColor,
    disabledSuffixColor = FadedTextColor,
    errorSuffixColor = FadedTextColor,
    textSelectionColors = TextSelectionColors(PrimaryColor, AccentColor)
)