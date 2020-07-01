//
//  ScanHelperTest.swift
//  uncoronainfectifyTests
//
//  Created by Sven Rediske on 28.05.20.
//  Copyright © 2020 group9. All rights reserved.
//

import XCTest
@testable import G9UncoronaInfectify

class ScanHelperTest: XCTestCase {

    func testSaveMeetingCombination() throws {
        XCTAssert(ScanHelper.saveMeetingCombination(longitude: 4.4, latitude: 4.4, ownUserId: 51, otherUserId: 50, date: "2020-04-16T14:03:29.000Z"))
    }
    
    //Just for me to learn it
    func testISO8601() -> Void {
        let formatter: ISO8601DateFormatter = ISO8601DateFormatter.init()
        
//        let date2: Date = Date(
        let date: Date = formatter.date(from: "2020-04-16T14:03:29.000Z") ?? Date.init()
        
        print("------------------------------------------")
        print(formatter.string(from: date))
        print("------------------------------------------")
    }

}
