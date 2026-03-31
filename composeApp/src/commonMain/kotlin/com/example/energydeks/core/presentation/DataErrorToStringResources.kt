package com.example.energydeks.core.presentation


import com.example.energydeks.core.domain.DataError
import energydeks.composeapp.generated.resources.Res
import energydeks.composeapp.generated.resources.error_disk_full
import energydeks.composeapp.generated.resources.error_unknown

fun DataError.toUiText(): UiText{
    val stringRes = when(this){
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Local.UNKNOWN -> Res.string.error_unknown
    }

    return UiText.StringResourceId(stringRes)
}