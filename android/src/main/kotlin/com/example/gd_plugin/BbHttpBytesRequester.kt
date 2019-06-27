package com.example.gd_plugin

import android.os.AsyncTask

import com.good.gd.apache.http.client.methods.HttpGet
import com.good.gd.net.GDHttpClient

import java.io.IOException

import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import com.good.gd.apache.http.message.BasicHeader


class BbHttpBytesRequester : AsyncTask<String, Void, ByteArray>() {

    override fun doInBackground(vararg url: String): ByteArray {
        val httpclient = GDHttpClient()
//        httpclient.disableHostVerification();
//        httpclient.disablePeerVerification();
        val request = HttpGet(url[0])

        print("DEEBUG: DOING BB HTTP REQUEST WITHOUT HEADERS")

////        val headers = arrayOf( BasicHeader("X-User-Id", "Ffmr8gbpRqx7EHP5T"),
////            BasicHeader("X-Auth-Token", "jHXEkXPrXUqD6iR8lpSykjvP3ExCtGmT8_81StPyZyG"))
//        val headers = arrayOf( BasicHeader("X-User-Id", "bnouNi6Ri7Ra97dHR"),
//                BasicHeader("X-Auth-Token", "4w6vAAy5IQxpasHwAzhrUpsRezkVEhTF6YcHiRMoM7h"))
//        request.setHeaders(headers)
//        print("DEEBUG: BB HTTP request WITH headers")

        try {
            val response = httpclient.execute(request)
            val stream = response.entity.content
            println("response was executed")
            println(stream.toString())

            val bis = BufferedInputStream(stream)
            val buf = ByteArrayOutputStream()
            var result = bis.read()
            while (result != -1) {
                buf.write(result.toByte().toInt())
                result = bis.read()
            }
            return buf.toByteArray()

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ByteArray(0)
    }
}
