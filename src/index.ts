import { registerPlugin } from '@capacitor/core';

import type { FakeGpsDetectorPlugin } from './definitions';

const FakeGpsDetector = registerPlugin<FakeGpsDetectorPlugin>(
  'FakeGpsDetector',
  {
    web: () => import('./web').then(m => new m.FakeGpsDetectorWeb()),
  },
);

export * from './definitions';
export { FakeGpsDetector };
