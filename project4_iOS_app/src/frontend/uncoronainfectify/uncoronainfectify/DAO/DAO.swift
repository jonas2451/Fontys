//
// Created by Jonas Terschlüsen on 07.05.20.
// Copyright (c) 2020 group9. All rights reserved.
//

import Foundation

protocol DAO {


    /// - Parameter object: Model to be saved as persistent data in the backend. This does not contain an ID because the backend assigns this id when saving the object.
    /// - Returns: The Model that has been saved. This does contain the ID assigned by the backend
    func save(object: Model) -> Model

    
    /// - Parameter object: Model to be updated.
    /// - Returns: True on success.
    func update(object: Model) -> Bool

    
    /// - Parameter id: ID of the Model to get.
    /// - Returns:  The Model that has the ID provided. If there is no object it returns an empty object.
    func get(id: Int) -> Model

    
    /// - Returns: All Models saved of one type.
    func getAll() -> [Model]


    /// - Parameter id: ID of the Object to be deleted.
    /// - Returns: True on success.
    func delete(id: Int) -> Bool
}

extension DAO {
    static func getURL() -> String {
        return "https://uncoronainfectifyjonas.eu-gb.mybluemix.net"
        // "https://uncoronainfectify.eu-gb.mybluemix.net"
        // "https://uncoronainfectifyjonas.eu-gb.mybluemix.net"
    }
}
