package com.example.gd_plugin

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar



class GdPlugin: MethodCallHandler {
  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "gd_plugin")
      channel.setMethodCallHandler(GdPlugin())
    }
  }

  override fun onMethodCall(call: MethodCall, result: Result) {


    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if (call.method == "bbHttpGet") {

      val bbHttpRequester = BbHttpRequester(callback = object : OnTaskCompletion<String> {
        override fun onSuccess(output: String) {
          println("DEBUG: got string from bb (in plugin): $output")
          result.success(output)
        }
        override fun onFailure(e: Exception) {
          println("DEBUG: onFailure happened")
        }
      })
      val url = call.argument<String>("url")
      bbHttpRequester.execute(url) // retrieves a stream, converted to a string
//      val str = bbHttpRequester.get()


    } else if (call.method == "bbHttpGetBytes") {
      val bbHttpBytesRequester = BbHttpBytesRequester(callback = object : OnTaskCompletion<ByteArray> {
        override fun onSuccess(output: ByteArray) {
          println("DEBUG: got byte stream from bb (in plugin): $output")
          result.success(output)
        }
        override fun onFailure(e: Exception) {
          println("DEBUG: onFailure happened")
        }
      })
      val url = call.argument<String>("url")
      bbHttpBytesRequester.execute(url) // retrieves a stream, converted to bytes
//      val str = bbHttpBytesRequester.get()
    }


    else if (call.method == "httpGet") {
      result.success("hardcoded output from android")
    } else {
      result.notImplemented()
    }
  }
}
