//
//  startupView.swift
//  uncoronainfectify
//
//  Created by Jonas Terschlüsen on 23.04.20.
//  Copyright © 2020 group9. All rights reserved.
//

import SwiftUI

struct startupView: View {

    let dao = PersonDAO.init(givenRootUrl: PersonDAO.getURL())

//    var body: some View {
//        VStack {
//            Text("test")
//            Button(action: {
//                self.dao.fetchPerson(movieId: 1) { (result:
//                        Result<Person, PersonDAO.PersonDAOError>) in
//                    switch result {
//                    case .success(let person):
//                        print(person)
//                    case .failure(let error):
//                        print(error.localizedDescription)
//                    }
//                }
//            }, label: {Text("Get All")})
//        }
//    }

//    var test: String;

    var body: some View {
        VStack {
            Text("test")
            Button(action: {
                //self.dao.save(object: Person(phonenumber: "12345678910", healthstate: "sick"))
            }, label: {Text("Get All")})
//            Text(test)
        }
    }
}

struct startupView_Previews: PreviewProvider {
    static var previews: some View {
        startupView()
    }
}
