package org.example

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun main(args: Array<String>) {

    val token = args[0]
    val urlGetMe = "https://api.telegram.org/bot$token/getMe"
    val urlGetUpdates = "https://api.telegram.org/bot$token/getUpdates"

    val getMeResponse = sendHttpRequest(urlGetMe)
    val getUpdatesResponse = sendHttpRequest(urlGetUpdates)

    println("getMe response: ${getMeResponse.body()}")
    println("getUpdates response: ${getUpdatesResponse.body()}")
}

fun sendHttpRequest(url: String): HttpResponse<String> {
    val httpClient = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder().uri(URI.create(url)).build()

    return httpClient.send(request, HttpResponse.BodyHandlers.ofString())
}