import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:gd_plugin/gd_plugin.dart';

void main() {
  const MethodChannel channel = MethodChannel('gd_plugin');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await GdPlugin.platformVersion, '42');
  });
}
