'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const FakeGpsDetector = core.registerPlugin('FakeGpsDetector', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FakeGpsDetectorWeb()),
});

class FakeGpsDetectorWeb extends core.WebPlugin {
    async checkMockLocation() {
        return {
            isEnabled: false,
        };
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    FakeGpsDetectorWeb: FakeGpsDetectorWeb
});

exports.FakeGpsDetector = FakeGpsDetector;
//# sourceMappingURL=plugin.cjs.js.map
