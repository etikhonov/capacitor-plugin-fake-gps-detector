import { WebPlugin } from '@capacitor/core';

import type {
  FakeGpsDetectorPlugin,
  CheckMockLocationResult,
} from './definitions';

export class FakeGpsDetectorWeb
  extends WebPlugin
  implements FakeGpsDetectorPlugin
{
  async checkMockLocation(): Promise<CheckMockLocationResult> {
    return {
      isEnabled: false,
    };
  }
}
