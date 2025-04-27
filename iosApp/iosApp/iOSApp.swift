import SwiftUI

@main
struct iOSApp: App {
    init(){
    GADMobileAds.shared.start(completionHandler: nil)
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
