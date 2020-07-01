//
//  LocationDAOTest.swift
//  uncoronainfectifyTests
//
//  Created by Sven Rediske on 15.05.20.
//  Copyright © 2020 group9. All rights reserved.
//

import XCTest
@testable import G9UncoronaInfectify

class LocationDAOTest: XCTestCase {
    
    var globalLocationDAO: LocationDAO = LocationDAO.init(givenRootUrl: LocationDAO.getURL())
    //TODO What happens if the url is wrong or the connection could not be established
    
    /// This method uses all other test methods to test all the functionality of the LocationDAO in a way that no trash is let behind in the database
    func testFullCycle() -> Void {
        let locationDAO = globalLocationDAO
        let latitude1: Double = 2.2
        let longitude1: Double = 3.3
        let location1 = testSave(locationDAO: locationDAO, latitude: latitude1, longitude: longitude1)
        
        //Check that the Location was saved correctly
        XCTAssert(location1.latitude == latitude1)
        XCTAssert(location1.longitude == longitude1)
        testGet(locationDAO: locationDAO, locationPara: location1)
        
        //Update the Location that was just saved
        let latitude2: Double = 5.42
        let longitude2: Double = 4.25
        if(location1.id == nil) { XCTFail(); return }
        testUpdate(locationDAO: locationDAO, id: location1.id!, latitude: latitude2, longitude: longitude2)
        
        
        //Check that the Location was updated correctly
        let location2 = LocationDAO.Location(id: location1.id!, latitude: latitude2, longitude: longitude2)
        testGet(locationDAO: locationDAO, locationPara: location2)
        
        //Delete the Location just updated
        testDelete(locationDAO: locationDAO, id: location1.id!)
        
        //Check that it was actually deleted
        let location3 = locationDAO.get(id: location1.id!) as! LocationDAO.Location
        if location3.id != nil {
            XCTFail()
        }
    }
    
    
    /// This tests if the save method of the LocationDAO returns the object that was entered. To check if it actually saves the correct object in the database we also need the get method. This check is done in the testFullCycle method
    func testSave(locationDAO: LocationDAO, latitude: Double, longitude: Double) -> LocationDAO.Location {
        let location: LocationDAO.Location = locationDAO.save(object: LocationDAO.Location(latitude: latitude, longitude: longitude)) as! LocationDAO.Location
        XCTAssert(location.latitude == latitude)
        XCTAssert(location.longitude == longitude)
        return location
    }
    
    
    func testUpdate(locationDAO: LocationDAO, id: Int, latitude: Double, longitude: Double) -> Void {
        XCTAssert(locationDAO.update(object: LocationDAO.Location(id: id, latitude: latitude, longitude: longitude)))
    }
    
    
    /// This method tests if the get method if the locationDAO returns the expected object
    /// - Parameters:
    ///   - locationDAO: the locationDAO object to be used for the request
    ///   - location: the location object that should come back from the REST API when the locationDAO makes a request for the id also specified in it
    func testGet(locationDAO: LocationDAO, locationPara: LocationDAO.Location) -> Void {
        if locationPara.id == nil {
            XCTFail()
        }else{
        let location: LocationDAO.Location = locationDAO.get(id: locationPara.id!) as! LocationDAO.Location
        XCTAssert(location.id == locationPara.id)
        XCTAssert(location.latitude == locationPara.latitude)
        XCTAssert(location.longitude == locationPara.longitude)
        }
    }
    
    
    /// This method calls getAll on the DAO object and then prints all the results to the command line. This can then be compared to: "https://uncoronainfectify.eu-gb.mybluemix.net/locations/"
    /// - Parameter locationDAO: The DAO object to be used for the request
    func testGetAll(locationDAO: LocationDAO) -> Void {
        let locations = locationDAO.getAll() as! [LocationDAO.Location]
        print("testGetAll:")
        for location in locations {
            print("Location ID: ", location.id as Any)
        }
    }
    
    
    func testDelete(locationDAO: LocationDAO, id: Int) -> Void {
        XCTAssert(locationDAO.delete(id: id))
    }
    
    // Not to be used anymore. Was used for development.
    //func testSave() -> Void { testSave(locationDAO: globalLocationDAO,latitude: 1.1,longitude: 1.1) }
    //func testUpdate() -> Void { testUpdate(locationDAO: globalLocationDAO, id: 1, latitude: 42, longitude: 42) }
    //func testGet() -> Void { testGet(locationDAO: globalLocationDAO, location: LocationDAO.Location(id: 1, latitude: 42, longitude: 42)) }
    //func testGetAll() -> Void { testGetAll(locationDAO: globalLocationDAO) }
    //func testDelete() -> Void { testDelete(locationDAO: globalLocationDAO, id: 55) }
    
    func testExampleFromLecturers() throws { //this test is from the github provided by lecturers
        // 1. Define an expectation
                let expect = expectation(description: "Request and run callback closure")
                
                // 2. Define session and url
                let defaultSession = URLSession(configuration: .default)
        //        let url = URL(string: "http://[::1]:3000/locations/1?")
                let url = URL(string: "https://uncoronainfectify.eu-gb.mybluemix.net/locations/1")
                
                // 3. Define the task
                // Forced unwrap of url - we set it ourselves
                let task = defaultSession.dataTask(with: url!) { data, response, error in
                    
                    // Check data
                    if let data = data  {
                        // Decode JSON
                        let decoder = JSONDecoder()
                        do {
                          let location = try decoder.decode(LocationDAO.Location.self, from: data)
                          XCTAssert(location.id == 1, "Wrong location id")
                          XCTAssert(location.longitude == 42, "Wrong location longitude")
                          XCTAssert(location.latitude == 42, "Wrong location latitude")
                        } catch {
                          XCTFail(error.localizedDescription)
                        }
                    } else {
                        XCTFail("No data")
                    }
                    // 4. Don't forget to fulfill the expectation in the async callback
                    expect.fulfill()
                }
                
                // 5. Run the task
                task.resume()
                
                // 6. Wait for the expectation to be fulfilled
                waitForExpectations(timeout: 4) {
                    error in if let error = error {
                         XCTFail("Error \(error)")
                    }
                 }
    }
}
