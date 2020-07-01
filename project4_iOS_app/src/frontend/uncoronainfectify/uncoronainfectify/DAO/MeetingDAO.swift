//
//  MeetingDAO.swift
//  uncoronainfectify
//
//  Created by Guus Damen and Louisa Hereth on 14/05/2020.
//  Copyright © 2020 group9. All rights reserved.
//

import Foundation

class MeetingDAO: DAO {

    let rootUrl: String     // This is the url without the entity name or an id in it.
    let entityType: String = "/meetings/"
    let baseUrl: String     //This is the url with the entity name but without an id
    
    ///
    /// opens a connection with URLsession and sends a POST request
    ///
    /// - Parameter object: object that needs to be saved into the database
    /// - Returns: a Model (in this case a meeting) that has been saved
    init(givenRootUrl: String) {
        rootUrl = givenRootUrl
        baseUrl = rootUrl + entityType
    }

    ///
    /// opens a connection with URLsession and sends a POST request to the URL
    /// . The result is retrieved as JSON, but
    /// afterwards decoded to a model.V
    ///
    /// - Parameter object: model to be saved (meeting in this case)
    /// - Returns: the model that is saved into the database
    func save(object: Model) -> Model {

        var meeting: Meeting = Meeting()
        let url = URL(string: baseUrl)
        let session = URLSession(configuration: .default)
        var request = URLRequest(url: url!)

        request.httpMethod = "POST"

        /// tells the URL that the content is encoded in JSON
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")

        do {

            let jsonData2 = try JSONEncoder().encode(object as! Meeting)

            session.uploadTask(with: request, from: jsonData2) { data, response, error in

                do {
                    if let data = data {
                        meeting = try JSONDecoder().decode(Meeting.self, from: data)
//                        print("The meeting you just saved: \(meeting)")
                    }
                } catch {
                    print("Json error:", error.localizedDescription)
                }
            }.resume()
        } catch {
            print("Json encoding error: ", error.localizedDescription)
        }

        let sleepDuration = 1
        print("this thread is sleepy for ", sleepDuration, " second")
        sleep(1)

        return meeting
    }

    ///
    /// currently not necessary in any use case
    ///
    /// - Parameter object: a Model object
    /// - Returns: true or false
    func update(object: Model) -> Bool {
        return true;
    }

    ///
    /// opens a connection with URLsession and sends a get request to the
    /// backend with a given id. The result is retrieved as JSON, but
    /// afterwards decoded to a model.
    ///
    /// - Parameter id: primary (key) id of a meeting
    /// - Returns: a Model that corresponds to the meeting in the database
    func get(id: Int) -> Model {

        let url = URL(string: baseUrl + String(id))
        var meeting: Meeting = Meeting()

        let session = URLSession(configuration: .default)

        // Open closure
        session.dataTask(with: url!) { data, response, error in

            // If error is returned, print error
            if let error = error {
                print("Error: \(error)")
            }

            // If data received print JSON
            if let data = data {
                do {
                    meeting = try JSONDecoder().decode(Meeting.self,
                            from: data)

                } catch {
                    print("Error: \(error.localizedDescription)")
                }
            }
        }.resume()

        // Sleep the thread for a second
        let sleepDuration = 1
        print("This thread is sleeping for", sleepDuration, "second(s)")
        sleep(UInt32(sleepDuration))

        return meeting
    }

    ///
    /// opens a connection with URLSession and sends a get request to the
    /// backend. The result is retrieved as JSON, but afterwards decoded to an
    /// array of models (Meetings in this case)
    ///
    /// - Returns: an array of models (Meetings in this case)
    func getAll() -> [Model] {

        let group = DispatchGroup()

        let url = URL(string: baseUrl)
        var meetings: [Meeting] = []

        let session = URLSession(configuration: .default)

        group.enter()
        // Open closure
        session.dataTask(with: url!) { data, response, error in

            // If error is returned, print error
            if let error = error {
                print("Error: \(error)")
            }

            // If data received print JSON
            if let data = data {

                do {
                    meetings = try JSONDecoder().decode([Meeting].self,
                            from: data)
                } catch {
                    print("Error: \(error.localizedDescription)")
                }
            }
            group.leave()
        }.resume()

        // Sleep the thread for a second
//        let sleepDuration = 1
//        print("This thread is sleeping for", sleepDuration, "second(s)")
//        sleep(UInt32(sleepDuration))
        group.wait(timeout: .distantFuture)
        return meetings
    }

    ///
    /// currently not necessary for any use case
    ///
    /// - Parameter id: meeting to be deleted id
    /// - Returns: true or false
    func delete(id: Int) -> Bool {
        return false
    }

    /// struct Meeting which can also be accessed from other classes
    public struct Meeting: Codable, Model {
        var id: Int?
        var p1Id: Int?
        var p2Id: Int?
        var locationId: Int?
        var date: String?
    }
}
