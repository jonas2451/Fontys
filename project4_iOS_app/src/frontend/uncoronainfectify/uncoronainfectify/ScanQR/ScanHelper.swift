//
//  ScanHelper.swift
//  uncoronainfectify
//
//  Created by Sven Rediske on 28.05.20.
//  Copyright © 2020 group9. All rights reserved.
//

import Foundation
import SwiftUI
import MapKit

class ScanHelper : NSObject, CLLocationManagerDelegate{
    
    let locationManager: CLLocationManager = CLLocationManager.init()
    
    override init() {
        super.init()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
    }
    
    /// This method uses a meeting and a location DAO to save a new location and a new meeting with that location id
    /// - Parameters:
    ///   - longitude: the longitude to be put into the location
    ///   - latitude: the latitude to be put into the location
    ///   - ownUserId: the first user id to be put into the meeting
    ///   - otherUserId: the second user id to be put into the meeting
    ///   - date: the date in ISO8601 format that is to be put into the meeting
    /// - Returns: true on success and false if the meeting DAO did not return a valid object, hence the meeting was not correctly saved
    static func saveMeetingCombination(longitude: Double, latitude: Double, ownUserId: Int, otherUserId: Int, date: String) -> Bool {
        
        //create the needed DAO's
        let locationDAO: LocationDAO = LocationDAO.init(givenRootUrl: LocationDAO.getURL())
        let meetingDAO: MeetingDAO = MeetingDAO.init(givenRootUrl: MeetingDAO.getURL())
        
        //create and save the location
        var location: LocationDAO.Location = LocationDAO.Location(latitude: latitude, longitude: longitude)
        location = locationDAO.save(object: location) as! LocationDAO.Location
        
        //create and save the meeting with the location ID
        if(location.id == nil) {print("Error!"); return false}
        var meeting: MeetingDAO.Meeting = MeetingDAO.Meeting(p1Id: ownUserId, p2Id: otherUserId, locationId: location.id!, date: date)
        meeting = meetingDAO.save(object: meeting) as! MeetingDAO.Meeting
        
        //return true if the save method returns a valid meeting object
        return meeting.id != 0 && meeting.id != nil
    }
    
    
    /// This method gathers together all the information to create a meeting together with a location
    /// - Parameter otherUserIdAsString: This is the string extracted from the qr code. There should be an integer extractable from it
    /// - Returns: "Success" if the meeting was saved in the database and "Failure" if it was not.
    func doStuff(otherUserIdAsString: String) -> String{
        
        let coordinates = self.getCurrentLocation().coordinate
        let latitude: Double = coordinates.latitude
        let longitude: Double = coordinates.longitude
        let ownUserId: Int = UserDefaults.standard.integer(forKey: "Id")
        let date: String = ISO8601DateFormatter.init().string(from: Date.init())    //creates a String with the currect Date and Time in ISO8601 Format
        if let otherUserId: Int = Int(otherUserIdAsString){ //only if there is a extractable integer in the qr code i try to save the meeting
        
//        print("----------------------------------------------")
//        print("otherUserId",otherUserId)
//        print("ownUserId",ownUserId)
//        print("date",date)
//        print("latitude", latitude)
//        print("longitude", longitude)
//        print("----------------------------------------------")
        
        let boolean = ScanHelper.saveMeetingCombination(longitude: longitude, latitude: latitude, ownUserId: ownUserId, otherUserId: otherUserId, date: date)
        return boolean ? "Success" : "Failure"
        }else {
            return "Failure"
        }
    }
}

extension ScanHelper{
    
    func getCurrentLocation() -> CLLocation {
        
        locationManager.startUpdatingLocation()

        return locationManager.location != nil ? locationManager.location! : CLLocation()
    }
}
