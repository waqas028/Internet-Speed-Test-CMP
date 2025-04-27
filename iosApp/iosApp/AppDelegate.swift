//
//  AppDelegate.swift
//  iosApp
//
//  Created by mac on 27/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import GoogleMobileAds
class AppDelegate: UIResponder, UIApplicationDelegate{
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        GADMobileAds.shared.start(completionHandler: nil)
    }
}
