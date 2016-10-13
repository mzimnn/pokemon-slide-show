# SlideShow

You can watch it in action on [YouTube](https://www.youtube.com/watch?v=BmSBQlLC_Yc).

## Build

Go into the project directory and execute the command below.
<br>
This will build a jar file. You can find it in `SlideShow/target/`.

`mvn clean install`

## Execute

To execute the application go into the `target` directory and execute following command:

`java -jar SlideShow-1.0.jar`

## Proxy

When you are using a proxy you have to set the environment variable `https_proxy`.

Example (Linux):

`export https_proxy=https://192.168.0.1:8080`
