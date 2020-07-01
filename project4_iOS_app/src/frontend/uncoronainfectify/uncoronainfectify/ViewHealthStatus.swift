//
//  ViewHealthStatus.swift
//  uncoronainfectify
//
//  Created by Florian Blum on 22.04.20.
//  Copyright © 2020 group9. All rights reserved.
//

import SwiftUI

struct viewHealthStatus: View {
    
    @State var healthstatus = 0;
    
    
    var body: some View {
        VStack {
            Text("Select your current health status")
                .multilineTextAlignment(.center)
            Picker("healthstatus", selection: $healthstatus.onChange((healthStatusChanged))){
            Text("susceptible").tag(1)
            Text("ill").tag(2)
            Text("immune").tag(3)
            }
            .frame(width: 300.0)
        }
    }
}

func healthStatusChanged(_ tag: Int) {
    var healthStatus:String
    switch tag {
    case 1:
        healthStatus = "susceptible"
    case 2:
        healthStatus = "ill"
    case 3:
        healthStatus = "immune"
    default:
        healthStatus = "error"
    }
    let dao = PersonDAO(givenRootUrl: PersonDAO.getURL())
    var userId: Int = UserDefaults.standard.integer(forKey: "Id")
    let person = dao.get(id: userId) as! Person
    let model = dao.update(object: Person(id : userId, phonenumber : person.phonenumber, healthstate : healthStatus))
    print("Health Status changed to: \(healthStatus)")
}

extension Binding {
    func onChange(_ handler: @escaping (Value) -> Void) -> Binding<Value> {
        return Binding(
            get: { self.wrappedValue },
            set: { selection in
                self.wrappedValue = selection
                handler(selection)
        })
    }
}

struct viewHealthStatus_Previews: PreviewProvider {
    static var previews: some View {
        viewHealthStatus()
    }
}
