import type { HybridObject } from 'react-native-nitro-modules'

export interface NitroDpiMetric
  extends HybridObject<{ ios: 'swift'; android: 'kotlin' }> {
  // Get the height of the system navigation bar
  isTablet(): boolean

  getNavBarHeight(): number

  getDpi(): number

  deviceInch(): number
}
