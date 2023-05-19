import { WebPlugin } from '@capacitor/core';
export class FakeGpsDetectorWeb extends WebPlugin {
    async checkMockLocation() {
        return {
            isEnabled: false,
        };
    }
}
//# sourceMappingURL=web.js.map