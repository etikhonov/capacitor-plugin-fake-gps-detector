import Foundation
import Capacitor
import CoreLocation

@objc(FakeGpsDetectorPlugin)
public class FakeGpsDetectorPlugin: CAPPlugin, CLLocationManagerDelegate {
    
    var locationManager = CLLocationManager()
    private var isUpdatingLocation: Bool = false
    private var callId: String!
    
    @objc func checkMockLocation(_ call: CAPPluginCall) {
        bridge?.saveCall(call)
        let enableHighAccuracy = call.getBool("enableHighAccuracy", false)
        callId = call.callbackId
        DispatchQueue.main.async {
            self.locationManager.delegate = self
            
            if enableHighAccuracy == true {
                self.locationManager.desiredAccuracy = kCLLocationAccuracyBestForNavigation
            } else {
                self.locationManager.desiredAccuracy = kCLLocationAccuracyThreeKilometers
            }
            
            if CLLocationManager.authorizationStatus() == .notDetermined {
                self.locationManager.requestWhenInUseAuthorization()
            } else {
                self.locationManager.startUpdatingLocation()
                self.isUpdatingLocation = true
            }
        }
    }
    
    
    private func resolveCall(_ call: CAPPluginCall, _ locations: [CLLocation]) {
        var result = JSObject()
        var isMock = false
        if let location = locations.first {
            if #available(iOS 15.0, *) {
                let isLocationSimulated = location.sourceInformation?.isSimulatedBySoftware ?? false
                let isProducedByAccess = location.sourceInformation?.isProducedByAccessory ?? false
                
                let info = CLLocationSourceInformation(softwareSimulationState: isLocationSimulated, andExternalAccessoryState: isProducedByAccess)
                
                if info.isSimulatedBySoftware == true || info.isProducedByAccessory == true{
                    isMock = true
                } else {
                    isMock = false
                }
            }
            result["isEnabled"] = isMock
            call.resolve(result)
        } else {
            result["isEnabled"] = isMock
            call.resolve(result)
        }
        self.locationManager.stopUpdatingLocation()
        self.isUpdatingLocation = false
        bridge?.releaseCall(call)
    }
    
    public func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        if let call = bridge?.savedCall(withID: callId) {
            call.reject(error.localizedDescription)
            bridge?.releaseCall(call)
        }
    }
    
    public func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        if let call = bridge?.savedCall(withID: callId) {
            resolveCall(call, locations)
            bridge?.releaseCall(call)
        }
    }
    
    public func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if let call = bridge?.savedCall(withID: callId) {
            checkPermissions(call)
            bridge?.releaseCall(call)
        }
        
        if !(self.callId.isEmpty) && !self.isUpdatingLocation {
            self.locationManager.startUpdatingLocation()
            self.isUpdatingLocation = true
        }
    }
}
