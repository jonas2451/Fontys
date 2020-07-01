//
//  ContentView.swift
//  uncoronainfectify
//
//  Created by Jonas Terschlüsen on 08.04.20.
//  Copyright © 2020 group9. All rights reserved.
//

import SwiftUI
import MapKit
import CoreLocation

struct ContentView: View {
   
   var body: some View {
      VStack {
         GPSView()
            .edgesIgnoringSafeArea(.top)
            .frame(height: 400)
         
         VStack(alignment: .leading) {
            Text("Coordinates")
               .font(.title)
            HStack(alignment: .top) {
               Text("Longitude")
                  .font(.subheadline)
               Spacer()
               Text("Number")
                  .font(.subheadline)
            }
            HStack(alignment: .top) {
               Text("Latitude")
                  .font(.subheadline)
               Spacer()
               Text("Number")
                  .font(.subheadline)
            }
         }
         .padding()
         
         Spacer()
      }
   }
}

struct ContentView_Previews: PreviewProvider {
   static var previews: some View {
      ContentView()
   }
}

/// This is a method
/// - Parameters:
///   - x: the X
///   - y: the Y
func test(x: Double, y: Double) {
   
}

/// This is another method
/// - Parameters:
///   - x: Hans
///   - y: Peter
func test2(x: Double, y: Double) {
   
}
