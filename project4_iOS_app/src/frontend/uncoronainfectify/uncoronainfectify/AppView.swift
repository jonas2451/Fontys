//
//  AppView.swift
//  uncoronainfectify
//
//  Created by Jonas Terschlüsen on 16.04.20.
//  Copyright © 2020 group9. All rights reserved.
//

import SwiftUI

struct AppView: View {

    // the view on which the app starts
    @State var selectedView: Int = 1
    // variable is declared true if account credentials are saved on the phone and account exists online
    @State var signInSuccess: Bool = {
        
        //UserDefaults.standard.set(nil, forKey: "Id") //Uncomment this line to logout!
        
        UIApplication.shared.applicationIconBadgeNumber = 0
        
        var key = UserDefaults.standard.integer(forKey: "Id")

        if (key != 0) {
            var dao = PersonDAO(givenRootUrl: PersonDAO.getURL())
            var person = dao.get(id: key) as! Person

            if (person.id != nil) {
                return true
            }
//            UserDefaults.standard.removeObject(forKey: "Id") // Would remove the ID from your phone, when opening the without an internet connection
            return false
        } else {
            return false
        }
    }()

    var body: some View {

        VStack {
            if signInSuccess == true {
                TabView(selection: $selectedView) {
                    GPSView()
                            .tabItem {
                                Image(systemName: "map")
                                Text("Map")
                            }.tag(0)

                    ViewShowQR()
                            .tabItem {
                                Image(systemName: "qrcode")
                                Text("Your QR Code")
                            }.tag(1)

                    viewHealthStatus()
                            .tabItem {
                                Image(systemName: "waveform.path.ecg")
                                Text("Health Status")
                            }.tag(2)

                    viewScanQR()
                            .tabItem {
                                Image(systemName: "qrcode")
                                Text("Scan QR Codes")
                            }.tag(3)

                    MeetingList()
                            .tabItem {
                                Image(systemName: "calendar")
                                Text("Meeting list")
                            }.tag(4)
                }
            } else {
                loginView(signInSuccess: $signInSuccess)
            }
        }
    }
}
