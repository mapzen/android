# Turn-by-Turn

## Getting Started

To start using Mapzen Turn-by-turn in your apps, you need a Mapzen developer API key. After you [sign up for an API key](https://mapzen.com/developers/sign_in) you will need to include it in your application through the Java API or in a `mapzen.xml` properties file in your app resources folder.

**Java API**
```java
MapzenRouter router = new MapzenRouter(this, [YOUR_MAPZEN_API_KEY]);
```

-or-

**mapzen.xml**
```xml
<string name="mapzen_api_key">[YOUR_MAPZEN_API_KEY]</string>
```

```java
MapzenRouter router = new MapzenRouter(this);
```

## Fetch Route

With the API key set, you're ready to fetch a route. Set the costing model from one of the following calls:

```java
router.setMultimodal();
router.setWalking();
router.setBiking();
router.setDriving();
```

Next, set a callback so you know when the route is successfully retrieved or when a failure occurs while trying to fetch it:

```java
router.setCallback(new RouteCallback() {
    @Override public void success(Route route) {
        //Do something with route    
    }

    @Override public void failure(int i) {
        //Handle failure
    }
});
```

Then, set two locations so the router knows where to route to/from:

```java
double[] point = {40.734807, -73.984770};
router.setLocation(point);

double[] point = {40.732172, -73.998674};
router.setLocation(point);
```

Now you're ready to fetch a route:

```java
router.fetch();
```

## Track User's Progress Along Route

You can receive callbacks when significant events occur due to particular changes in location by using the `RouteEngine`. First create an engine:

```java
RouteEngine engine = new RouteEngine();
```

Next, set a RouteListener for the object. Note that this must be done before you can set the engine’s route.

```java
RouteListener routeListener = new RouteListener() {
   @Override public void onRouteStart() {
      	//Show UI to indicate route started
   }

   @Override public void onRecalculate(ValhallaLocation location) {
        //Fetch new route
   }

   @Override public void onSnapLocation(ValhallaLocation originalLocation,
       ValhallaLocation snapLocation) {
        //Center map on snapLocation
   }

   @Override public void onMilestoneReached(int index, RouteEngine.Milestone milestone) {
        //String instruction  = route.getRouteInstructions().get(index).getVerbalTransitionAlertInstruction();
        //Speak alert instruction
   }

   @Override public void onApproachInstruction(int index) {
        //String instruction  = route.getRouteInstructions().get(index).getVerbalPreTransitionInstruction();
        //Speak pre transition instruction
   }

   @Override public void onInstructionComplete(int index) {
        //String instruction  = route.getRouteInstructions().get(index).getVerbalPostTransitionInstruction();
        //Speak post transition instruction
   }

   @Override
   public void onUpdateDistance(int distanceToNextInstruction, int distanceToDestination) {
        //Update trip summary UI to reflect distance away from destination
   }

   @Override public void onRouteComplete() {
        //Show 'you have arrived' UI
   }
};
engine.setListener(routeListener);
```

After the listener is set, the engine is ready to receive a route:

```java
engine.setRoute(route);
```

The next thing you need to do is retrieve the device’s current location. We won’t detail out how to do this here but we recommend using a library like [Lost](https://github.com/mapzen/LOST) to facilitate this task. As you receive location updates, you need to notify the `RouteEngine` of the new current location. You can do this by converting your `Location` objects into `ValhallaLocation` objects and simply calling:

```java
ValhallaLocation valhallaLocation = new ValhallaLocation();
valhallaLocation.setBearing(location.getBearing());
valhallaLocation.setLatitude(location.getLatitude());
valhallaLocation.setLongitude(location.getLongitude());

engine.onLocationChanged(valhallaLocation);
```

Every time this method is called, `RouteEngine` will do the heavy lifting to determine whether the user has started the route, reached a milestone or instruction, gotten lost, or ended the route. Your `RouteListener` will be called on each of these events, allowing you to appropriately request a new route if the user is lost or speak instructions upon the approach of a milestone or instruction.

