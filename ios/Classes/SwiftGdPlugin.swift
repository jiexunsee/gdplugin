import Flutter
import UIKit

public class SwiftGdPlugin: NSObject, FlutterPlugin, URLSessionDelegate {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "gd_plugin", binaryMessenger: registrar.messenger())
    let instance = SwiftGdPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }
    
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        if (call.method == "getPlatformVersion") {
            result("iOS " + UIDevice.current.systemVersion)
        } else if (call.method == "bbHttpGet") {
            print("inside ios method call")
            let arguments = call.arguments as! NSDictionary // INFO: get arguments
            let url = arguments["url"] as! String
            
            requestData(url: url){output in
                result(output)
            }
        } else if (call.method == "httpGet") {
            print("inside ios method call")
            result("hardcoded output from ios")
        }
    }
    
    // Requests the data from a specific URL. This can trigger authentication dialogs
    func requestData(url : String, completionHandler:@escaping (String) -> ()) {
        // Open the request using NSURLConnection or NSURLSession
        let nsUrl = URL(string: url)
        let urlRequest = URLRequest(url: nsUrl!)
        let session = URLSession.init(configuration: URLSessionConfiguration.default, delegate: self, delegateQueue: nil)
        //print(session.delegate)
        let task = session.dataTask(with: urlRequest, completionHandler: { (data, response, error) in
            print(data as Any)
            print("PRINTED DATA")
            if data != nil {
                let string = String(bytes: data!, encoding: .utf8)
                completionHandler(string!)
            } else {
                completionHandler("{\"person\": {\"displayName\": \"IT FAILED\"}}")
            }
        })
        task.resume()
    }
    
    public func urlSession(_ session: URLSession, didReceive challenge: URLAuthenticationChallenge, completionHandler: @escaping (URLSession.AuthChallengeDisposition, URLCredential?) -> Swift.Void) {
        
        print("connection will send request for authentication challenge")
        
        let authMethod = challenge.protectionSpace.authenticationMethod
        print("Auth method in use: \(authMethod)")
        
        if authMethod == NSURLAuthenticationMethodHTTPBasic || authMethod == NSURLAuthenticationMethodHTTPDigest || authMethod == NSURLAuthenticationMethodNTLM || authMethod == NSURLAuthenticationMethodNegotiate {
        } else if authMethod == NSURLAuthenticationMethodClientCertificate {
            print("INSIDE ClientCertificate CHALLENGE")
            // Do nothing - GD will automatically supply an appropriate client certificate
        } else if authMethod == NSURLAuthenticationMethodServerTrust {
            print("INSIDE ServerTrust 1 CHALLENGE")
            if challenge.protectionSpace.authenticationMethod == NSURLAuthenticationMethodServerTrust {
                print("INSIDE ServerTrust 2 CHALLENGE")
                challenge.sender?.use(URLCredential.init(trust: challenge.protectionSpace.serverTrust!), for: challenge)
            }
            challenge.sender?.continueWithoutCredential(for: challenge)
        }
    }
}
