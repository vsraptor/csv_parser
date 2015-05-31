### Java : CSV parser

#### What is this ?

This is a simple CSV parser implemented in Java.

The interesting part I think is that it implements parsing quoted csv files
without using Regular expressions.

I will have soon a tutorial on my site of how it works : http://ifni.co

(Once I test it more troughtfully).

Two tidbits which I will explore in the future article :

  1. I have example how to simulate map() function, similar to functional languages.
  2. I also have implemeted in my opinion the biggest discovery since sliced bread ;) (in Java world), namely :
  
```
public static <ARG> void say(ARG arg) { System.out.println(arg); }
```

You can see some simple usage of the module in ReadCSV, ReadWrite, ReadQuotedCSV classes.
