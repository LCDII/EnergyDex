package com.example.energydeks.core.domain

sealed interface DataError: Error {

    enum class Local: DataError{
        DISK_FULL,
        ALREADY_EXISTS,
        DOESNT_EXISTS,
        UNKNOWN
    }
}