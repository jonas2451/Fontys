//
//  viewScanQR.swift
//  uncoronainfectify
//
//  Created by Sven Rediske on 08.04.20.        //code samples from: https://www.iosapptemplates.com/blog/swiftui/photo-camera-swiftui
//  Copyright © 2020 group9. All rights reserved.
//

import SwiftUI

struct CaptureImageView {
      
      /// MARK: - Properties
      @Binding var isShown: Bool
      @Binding var image: Image?
      @Binding var stringOutput: String
      
      func makeCoordinator() -> Coordinator {
        return Coordinator(isShown: $isShown, image: $image, stringOutput: $stringOutput)
      }
  }
extension CaptureImageView: UIViewControllerRepresentable {
    func makeUIViewController(context: UIViewControllerRepresentableContext<CaptureImageView>) -> UIImagePickerController {
        let picker = UIImagePickerController()
        picker.delegate = context.coordinator
        picker.sourceType = .camera          //comment this out to try it with the library in the simulator
        return picker
    }
    
    func updateUIViewController(_ uiViewController: UIImagePickerController, context: UIViewControllerRepresentableContext<CaptureImageView>) { }
}

struct viewScanQR: View {
    
    @State var image: Image? = nil
    @State var showCaptureImageView: Bool = false
    @State var stringOutputInView: String = ""
    
        var body: some View {
        VStack {
            Button(action: {
              self.showCaptureImageView.toggle()
            }) {
              Text("Scan a qr code")
            }
          
          if (showCaptureImageView) {
             CaptureImageView(isShown: $showCaptureImageView, image: $image, stringOutput: $stringOutputInView)
          }else{
            image?.resizable()
            .frame(width: 220, height: 300)
            
//            Text("\(stringOutputInView)").padding()
            Text("\(stringOutputInView.count == 0 ? "" : ScanHelper.init().doStuff(otherUserIdAsString: stringOutputInView))").padding()
//            Text("\(ScanHelper.init().doStuff(otherUserId: 5))").padding()
          }
      }
    }

struct viewScanQR_Previews: PreviewProvider {
    static var previews: some View {
        viewScanQR()
        }
    }
}
