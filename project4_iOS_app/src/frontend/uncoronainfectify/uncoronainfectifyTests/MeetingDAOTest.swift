//
//  MeetingDAOTest.swift
//  uncoronainfectifyTests
//
//  Created by Louisa Hereth on 26.05.20.
//  Copyright © 2020 group9. All rights reserved.
//

import XCTest
@testable import G9UncoronaInfectify

class MeetingDAOTest: XCTestCase {
    
    //method to test the interaction between save and get --> makes sure that a meeting that is saved can be retrieved from the database
    func testSaveAndGetCombined() -> Void {
        let meetingDAO = MeetingDAO.init(givenRootUrl: MeetingDAO.getURL())
        let person1: Int = 64
        let person2: Int = 65
        let locationId: Int = 92
        let date: String = "2020-04-16T14:03:29.000Z"
        let meeting1 = testSave(meetingDAO: meetingDAO, person1: person1, person2: person2, locationId: locationId, date: date)
        
        testGet(meetingDAO: meetingDAO, meeting: meeting1)
    }
    
    //method that is used in testSaveAndGetCombined(); saves a meeting with the variables that are given to it and returns the meeting (plus it's new id)
    func testSave(meetingDAO: MeetingDAO, person1: Int, person2: Int, locationId: Int, date: String) -> MeetingDAO.Meeting {
        
        let meeting: MeetingDAO.Meeting = meetingDAO.save(object: MeetingDAO.Meeting(p1Id: person1, p2Id: person2, locationId: locationId, date: date)) as! MeetingDAO.Meeting
        XCTAssert(meeting.p1Id == person1)
        XCTAssert(meeting.p2Id == person2)
        XCTAssert(meeting.locationId == locationId)
        XCTAssert(meeting.date == date)
        
        return meeting
    }
    
    //method that is used in testSaveAndGetCombined(); takes a meetingDAO and a meeting to retrieve it from the database
    func testGet(meetingDAO: MeetingDAO, meeting: MeetingDAO.Meeting) {
        if(meeting.id == nil) { XCTFail(); return}  //We have to check that the meeting id is not nil before we unwrap it so we do not get fatal errors
        let retrievedMeeting: MeetingDAO.Meeting = meetingDAO.get(id: meeting.id!) as! MeetingDAO.Meeting
        
        XCTAssert(meeting.id == retrievedMeeting.id)
        XCTAssert(meeting.p1Id == retrievedMeeting.p1Id)
        XCTAssert(meeting.p2Id == retrievedMeeting.p2Id)
        XCTAssert(meeting.locationId == retrievedMeeting.locationId)
        XCTAssert(meeting.date == retrievedMeeting.date)
    }
    
    //method to test if a hardcoded meeting is in the database (meeting should not be deleted from the database for the test to run properly)
    func testGet(){
        let dao = MeetingDAO.init(givenRootUrl: MeetingDAO.getURL())
        let meeting: MeetingDAO.Meeting = MeetingDAO.Meeting(id: 83, p1Id: 64, p2Id: 65, locationId: 92, date: "2020-04-16T14:03:29.000Z")
        let retrievedMeeting: MeetingDAO.Meeting = dao.get(id: meeting.id!) as! MeetingDAO.Meeting
        XCTAssert(meeting.id == retrievedMeeting.id)
        XCTAssert(meeting.p1Id == retrievedMeeting.p1Id)
        XCTAssert(meeting.p2Id == retrievedMeeting.p2Id)
        XCTAssert(meeting.locationId == retrievedMeeting.locationId)
        XCTAssert(meeting.date == retrievedMeeting.date)
    }
    
    //just for comparing it to the meetings when we call it from the website --> same format; really testing getAll would require a whole scenario
    func testGetAll() {
        let dao = MeetingDAO.init(givenRootUrl: MeetingDAO.getURL())
        let meetings: [MeetingDAO.Meeting] = dao.getAll() as! [MeetingDAO.Meeting];
        
        var counter: Int = 1
        for meeting in meetings {
            print("meeting \(counter): {id:\(meeting.id!), p1Id:\(meeting.p1Id!), p2Id:\(meeting.p2Id!), locationId\(meeting.locationId!), date:\(meeting.date!)}")
            counter += 1
        }
    }
}
