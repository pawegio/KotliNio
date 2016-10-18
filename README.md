# KotliNio
Non-blocking I/O example in Kotlin (using java.nio)

Implementation is based on [Crunchify Tutorial](http://crunchify.com/java-nio-non-blocking-io-with-server-client-example-java-nio-bytebuffer-and-channels-selector-java-nio-vs-io/) written originally in Java.

### Server output:

```
[2016-10-18T11:24:41.196Z] Waiting
[2016-10-18T11:24:44.043Z] Connection accepted: /127.0.0.1:1111
[2016-10-18T11:24:44.043Z] Waiting
[2016-10-18T11:24:44.248Z] Message received: Facebook
[2016-10-18T11:24:44.248Z] Waiting
[2016-10-18T11:24:46.208Z] Message received: Twitter
[2016-10-18T11:24:46.208Z] Waiting
[2016-10-18T11:24:48.212Z] Message received: IBM
[2016-10-18T11:24:48.212Z] Waiting
[2016-10-18T11:24:50.214Z] Message received: Google
[2016-10-18T11:24:50.215Z] Waiting
[2016-10-18T11:24:52.219Z] Message received: Crunchify
[2016-10-18T11:24:52.219Z] Waiting
[2016-10-18T11:24:54.220Z] Message received: Close
[2016-10-18T11:24:54.221Z] Connection closed
[2016-10-18T11:24:54.221Z] Waiting
```

### Client output:

```
[2016-10-18T11:24:44.045Z] Connecting to server on port 1111
[2016-10-18T11:24:44.205Z] Sending: Facebook
[2016-10-18T11:24:46.208Z] Sending: Twitter
[2016-10-18T11:24:48.212Z] Sending: IBM
[2016-10-18T11:24:50.214Z] Sending: Google
[2016-10-18T11:24:52.219Z] Sending: Crunchify
[2016-10-18T11:24:54.220Z] Sending: Close
```
