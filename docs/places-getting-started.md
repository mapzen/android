# Getting started

## 1. Sign up for a Mapzen API key

Sign up for an API key from the [Mapzen developer portal](https://mapzen.com/documentation/overview/).

### Set API key using a string resource

You can set your Mapzen API key via an XML string resource. Add a file `app/src/main/res/values/mapzen.xml` and copy the following code.

```xml
<resources>
    <string name="mapzen_api_key">your-mapzen-api-key</string>
</resources>
```

### Set API key using `MapzenManager` class

Alternatively you can set your Mapzen API key via the `MapzenManager` class. Just make sure you call the following method prior to calling `MapView#getMapAsyc(...)` or creating an instance of `MapzenSearch` or `MapzenRouter`.

```java
MapzenManager.instance(context).setApiKey("your-mapzen-api-key");
```
