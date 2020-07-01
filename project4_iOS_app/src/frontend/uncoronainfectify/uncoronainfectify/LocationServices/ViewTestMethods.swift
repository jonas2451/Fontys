//
//  GPSView.swift
//  uncoronainfectify
//
//  Created by Guus Damen on 14/04/2020.
//  Copyright © 2020 group9. All rights reserved.
//

import SwiftUI
import MapKit

///
/// test class to test some certain methods on testing branch, not in the
/// final product
struct ViewTestMethods: View {

    let dao = MeetingDAO(givenRootUrl: MeetingDAO.getURL())

    var body: some View {

        VStack {
            Button(action: {
                let helper: MeetingHelper = MeetingHelper()
                for item in helper.getMeetingByPerson() {
                    print(item)
                }
            }) {
                Text("By userID")
            }

            Button(action: {
                let helper: MeetingHelper = MeetingHelper()
                print(helper.getMeetingLocationById(id: 1).longitude)
            }) {
                Text("Get location by id")
            }

            Button(action: {
                let helper: MeetingHelper = MeetingHelper()
                print(helper.getOtherPerson(meeting: helper
                        .getMeetingByPerson().first!).phonenumber!)
            }) {
                Text("Get other person")
            }

            Button(action: {
                let helper: MeetingHelper = MeetingHelper()
                for item in helper.getArrayOfMeetingListElements() {
                    print(item)
                }
            }) {
                Text("Get listElements")
            }
        }

    }
}

struct ViewTestMethods_Previews: PreviewProvider {
    static var previews: some View {
        ViewTestMethods()
    }
}
