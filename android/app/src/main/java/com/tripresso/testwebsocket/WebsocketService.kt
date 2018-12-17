package com.tripresso.testwebsocket

import com.tinder.scarlet.WebSocket.Event
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface WebsocketService {

  @Receive
  fun observeWebSocketEvent(): Flowable<Event>

  @Send
  fun sendSubscribe(subscribe: String)

  @Receive
  fun observeTicker(): Flowable<String>

}