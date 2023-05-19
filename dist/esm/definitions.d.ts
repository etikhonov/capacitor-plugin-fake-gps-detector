export interface CheckMockLocationResult {
    isEnabled: boolean;
}
export interface CheckOptions {
    /**
     * High accuracy mode (such as GPS, if available)
     *
     * On Android 12+ devices it will be ignored if users didn't grant
     * ACCESS_FINE_LOCATION permissions (can be checked with location alias).
     *
     * @default false
     */
    enableHighAccuracy?: boolean;
    /**
     * The maximum wait time in milliseconds for location updates.
     * @default 10000
     */
    timeout?: number;
}
export interface FakeGpsDetectorPlugin {
    checkMockLocation(options: CheckOptions): Promise<CheckMockLocationResult>;
}
