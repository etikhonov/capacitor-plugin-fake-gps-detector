package io.gitlab.etikhonov.gps.fakegpsdetector;

import android.content.Context;

import androidx.annotation.NonNull;

import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FakeGpsDetector")
public class FakeGpsDetectorPlugin extends Plugin {

    private FakeGpsDetector implementation = new FakeGpsDetector();
    static final String LOCATION = "location";

    @PluginMethod
    public void checkMockLocation(PluginCall pluginCall) {
        Context context = getContext();
        boolean enableHighAccuracy = Boolean.TRUE.equals(pluginCall.getBoolean("enableHighAccuracy", false));
        if (getPermissionState(LOCATION) != PermissionState.GRANTED) {
            enableHighAccuracy = false;
        }

        int timeout = 10000;
        try {
            timeout = pluginCall.getInt("timeout", 10000);
        } catch (NullPointerException ignored) {}

        implementation.checkMockLocation(context, pluginCall, enableHighAccuracy, timeout);
    }
}
