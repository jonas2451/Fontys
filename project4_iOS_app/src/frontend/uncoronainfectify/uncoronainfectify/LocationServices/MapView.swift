//
// Created by Guus Damen & Louisa Hereth on 29/05/2020.
// Copyright (c) 2020 group9. All rights reserved.
//

import SwiftUI
import MapKit

struct MapView: UIViewRepresentable {
    var coordinate: CLLocationCoordinate2D

    ///
    /// function implemented by UIViewRepresentable -> creates the view
    /// object and configures its initial state
    ///
    /// - Parameter context:
    /// - Returns: MKMapView object
    func makeUIView(context: Context) -> MKMapView {
        return MKMapView(frame: .zero)
    }

    ///
    /// function implemented by UIViewRepresentable -> updates the state of
    /// the specified view with the new information from SwiftUI
    ///
    /// - Parameters:
    ///   - uiView: retrieves a MKMapView as argument
    ///   - context:
    func updateUIView(_ uiView: MKMapView, context: Context) {
        // defines how much the map should be visible, as well as the zoom level
        let span = MKCoordinateSpan(latitudeDelta: 0.02, longitudeDelta: 0.02)
        // the area currently displayed by the map view
        let region = MKCoordinateRegion(center: coordinate, span: span)
        uiView.setRegion(region, animated: true)
        
        // to make an annotation of the map at the exact coordinates where the
        // meeting was
        let annotation = MKPointAnnotation()
        annotation.coordinate = coordinate
        uiView.addAnnotation(annotation)
        
        // to display the current location of the user on the map
        uiView.userTrackingMode = .follow
    }

}
