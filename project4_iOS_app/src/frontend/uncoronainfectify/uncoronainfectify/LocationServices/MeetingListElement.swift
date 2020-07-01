//
//  MeetingListElement.swift
//  uncoronainfectify
//
//  Created by Guus Damen & Louisa Hereth on 28/05/2020.
//  Copyright © 2020 group9. All rights reserved.
//

import SwiftUI
import CoreLocation

///
/// a struct that corresponds to all the information needed to make up for a
/// display for the user. These elements are used in the MeetingList
struct MeetingListElement: Hashable {
    var id: Int
    var phoneNumber: String //phonennumber of the other person
    var healthStatus: String // current healthStatus of the person you met
    var date: String
    var longitude: Double
    var latitude: Double
}

