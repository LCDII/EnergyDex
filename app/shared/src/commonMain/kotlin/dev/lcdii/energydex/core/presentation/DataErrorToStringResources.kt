package dev.lcdii.energydex.core.presentation


import dev.lcdii.energydex.core.domain.DataError
import energydex.app.shared.generated.resources.Res
import energydex.app.shared.generated.resources.error_disk_full
import energydex.app.shared.generated.resources.error_unknown

fun DataError.toUiText(): UiText{
    val stringRes = when(this){
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        DataError.Local.ALREADY_EXISTS -> Res.string.error_unknown//TODO change
        DataError.Local.DOESNT_EXISTS -> Res.string.error_unknown//TODO change
    }

    return UiText.StringResourceId(stringRes)
}

fun Throwable.toUiText(): UiText {
    return when (this) {
        else -> UiText.StringResourceId(Res.string.error_unknown)
    }
}
