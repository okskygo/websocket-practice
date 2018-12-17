package com.tripresso.testwebsocket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import kotlinx.android.synthetic.main.activity_main.button
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    OkHttpClient.Builder()

    val scarletInstance = Scarlet.Builder()
      .webSocketFactory(provideOkHttpClient().newWebSocketFactory("ws://10.0.2.2:1234"))
      .addMessageAdapterFactory(MoshiMessageAdapter.Factory())
      .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
      .build()

    val service = scarletInstance.create<WebsocketService>()
    button.setOnClickListener {
      service.sendSubscribe("helll world")
    }
    service.observeWebSocketEvent()
      .subscribe({ event ->
                   println(">>>>>>>>>>> event = ${event}")
                 }, { throwable: Throwable? ->
                   println(">>>>>>>>>>> Throwable = ${throwable}")
                 })
    service.observeTicker()
      .subscribe { ticker ->
        println(">>>>>>>>>>> ticker = ${ticker}")
      }

  }

  fun provideOkHttpClient(): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    return httpClient.build()
  }
}

data class Ticker(
  val type: String
)

data class Subscribe(
  val type: String
)