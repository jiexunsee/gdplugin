#import "GdPlugin.h"
#import <gd_plugin/gd_plugin-Swift.h>

@implementation GdPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftGdPlugin registerWithRegistrar:registrar];
}
@end
