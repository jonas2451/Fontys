//
//  LocationDAO.swift
//  uncoronainfectify
//
//  Created by Sven Rediske on 12.05.20.
//  Copyright © 2020 group9. All rights reserved.
//

import Foundation


public class LocationDAO : DAO {
    
    let rootUrl: String     //This is the url without the entity name or an id in it.
    let entityType: String = "/locations/"
    let baseUrl: String     //This is the url with the entity name but without an id
    
    init(givenRootUrl: String) {
        rootUrl = givenRootUrl
        baseUrl = rootUrl + entityType
    }
    
    /// This method sleeps the current thread and prints a message before that
    /// - Parameter duration: The duration in seconds for which the thresh should sleep
    func letMeSleep(duration: Int, methodName: String) -> Void {
        print("This thread is sleepy for ", duration, " second(s) from:", methodName)
        sleep(1)    //What about always putting a print before a sleep?!
    }
    
    /// Saves the provided model through the REST API defined in the baseURL
    /// - Parameter object: Location that is to be stored in the REST API
    /// - Returns: Location that was stored in the REST API
    func save(object: Model) -> Model {
        
        var locationGetter:Location = Location()    //This is the variable with which we get the Location out of the closure to return it
        let url = URL(string: baseUrl)  //For saving we just need the base URL
        let defaultSession = URLSession(configuration: .default)    //We could also use URLSession.sharded
        var request = URLRequest(url: url!)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")    //We have to set the Content-Type for the REST API to interpret the Data
        
        do{ //Here the call to the REST API is done after the JSON Encoder parsed the Location
            let jsonData = try JSONEncoder().encode(object as! Location)
            defaultSession.uploadTask(with: request, from: jsonData) {data, response, error in
                
                if let error = error  {print ("HTTP-Error in \"LocationDAO.save\": \(error)")}
                if let data = data{     //Here the Json-Body that came back from the REST API gets parsed and returned
                    locationGetter = try! JSONDecoder().decode(Location.self, from: data)
                }
            }.resume()  //With resume the Datatask is started and the call to the backend is done. When the response comes back it is handled in the completionHandler meaning the code in the curly braces after the function call "uploadTask"
        }catch{
            print("JSON-Parsing-Error in \"LocationDAO.save\":", error.localizedDescription)
        }
        letMeSleep(duration: 1, methodName: "\"LocationDAO.save\"")
        return locationGetter
    }
    
    func update(object: Model) -> Bool {
        
        // Here all the required variables are created again
        var returnBoolean: Bool = false
        let location = object as! Location
        let actualUrl: String = baseUrl + String(location.id!)
        let url = URL(string: actualUrl)
        let defaultSession = URLSession(configuration: .default)
        var request = URLRequest(url: url!)
        request.httpMethod = "PATCH"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        do{
            let jsonData = try JSONEncoder().encode(object as! Location)
            defaultSession.uploadTask(with: request, from: jsonData) {data, response, error in
                
                if let error = error  {print ("HTTP-Error in \"LocationDAO.update\": \(error)")}
                if let httpResponse = response as? HTTPURLResponse {
                    if httpResponse.statusCode == 204 {returnBoolean = true}
                }
            }.resume()
        }
        catch{
            print("JSON-Encoding-Error in \"LocationDAO.update\":", error.localizedDescription)
        }
        letMeSleep(duration: 1, methodName: "\"LocationDAO.update\"")
        return returnBoolean
    }
    
    func get(id: Int) -> Model {

        let group = DispatchGroup()

        var locationGetter:Location = Location()
        let defaultSession = URLSession(configuration: .default)
        let actualUrl: String = baseUrl + String(id)
        let url = URL(string: actualUrl)

        group.enter()
        defaultSession.dataTask(with: url!)  { data, response, error in

            if let error = error  {print ("HTTP-Error in \"LocationDAO.get\": \(error)")}
            if let data = data  {
                do {
                    locationGetter = try JSONDecoder().decode(Location.self, from: data)
                }catch{
                    print("JSON-Decoding-Error in \"LocationDAO.get\":", error.localizedDescription)
                }
            }
            group.leave()
        }.resume()

        group.wait(timeout: .distantFuture)

//        letMeSleep(duration: 1, methodName: "\"LocationDAO.get\"")
        return locationGetter
    }
    
    func getAll() -> [Model] {
        
        let actualUrl: String = baseUrl
        var locationGetter:[Location] = [Location()]
        let defaultSession = URLSession(configuration: .default)
        let url = URL(string: actualUrl)
        
        defaultSession.dataTask(with: url!)  { data, response, error in

            if let error = error  {print ("HTTP-Error in \"LocationDAO.getAll\": \(error)")}
            if let data = data  {
                do {
                    let location = try JSONDecoder().decode([Location].self, from: data)
                    locationGetter = location

                } catch {
                    print("JSON-Decoding-Error in \"LocationDAO.getAll\":", error.localizedDescription)
                }
            }
        }.resume()
            
        letMeSleep(duration: 1, methodName: "\"LocationDAO.getAll\"")
        return locationGetter
    }
    
    func delete(id: Int) -> Bool {
        
        var returnBoolean: Bool = false
        let actualUrl: String = baseUrl + String(id)
        let url = URL(string: actualUrl)
        let defaultSession = URLSession(configuration: .default)
        var request = URLRequest(url: url!)
        request.httpMethod = "DELETE"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
            defaultSession.dataTask(with: request) {data, response, error in
                
                if let error = error  {print ("HTTP-Error in \"LocationDAO.delete\": \(error)")}
                if let httpResponse = response as? HTTPURLResponse {
                    if httpResponse.statusCode == 204 {
                        returnBoolean = true
                    }
                }
            }.resume()
        letMeSleep(duration: 1, methodName: "\"LocationDAO.delete\"")
        return returnBoolean
    }

    struct Location: Codable, Model{
        var id: Int?
        var latitude: Double?
        var longitude: Double?
    }
}
