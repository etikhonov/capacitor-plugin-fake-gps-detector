import { registerPlugin } from '@capacitor/core';
const FakeGpsDetector = registerPlugin('FakeGpsDetector', {
    web: () => import('./web').then(m => new m.FakeGpsDetectorWeb()),
});
export * from './definitions';
export { FakeGpsDetector };
//# sourceMappingURL=index.js.map