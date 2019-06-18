import 'dart:async';

import 'package:flutter/services.dart';

class GdPlugin {
  static const MethodChannel _channel = const MethodChannel('gd_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> bbHttpGet(String url) async {
    final String result =
        await _channel.invokeMethod('bbHttpGet', {'url': url});
    return result;
  }

  static Future<String> httpGet(String url) async {
    final String result = await _channel.invokeMethod('httpGet', {'url': url});
    return result;
  }
}
