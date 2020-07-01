//
//  loginView.swift
//  uncoronainfectify
//
//  Created by Jonas Terschlüsen on 23.04.20.
//  Copyright © 2020 group9. All rights reserved.
//

import SwiftUI
import Foundation

struct loginView: View {

    @State var phonenumber: String = ""
    @State var alertIsVisible: Bool = false

    // Implicitly required in the initializer.
    // When the phone number is entered, the login screen disappears and the app will show up -> Variable comes from App View
    @Binding var signInSuccess: Bool

    var body: some View {
        VStack {
            Text("Hello! Thanks for using our App!")
                    .padding()
            Text("Please enter YOUR mobile phone number:")
            TextField("Phonenumber... +49...", text: $phonenumber)
                    .keyboardType(.phonePad)
                    .padding()
            Button(action: {
                if (self.phonenumber != "") {

                    let range = NSRange(location: 0, length: self.phonenumber.utf16.count)

                    let regex = try! NSRegularExpression(pattern: #"\(?\+\(?49\)?[ ()]?([- ()]?\d[- ()]?){10,}"#)

                    if (regex.firstMatch(in: self.phonenumber, options: [], range: range) != nil) {

                        var daoUrl = PersonDAO.getURL()
                        let dao = PersonDAO(givenRootUrl: daoUrl)

                        // Tries to get a person with the given phoneNumber
                        let alreadyRegisteredPerson = dao.get(phoneNumber: self.phonenumber) as! Person
                        var person = Person()

                        // Create new account when number not found
                        // Login if number is found
                        if (alreadyRegisteredPerson.id != nil) {
                            person = alreadyRegisteredPerson
                        } else {
                            let model = dao.save(object: Person(phonenumber: self.phonenumber, healthstate: "succeptible"))
                            person = model as! Person
                        }

                        let defaults = UserDefaults.standard
                        defaults.set(person.id, forKey: "Id")

                        // Move the the Main-Screen of the app
                        self.signInSuccess = true
                    } else {
                        self.alertIsVisible = true
                    }
                } else {
                    self.alertIsVisible = true
                }
            }) {
                Text("Submit")
            }
            .alert(isPresented: $alertIsVisible) { () -> Alert in
                return Alert(title: Text("Not a valid phone number!"), message: Text("Please enter a valid German Phonenumber. "), dismissButton: .default(Text("OK!")))
            }
        }

    }
}