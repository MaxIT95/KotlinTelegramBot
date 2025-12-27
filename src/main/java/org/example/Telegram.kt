package org.example

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

const val TELEGRAM_URL = "https://api.telegram.org/bot"

fun main(args: Array<String>) {

    val token = args[0]
    var updateId = 0

    while (true) {
        Thread.sleep(2000)
        val updatesResponse = getUpdates(token, updateId)
        println("getUpdates response: $updatesResponse")
        val startUpdateId = updatesResponse.indexOf("update_id")
        val endUpdateId = updatesResponse.lastIndexOf(",\n\"message\"")
        if (startUpdateId == -1 || endUpdateId == -1) {
            continue
        }
        val substring = updatesResponse.substring(startUpdateId + 11, endUpdateId)

        updateId = substring.toInt() + 1
    }
}

fun getUpdates(botToken: String, updateId: Int): String {
    val urlGetUpdates = "$TELEGRAM_URL$botToken/getUpdates?offset=$updateId"
    val getUpdatesResponse = sendHttpRequest(urlGetUpdates)

    return getUpdatesResponse.body()
}

fun sendHttpRequest(url: String): HttpResponse<String> {
    val httpClient = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder().uri(URI.create(url)).build()

    return httpClient.send(request, HttpResponse.BodyHandlers.ofString())
}