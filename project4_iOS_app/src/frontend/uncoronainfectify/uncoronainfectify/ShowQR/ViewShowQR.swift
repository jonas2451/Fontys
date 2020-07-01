//
//  ViewShowQR.swift
//  uncoronainfectify
//
//  Created by Jonas Terschlüsen on 08.04.20.
//  Copyright © 2020 group9. All rights reserved.
//
import Foundation
import SwiftUI
import UIKit


func generateQRCode(from string: String) -> UIImage? {
    let data = string.data(using: String.Encoding.ascii)
    
    if let filter = CIFilter(name: "CIQRCodeGenerator") {
        filter.setValue(data, forKey: "inputMessage")

        guard let qrImage = filter.outputImage else {return nil}
        
        let transform = CGAffineTransform(scaleX: 7, y: 7)
        let scaledQrImage = qrImage.transformed(by: transform)
        
        // IMPORTANT converting the CIImage to an UIImage...
        
        // Get a CIContext
        let context = CIContext()        // Create a CGImage *from the extent of the outputCIImage*
        guard let cgImage = context.createCGImage(scaledQrImage, from: scaledQrImage.extent) else { return nil }
        // Finally, get a usable UIImage from the CGImage
        let processedImage = UIImage(cgImage: cgImage)
        
        return processedImage
    }
    return nil
}

/// This View is showing a QR code that another user can scan
struct ViewShowQR: View {

//    let dao: DAO
    var userId: Int

    let qrImageView: UIImage?

    init() {
//        dao = PersonDAO(givenRootUrl: PersonDAO.getURL())
        userId = UserDefaults.standard.integer(forKey: "Id")

//        debugPrint(userId)

        qrImageView = generateQRCode(from: String(userId))
    }

    var body: some View {
        VStack {
            Text("Show your QR-code to the ones you meet!")
            qrImageView.map({
                Image(uiImage: $0)
                    .resizable().frame(width: 201.0, height: 201.0)
                })
        }
    }
}

struct ViewShowQR_Previews: PreviewProvider {
    static var previews: some View {
        ViewShowQR()
    }
}
