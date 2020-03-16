package io.audioshinigami.currencyconverter.convertAmount.events

sealed class Event(val message: String){

    class SnackBarEvent(val _message: String): Event(_message)
    class ToastEvent(val _message: String): Event(_message)
}
