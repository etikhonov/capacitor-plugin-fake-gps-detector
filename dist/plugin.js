var capacitorFakeGpsDetector = (function (exports, core) {
    'use strict';

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

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
