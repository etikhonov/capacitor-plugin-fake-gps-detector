import { WebPlugin } from '@capacitor/core';
import type { FakeGpsDetectorPlugin, CheckMockLocationResult } from './definitions';
export declare class FakeGpsDetectorWeb extends WebPlugin implements FakeGpsDetectorPlugin {
    checkMockLocation(): Promise<CheckMockLocationResult>;
}
