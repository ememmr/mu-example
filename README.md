
# mu-example

This repository contains three examples using [mu].

## simple-service/simple-client
It uses:
- cats library
- log4cats library
- mu-rpc-netty library
- mu-rpc-server library
- mu-rpc-channel library
- mu-rpc-testing library
    - scalatest library
- mu-rpc-dropwizard library
    - http4s-prometheus-metrics library
    - http4s-blaze-client library
    - http4s-blaze-server library

Run first,
```
sbt server/run
```
after that,
```
sbt client/run
```
#### Note
This example generates `scala` definitions from `proto` files, using `mu` plugin. For more information see *Generate sources from IDL* section on `mu` documentation.  

## fs2-service/fs2-client
It uses:
- cats library
- log4cats library
- mu-rpc-netty library
- mu-rpc-server library
- mu-rpc-channel library
- mu-config library
- mu-rpc-fs2 library
    - fs2-core library
- mu-rpc-prometheus library
    - http4s-prometheus-metrics library
    - http4s-blaze-client library
    - http4s-blaze-server library
 
Run first,
```
sbt fs2server/run
```
after that,
```
sbt fs2client/run
```
#### Note
This example generates `scala` definitions from `proto` files, using `mu` plugin. For more information see *Generate sources from IDL* section on `mu` documentation. 

## monix-service/monix-client
It uses:
- cats library
- log4cats library
- mu-rpc-okhttp library
- mu-config library
- mu-rpc-server library
- mu-rpc-channel library
- mu-config library
- mu-rpc-monix library `TODO`
    - monix library
    
 
Run first,
```
sbt monixServer/run
```
after that,
```
sbt monixClient/run
```


# References
* [`mu` documentation](https://higherkindness.io/mu/)
* [47 Degrees' Blog](https://www.47deg.com/blog/tags/mu/)
* [Metrics' article](https://www.47deg.com/blog/metrics-integration-with-mu/) (`Prometheus` and `Dropwizard`)



[mu]: https://higherkindness.github.io/mu/