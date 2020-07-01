//
//  SingleMeetingView.swift
//  uncoronainfectify
//
//  Created by Guus Damen and Louisa Hereth on 28/05/2020.
//  Copyright © 2020 group9. All rights reserved.
//

import SwiftUI
import MapKit
import Foundation

/// the view that displays a single meeting with all its details
struct SingleMeetingView: View {
    var meetingListElement: MeetingListElement
    
    var body: some View {

        VStack {
            MapView(coordinate: CLLocationCoordinate2D(latitude: self.meetingListElement.latitude, longitude:
            self.meetingListElement.longitude) )
                    .edgesIgnoringSafeArea(.top)
                    .frame(height: 500)

            VStack(alignment: .leading) {

                Text("Meeting")
                        .font(.title)

                HStack {
                    Text("Coordinates: ")
                    Spacer()
                    VStack {
                        Text("latitude: \(meetingListElement.latitude)")
                        Text("longitude: \(meetingListElement.longitude)")
                    }
                    //Text("X: \(meetingListElement.longitude), Y: \(meetingListElement.latitude)")
                }

                HStack {
                    Text("Met person (p nr): ")
                    Spacer()
                    Text("\(meetingListElement.phoneNumber)")
                }
                
                HStack {
                    Text("current health status: ")
                    Spacer()
                    Text("\(meetingListElement.healthStatus)")
                }

                HStack {
                    Text("Date: ")
                    Spacer()
                    Text("\(getDate())")
                }
                Spacer()
            }
                    .padding()

        }
        .navigationBarTitle(Text("Meeting: \(meetingListElement.id)"), 
                displayMode:.inline)
    }
    
    func getDate() -> String {
        let date = self.meetingListElement.date
        if let range = date.range(of: #"([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))"#, options: .regularExpression) {
            return String(date[range])
        }
        return "no valid date saved"
    }
}
