//
// Created by Jonas Terschlüsen on 07.05.20.
// Copyright (c) 2020 group9. All rights reserved.
//

import Foundation

public struct Person: Codable, Model {
    public var id : Int?
    public var phonenumber : String?
    public var healthstate : String?
}