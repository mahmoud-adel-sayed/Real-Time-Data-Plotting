# Real Time Data Plotting
This is a sample app that simulates real time data plotting. It fetches data from a [Fake Json Server][API] every two seconds and plot the returned values on line charts. There are two implementations of the app, one written in *Kotlin*, and the other in *Java*, but the behavior of the app is the same.

### Screenshots
<img src="screenshots/home.gif" />

### Important notes
1. The three line charts shown in the app plots the actual values returned from the [Server][API] with the **blue lines** & randomly scales these values (bounded by the min & max values for each chart type) and plots them for KPI simulation only in the green and red lines.
2. The bar chart shown at the end of the home screen shows **different gradient background colors** for the 3 values (*RSRP*, *RSRQ* and *SINR*) based on the signal strength (green refers to excellent signal, yellow good signal, orange fair signal, and red poor signal).
3. When the app is put on the background (by pressing the home button) __data fetching is stopped__ (to preserve battery & CPU) and when the app is put back to the foreground the __data fetching is resumed again__.
4. *RSRP* y values range from -140 to -60, *RSRQ* y values range from -30 to 0, and *SINR* from -10 to 30.
5. Line charts support zooming, draging, point highlighting, etc.

### Signal strength ranges
* _RSRP_ (excellent: value >= -80, good: -80 > value >= -90, fair: -90 > value > -100, poor: value <= -100).
* _RSRQ_ (excellent: value >= -10, good: -10 > value >= -15, fair: -15 > value > -20, poor: value <= -20).
* _SINR_ (excellent: value >= 20, good: 20 > value >= 13, fair: 13 > value > 0, poor: value <= 0).

### Architecture
This sample app uses MVVM Architecture, a well known architecture for Android, the app's components are less dependents and easier to test.

### Libraries used
* [Dagger 2][dagger2] for dependency injection
* [Retrofit][retrofit] for REST api communication
* [Gson][gson] for parsing JSON
* [ButterKnife][butterKnife] for view binding
* [MPAndroidChart][charting] for charts

[dagger2]: https://google.github.io/dagger
[retrofit]: http://square.github.io/retrofit
[gson]: https://github.com/google/gson
[butterKnife]: https://github.com/JakeWharton/butterknife
[charting]: https://github.com/PhilJay/MPAndroidChart
[API]: https://my-json-server.typicode.com/mahmoud-adel-sayed/Real-Time-Data-Plotting/netInfo
