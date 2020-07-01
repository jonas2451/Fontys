//
//  Model.swift
//  uncoronainfectify
//
//  Created by Sven Rediske on 12.05.20.
//  Copyright © 2020 group9. All rights reserved.
//

import Foundation
// This is the interface for out 3 entities location, meeting and person.
// There shall be 1 class per entity implementing this interface
protocol Model: Codable {}

extension Encodable {
    func toJSONData() -> Data? { try? JSONEncoder().encode(self) }
}

