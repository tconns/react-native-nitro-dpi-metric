import UIKit

class NitroDpiMetric: HybridNitroDpiMetricSpec {
    public func isTablet() throws -> Bool {
        return UIDevice.current.userInterfaceIdiom == .pad
    }

    public func getNavBarHeight() throws -> Double {
        guard let window = UIApplication.shared.windows.first else {
            return 0
        }
        let bottomInset = window.safeAreaInsets.bottom
        return bottomInset
    }

    public func getDpi() throws -> Double {
        return getDevicePPI()
    }

    public func deviceInch() throws -> Double {
        let screenSize = UIScreen.main.bounds.size
        let scale = UIScreen.main.scale
        let width = Double(screenSize.width * scale)
        let height = Double(screenSize.height * scale)
        
        let ppi = getDevicePPI()
        if ppi == 0 {
            return 0
        }
        
        let inchWidth = width / Double(ppi)
        let inchHeight = height / Double(ppi)
        let diagonal = sqrt(pow(inchWidth, 2) + pow(inchHeight, 2))
        
        return diagonal
    }

    private func getDevicePPI() -> Int {
        let idiom = UIDevice.current.userInterfaceIdiom
        let screenHeight = Int(UIScreen.main.bounds.size.height)
        
        if idiom == .pad {
        return 264 // Standard iPad PPI
        } else if screenHeight >= 812 {
        return 458 // Super Retina Display (iPhone X and newer)
        } else {
        return 326 // Retina iPhones
        }
    } 
}
