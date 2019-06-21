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
      val bbHttpRequester = BbHttpRequester()
      val url = call.argument<String>("url")
      bbHttpRequester.execute(url) // retrieves a stream, converted to a string
      val str = bbHttpRequester.get()
      println("RESULT FROM BB: $str")
      result.success(str)
    } else if (call.method == "httpGet") {
      result.success("hardcoded output from android")
    } else {
      result.notImplemented()
    }
  }
}
