//
//  Coordinator.swift
//  uncoronainfectify
//
//  Created by Sven Rediske on 21.04.20.    //code samples from: https://www.iosapptemplates.com/blog/swiftui/photo-camera-swiftui
//  Copyright © 2020 group9. All rights reserved.
//

import SwiftUI

class Coordinator: NSObject, UINavigationControllerDelegate, UIImagePickerControllerDelegate {
    
  @Binding var isCoordinatorShown: Bool
  @Binding var imageInCoordinator: Image?
  @Binding var stringOutput: String
    
    init(isShown: Binding<Bool>, image: Binding<Image?>, stringOutput: Binding<String>) {
    _isCoordinatorShown = isShown
    _imageInCoordinator = image
    _stringOutput = stringOutput
  }
    
  func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
    guard let pickedImage = info[UIImagePickerController.InfoKey.originalImage] as? UIImage else { return }
    imageInCoordinator = Image(uiImage: pickedImage)
    isCoordinatorShown = false

    let detector:CIDetector = CIDetector(ofType: CIDetectorTypeQRCode, context: nil, options: [CIDetectorAccuracy:CIDetectorAccuracyHigh])! //here i create a so called detector that is able to decode a qr code from a CIImage
    let ciImage:CIImage = CIImage(image:pickedImage)!
    var tempQrCode = ""
    let features = detector.features(in: ciImage) //this returns an array of "features" which are no more than characters from the qr code
    for feature in features as! [CIQRCodeFeature]
    {
        tempQrCode += feature.messageString!
    }
    stringOutput = tempQrCode
//    print(tempQrCode)//Your result from QR Code (debug purposes)
    
    }
    
  func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
     isCoordinatorShown = false
  }
}
