//
// Created by Jonas Terschlüsen on 07.05.20.
// Copyright (c) 2020 group9. All rights reserved.
//

import Foundation

class PersonDAO: DAO {

    let rootUrl: String     //This is the url without the entity name or an id in it.
    let entityType: String = "/persons/"
    let url: String     //This is the url with the entity name but without an id
    
    init(givenRootUrl: String) {
        rootUrl = givenRootUrl
        url = rootUrl + entityType
    }
    
    private func getConnection() -> (URLSession, URL) {
        return (URLSession.shared, URL(string: url)!)
    }

    func save(object: Model) -> Model {
        let (session, url) = getConnection()

        var person = Person()

        var request = URLRequest(url: url)

        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "accept")
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpBody = object.toJSONData()

//        print(String(decoding: request.httpBody!, as: UTF8.self))

        let task: URLSessionTask = session.dataTask(with: request) { data, response, error in

            // Print Error
            if let error = error {
                print("error: \(error)")
            }

            if let data = data {
                // Print JSON
                do {
//                    let responseJSON = try? JSONSerialization.jsonObject(with: data, options: [])
//                    if let responseJSON = responseJSON as? [String: Any] {
////                        debugPrint("<PersonDAO.save>", responseJSON)
//                    }
                    person = try JSONDecoder().decode(Person.self, from: data)
                } catch {
                    print("JSON error: \(error.localizedDescription)")
                }
            }
        }
        task.resume()

        sleep(1)
        if (person.id != nil) {
            return person
        }
        return person
    }

    func update(object: Model) -> Bool {

        let group = DispatchGroup()

        var returnBoolean: Bool = false
        let person = object as! Person
        let actualUrl: String = url + String(person.id!)
        let url = URL(string: actualUrl)
        let defaultSession = URLSession(configuration: .default)
        var request = URLRequest(url: url!)
        request.httpMethod = "PATCH"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        do{
            group.enter()
            let jsonData = try JSONEncoder().encode(object as! Person)
            defaultSession.uploadTask(with: request, from: jsonData) {data, response, error in
                
                if let error = error  {print ("HTTP-Error in \"personDAO.update\": \(error)")}
                if let httpResponse = response as? HTTPURLResponse {
                    if httpResponse.statusCode == 204 {returnBoolean = true}
                }
                group.leave()
            }.resume()
        }
        catch{
            print("JSON-Encoding-Error in \"PersonDAO.update\":", error.localizedDescription)
        }
        sleep(1)
        return returnBoolean
    }

    func get(id: Int) -> Model {

        let group = DispatchGroup()

        var personGetter:Person = Person()
        let defaultSession = URLSession(configuration: .default)
        let actualUrl: String = url + String(id)
        let url = URL(string: actualUrl)

        group.enter()
        defaultSession.dataTask(with: url!)  { data, response, error in

            if let error = error  {print ("HTTP-Error in \"PersonDAO.get\": \(error)")}
            if let data = data  {
                do {
//                    debugPrint(String(data: data, encoding: .utf8))
                    personGetter = try JSONDecoder().decode(Person.self, from: data)
                }catch{
                    print("JSON-Decoding-Error in \"PersonDAO.get\":", error.localizedDescription)
                }
            }
            group.leave()
        }.resume()
                
//        sleep(1)

        group.wait(timeout: .distantFuture)

        return personGetter
    }

    func get(phoneNumber: String) -> Model {
        var person = Person()
        let (session, url) = getConnection()

        let number = phoneNumber

        let urlWithPathAsString = url.appendingPathComponent("?filter[where][phonenumber]=%2B" + number.dropFirst()).absoluteString.removingPercentEncoding

        let urlWithPath = URL(string: urlWithPathAsString!)


        // Forced unwrap of url - we set it ourselves
        let task: URLSessionTask = session.dataTask(with: urlWithPath!) { data, response, error in

            // Print Error
            if let error = error {
                print("error: \(error)")
            }

            if let data = data {
                // Print JSON
                do {
                    debugPrint(String(data: data, encoding: .utf8)!)
                    var persons = [Person()]
                    persons = try JSONDecoder().decode([Person].self, from: data)
                    if (persons.count > 0) {
                        person = persons[0]
                    } else {
                        return
                    }

                } catch {
                    print("JSON error: \(error.localizedDescription)")
                }
            }
        }

        task.resume()

        sleep(2)

        if (person.id != nil) {
            return person
        }
        return person
    }

    func getAll() -> [Model] {
        var persons = [Person()]
        let (session, url) = getConnection()

        // Forced unwrap of url - we set it ourselves
        let task: URLSessionTask = session.dataTask(with: url) { data, response, error in

            // Print Error
            if let error = error {
                print("error: \(error)")
            }

            if let data = data {
                // Print JSON
                do {

                    persons = try JSONDecoder().decode([Person].self, from: data)

                } catch {
                    print("JSON error: \(error.localizedDescription)")
                }
            }
        }

        task.resume()

        sleep(1)
//        debugPrint(persons)
        return persons
    }

    func delete(id: Int) -> Bool {
        
        var returnBoolean: Bool = false
        let actualUrl: String = url + String(id)
        let url = URL(string: actualUrl)
        let defaultSession = URLSession(configuration: .default)
        var request = URLRequest(url: url!)
        request.httpMethod = "DELETE"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
            defaultSession.dataTask(with: request) {data, response, error in
                
                if let error = error  {print ("HTTP-Error in \"personDAO.delete\": \(error)")}
                if let httpResponse = response as? HTTPURLResponse {
                    if httpResponse.statusCode == 204 {
                        returnBoolean = true
                    }
                }
            }.resume()
        sleep(1)
        return returnBoolean
    }
}
