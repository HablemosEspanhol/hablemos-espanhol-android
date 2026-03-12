package io.github.cadnunsdimir.android.javierchopeklecciones.app.service

object NotificationService {
    val _subscriptions = mutableListOf<NotificationSubscription>()

    fun subscribe(callback: (message: String)-> Unit) : NotificationSubscription {
           var subscription = NotificationSubscription(callback)
        _subscriptions.add(subscription)
        return subscription
    }

    fun notify(message: String) {
        _subscriptions.forEach { subscription -> subscription.callback.invoke(message) }
    }
}

class NotificationSubscription(val callback: (String) -> Unit) {
    fun unsubscribe() {
        NotificationService._subscriptions.remove(this);
    }
}


