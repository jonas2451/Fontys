//
// Created by Guus Damen & Louisa Hereth on 29/05/2020.
// Copyright (c) 2020 group9. All rights reserved.
//

import Foundation

///
/// class that helps to retrieve all the information (from the database) that
/// is needed for an array of MeetingListElements; the array of
/// MeetingListElements is needed to display the list of meetings
/// a user had in the MeetingList view
class MeetingHelper {

    var meetingDAO: MeetingDAO = MeetingDAO(givenRootUrl: "https://uncoronainfectify.eu-gb.mybluemix.net")
    var locationDAO: LocationDAO = LocationDAO(givenRootUrl: "https://uncoronainfectify.eu-gb.mybluemix.net")
    var personDAO: PersonDAO = PersonDAO(givenRootUrl: "https://uncoronainfectify.eu-gb.mybluemix.net")

    ///
    /// gets all the meetings that exist in the database and filters it to
    /// the ID set when launching the application
    ///
    /// - Returns: array of meetings based on an user id
    func getMeetingByPerson() -> [MeetingDAO.Meeting] {

        let id = UserDefaults.standard.integer(forKey: "Id")
        let meetings: [MeetingDAO.Meeting] = meetingDAO.getAll() as!
                [MeetingDAO.Meeting]

        var meetingResult : [MeetingDAO.Meeting] = []

        for item in meetings {
            // Meeting can consist of two persons, hence two conditions
            if (item.p1Id == id || item.p2Id == id) {
                meetingResult.append(item)
            }
        }

        return meetingResult
    }

    ///
    /// gets the location of a meeting by id
    ///
    /// - Parameter id: location id
    /// - Returns: a single location object
    func getMeetingLocationById(id: Int) -> LocationDAO.Location {
       locationDAO.get(id: id) as! LocationDAO.Location
    }

    ///
    /// get the other person of a meeting based on the ID set while launching
    ///
    /// - Parameter meeting: the meeting of which the other person is needed
    /// - Returns: a single person object
    func getOtherPerson(meeting: MeetingDAO.Meeting) -> Person {
        let id = UserDefaults.standard.integer(forKey: "Id")

        if(meeting.p1Id == id) {
            return personDAO.get(id: meeting.p2Id!) as! Person
        }
        return personDAO.get(id: meeting.p1Id!) as! Person
    }

    ///
    /// converts all variables into a meetingListElement struct;
    /// it is needed to fill the list in MeetingList
    ///
    /// - Returns: a list of meetingListElements
    func getArrayOfMeetingListElements() -> [MeetingListElement]{

        var listElements : [MeetingListElement] = []
        let meetings : [MeetingDAO.Meeting] = getMeetingByPerson()


        for item in meetings {
            let otherPerson = getOtherPerson(meeting: item)
            let phonenumberOtherPerson : String = otherPerson.phonenumber!
            let healthStatusOtherPerson : String = otherPerson.healthstate!
            let longitude = getMeetingLocationById(id: item.locationId!)
                    .longitude!
            let latitude = getMeetingLocationById(id: item.locationId!)
                    .latitude!

            let listElement : MeetingListElement = MeetingListElement(id:
                item.id!, phoneNumber: phonenumberOtherPerson, healthStatus: healthStatusOtherPerson, date: item.date!,
                    longitude: longitude, latitude: latitude )

            listElements.append(listElement)
        }

        return listElements
    }
}
