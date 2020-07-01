//
//  LocationManager.swift
//  uncoronainfectify
//
//  Created by Louisa Hereth on 11.04.20.
//  Copyright © 2020 group9. All rights reserved.
//

import Foundation
import MapKit


class LocationManager: NSObject, ObservableObject {

   ///
   /// you use instances of the CLLocationManager to configure, start, and
   /// stop the Core Location services.
   ///
   private let locationManager = CLLocationManager()
   @Published var location: CLLocation? = nil
   
   override init() {
      // initialises a new object immediately after memory for it has been
      // allocated
      super.init()
      // the delegate object to receive update events
      self.locationManager.delegate = self
      // the accuracy of the location data
      self.locationManager.desiredAccuracy = kCLLocationAccuracyBest
      // the minimum distance a device must move horizontally before an
      // update event is generated
      self.locationManager.distanceFilter = kCLDistanceFilterNone
      // request the user's permission for location services whether or not
      // the app is in use -> requires entree in info.plist
      self.locationManager.requestAlwaysAuthorization()
      // request the user's permission to use the location services while the
      // app is in use -> requires entree in info.plist
      self.locationManager.requestWhenInUseAuthorization()
      // starts the generation of updates that report the user's current
      // location
      self.locationManager.startUpdatingLocation()
      // a boolean value indicating whether the location manager object may
      // pause location updates
      self.locationManager.pausesLocationUpdatesAutomatically = false
  }
}

/// adds extra functionality to the LocationManager class
extension LocationManager: CLLocationManagerDelegate {
   
   func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
      
//    print("Is this called?????????")
//    print("This is the Location:", location as Any)
//    print(location?.coordinate.latitude)
      guard let location = locations.last else {
         return
      }
      
      self.location = location
   }
}
