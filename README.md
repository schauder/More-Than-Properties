More Then Properties (MTP) is an experimental approach to binding and validation for Swing Components with Scala.

But it goes beyond just binding to components. The idea is that you can specify the complete logic for a panel without referencing a single Swing class and without specifying a property or similar using a String or any other mechanism which basically boils down to reflection. If this is achieved you can

* create at least a prototype GUI automatically
* test the GUI behavior without worrying about EDT and Swing Components or ugly things like Robots
* tweak and manipulate the GUI layout without worrying about breaking the logic, because a) in the GUI code is no more logic embedded b) you have good test coverage for it.

If you are interested, continue reading in the [Wiki](https://github.com/schauder/More-Than-Properties/wiki) or dive into the Source Code. I recommend the [PersonEditor](https://github.com/schauder/More-Than-Properties/blob/master/src/main/scala/de/schauderhaft/mtp/demo/PersonEditor.scala), which is an example of what is currently possible.

There is a lot of experimentation going on in this project, which means nothing is stable.
So if you are interested in using anything just grab the source code and let me know about your experiences.