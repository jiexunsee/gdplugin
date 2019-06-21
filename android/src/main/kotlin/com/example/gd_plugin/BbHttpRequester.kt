package com.example.gd_plugin

import android.os.AsyncTask

import com.good.gd.apache.http.client.methods.HttpGet
import com.good.gd.net.GDHttpClient

import java.io.IOException

import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream


class BbHttpRequester : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg url: String): String {
        println("DOING HTTP REQUEST")
        val httpclient = GDHttpClient()
//        httpclient.disableHostVerification();
//        httpclient.disablePeerVerification();
        val request = HttpGet(url[0])

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
            return buf.toString("UTF-8")

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }
}
