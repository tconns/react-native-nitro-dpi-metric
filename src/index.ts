import { NitroModules } from 'react-native-nitro-modules'
import type { NitroDpiMetric } from './specs/NitroDpiMetric.nitro'

const NitroDpiMetricHybridObject =
  NitroModules.createHybridObject<NitroDpiMetric>('NitroDpiMetric')

export function getNavBarHeight(): number {
  return NitroDpiMetricHybridObject.getNavBarHeight()
}

export function isTablet(): boolean {
  return NitroDpiMetricHybridObject.isTablet()
}

export function deviceInch(): number {
  return NitroDpiMetricHybridObject.deviceInch()
}

export function getDpi(): number {
  return NitroDpiMetricHybridObject.getDpi()
}
