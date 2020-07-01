//
//  MeetingList.swift
//  uncoronainfectify
//
//  Created by Guus Damen on 28/05/2020.
//  Copyright © 2020 group9. All rights reserved.
//

import SwiftUI

/**
 * the view that displays a list of all the meetings a user has had;
 * uses the meetinghelper to retrieve all the information needed
 */
struct MeetingList: View {
    let meetingHelper: MeetingHelper = MeetingHelper()
    
    var body: some View {
        NavigationView {
            //to display all the meetings a user has -> one in each line
            List(meetingHelper.getArrayOfMeetingListElements(), id: \.id) { listElement in
                 // to make sure that you get redirected to the singleMeetingView of the meeting the user clicked on
                NavigationLink(destination: SingleMeetingView(meetingListElement: listElement)) {
                    MeetingRow(listElement: listElement)
                }
                .navigationBarTitle("Your meetings")
            }
        }
    }
}
