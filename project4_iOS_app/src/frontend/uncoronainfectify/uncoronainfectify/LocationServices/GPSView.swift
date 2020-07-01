//
//  GPSView.swift
//  uncoronainfectify
//
//  Created by Guus Damen & Louisa Hereth on 14/04/2020.
//  Copyright © 2020 group9. All rights reserved.
//

import SwiftUI
import MapKit

struct GPSView: UIViewRepresentable {

    var mapView: MKMapView?

    /// location manager is an observed object, which means that every single
    /// time that class mutates (in this case, updates its location) this
    /// current class is notified
    @ObservedObject var manager = LocationManager()

    ///
    /// function implemented by UIViewRepresentable -> creates the view
    /// object and configures its initial state
    ///
    /// - Parameter context:
    /// - Returns: MKMapView object
    func makeUIView(context: Context) -> MKMapView {
        MKMapView(frame: .zero)
    }

    ///
    /// function implemented by UIViewRepresentable -> updates the state of
    /// the specified view with the new information from SwiftUI
    ///
    /// - Parameters:
    ///   - uiView: retrieves a MKMapView as argument
    ///   - context:
    func updateUIView(_ uiView: MKMapView, context: Context) {

        // displays the user on the map
        self.mapView?.showsUserLocation = true

        let coordinate = manager.location != nil ? manager.location!.coordinate : CLLocationCoordinate2D()

        // defines how much the map should be visible, as well as the zoom level
        let span = MKCoordinateSpan(latitudeDelta: 0.1, longitudeDelta: 0.1)
        // the area currently displayed by the map view
        let region = MKCoordinateRegion(center: coordinate, span: span)
        uiView.userTrackingMode = .follow // Displays the user on the map
        uiView.setRegion(region, animated: false)
        print("=============")
        print("latitude: \(coordinate.latitude)")
        print("longitude: \(coordinate.longitude)")
        print("=============")
    }
}

struct GPSView_Previews: PreviewProvider {
    static var previews: some View {
        GPSView()
    }
}
