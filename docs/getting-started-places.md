# Getting started

Sign up for a Mapzen API key on the [Mapzen developer portal](https://mapzen.com/developers).

## Set API key using a string resource

You can set your Mapzen API key via an XML string resource. Add a file `app/src/main/res/values/mapzen.xml` and copy the following code but replace `[YOUR_MAPZEN_API_KEY]` with your real Mapzen API key from the developer portal.

```xml
<resources>
    <string name="mapzen_api_key">[YOUR_MAPZEN_API_KEY]</string>
</resources>
```

## Set API key using `MapzenManager` class

Alternatively you can set your Mapzen API key via the `MapzenManager` class. Of course you'll want to replace `[YOUR_MAPZEN_API_KEY]` with your real key just like above.

```java
MapzenManager.instance(context).setApiKey("[YOUR_MAPZEN_API_KEY]");
```
