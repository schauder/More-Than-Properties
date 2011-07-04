moreThenProperties is an experimental approach to binding for Swing Components with Scala.

In order to benefit from the power of Scala it DOES NOT USE Java Beans getter and setter, 
but a special Property class.

Binding is currently only supported for Propterty[String] and Property[Int] to JTextField and for JButtons to methods. 

There is also some support for input validation.

Further bindings might follow. And of course you can easily implement your own.

There is a lot of experimentation going on in this project, which means nothin is stable.
So if you are interested in using anything just grab the source code and let me know about your experiences.