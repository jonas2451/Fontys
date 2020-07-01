//
//  MeetingRow.swift
//  uncoronainfectify
//
//  Created by Guus Damen and Louisa Hereth on 28/05/2020.
//  Copyright © 2020 group9. All rights reserved.
//

import SwiftUI

/// the view that defines how a single row of the view MeetingList is displayed
/// (design and information)
struct MeetingRow: View {
    var listElement: MeetingListElement

    var body: some View {
        VStack {
            HStack {
                Text("Meeting on")
                Spacer()
                Text("\(getDate())")
            }
            HStack {
                Text("With")
                Spacer()
                if listElement.healthStatus == "ill" {
                    Text("\(listElement.phoneNumber)").foregroundColor(.red)
                } else {
                    Text("\(listElement.phoneNumber)")
                }
            }
        }
        
    }

    ///
    /// function to format the data
    ///
    /// - Returns: a new date string
    func getDate() -> String {
        let date = self.listElement.date
        if let range = date.range(of: #"([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))"#, options: .regularExpression) {
            return String(date[range])
        }
        return "no valid date saved"
    }
}

