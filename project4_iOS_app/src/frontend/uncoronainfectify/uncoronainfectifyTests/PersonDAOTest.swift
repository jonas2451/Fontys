//
//  PersonDAOTest.swift
//  uncoronainfectifyTests
//
//  Created by Jonas Terschlüsen on 26.05.20.
//  Copyright © 2020 group9. All rights reserved.
//

import XCTest
@testable import G9UncoronaInfectify

class PersonDAOTest: XCTestCase {
    
    var globalLocationDAO: PersonDAO = PersonDAO.init(givenRootUrl: PersonDAO.getURL())
    
    func testFullCycle() -> Void {
        let personDAO = globalLocationDAO
        let phoneNumber1: String = "+495555555555"
        let healthState1: String = "susceptible"
        let person1 = testSave(personDAO: personDAO, phoneNumber: phoneNumber1, healthState: healthState1)
        
        //Check that the Person was saved correctly
        XCTAssert(person1.phonenumber == phoneNumber1)
        XCTAssert(person1.healthstate == healthState1)
        testGet(personDAO: personDAO, person1: person1)
        
        //Update the Person that was just saved
        let phoneNumber2: String = "+497777777777"
        let healthState2: String = "ill"
        if(person1.id == nil) { XCTFail(); return }
        testUpdate(personDAO: personDAO, id: person1.id!, phoneNumber: phoneNumber2, healthState: healthState2)
        
        
        //Check that the Person was updated correctly
        let location2 = Person(id: person1.id!, phonenumber: phoneNumber2, healthstate: healthState2)
        testGet(personDAO: personDAO, person1: location2)
        
        //Delete the Person just updated
        testDelete(personDAO: personDAO, id: person1.id!)
        
        //Check that it was actually deleted
        let location3 = personDAO.get(id: person1.id!) as! Person
        if location3.id != nil {
            XCTFail()
        }
    }
    
    
    /// This tests if the save method of the PersonDAO returns the object that was entered. To check if it actually saves the correct object in the database we also need the get method. This check is done in the testFullCycle method
    func testSave(personDAO: PersonDAO, phoneNumber: String, healthState: String) -> Person {
        let person: Person = personDAO.save(object: Person(phonenumber: phoneNumber, healthstate: healthState)) as! Person
        XCTAssert(person.phonenumber == phoneNumber)
        XCTAssert(person.healthstate == healthState)
        return person
    }
    
    
    func testUpdate(personDAO: PersonDAO, id: Int, phoneNumber: String, healthState: String) -> Void {
        XCTAssert(personDAO.update(object: Person(id: id, phonenumber: phoneNumber, healthstate: healthState)))
    }
    
    
    /// This method tests if the get method if the personDAO returns the expected object
    /// - Parameters:
    ///   - personDAO: the personDAO object to be used for the request
    ///   - person: the person object that should come back from the REST API when the personDAO makes a request for the id also specified in it
    func testGet(personDAO: PersonDAO, person1: Person) -> Void {
        if person1.id == nil {
            XCTFail()
        }else{
        let person: Person = personDAO.get(id: person1.id!) as! Person
        XCTAssert(person.id == person1.id)
        XCTAssert(person.phonenumber == person1.phonenumber)
        XCTAssert(person.healthstate == person1.healthstate)
        }
    }
    
    
    /// This method calls getAll on the DAO object and then prints all the results to the command line. This can then be compared to: "https://uncoronainfectify.eu-gb.mybluemix.net/persons/"
    /// - Parameter personDAO: The DAO object to be used for the request
    func testGetAll(personDAO: PersonDAO) -> Void {
        let locations = personDAO.getAll() as! [Person]
        print("testGetAll:")
        for person in locations {
            print("Person ID: ", person.id as Any)
        }
    }
    
    
    func testDelete(personDAO: PersonDAO, id: Int) -> Void {
        XCTAssert(personDAO.delete(id: id))
    }
}
