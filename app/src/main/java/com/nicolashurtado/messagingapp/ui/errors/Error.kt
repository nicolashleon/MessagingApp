package com.nicolashurtado.messagingapp.ui.errors

sealed class Error {
    data class AttachmentDeleteError(val throwable: Throwable) : Error()
    data class MessageDeleteError(val throwable: Throwable) : Error()
}
