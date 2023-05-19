# capacitor-plugin-fake-gps-detector

Capacitor 5 plugin for detecting if the mock location enabled

## Install

```bash
npm install capacitor-plugin-fake-gps-detector
npx cap sync
```

## API

<docgen-index>

* [`checkMockLocation(...)`](#checkmocklocation)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkMockLocation(...)

```typescript
checkMockLocation(options: CheckOptions) => Promise<CheckMockLocationResult>
```

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#checkoptions">CheckOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#checkmocklocationresult">CheckMockLocationResult</a>&gt;</code>

--------------------


### Interfaces


#### CheckMockLocationResult

| Prop            | Type                 |
| --------------- | -------------------- |
| **`isEnabled`** | <code>boolean</code> |


#### CheckOptions

| Prop                     | Type                 | Description                                                                                                                                                                           | Default            |
| ------------------------ | -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ |
| **`enableHighAccuracy`** | <code>boolean</code> | High accuracy mode (such as GPS, if available) On Android 12+ devices it will be ignored if users didn't grant ACCESS_FINE_LOCATION permissions (can be checked with location alias). | <code>false</code> |
| **`timeout`**            | <code>number</code>  | The maximum wait time in milliseconds for location updates.                                                                                                                           | <code>10000</code> |

</docgen-api>
