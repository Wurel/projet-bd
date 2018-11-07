all: Exe

Exe:
	javac -d bin -sourcepath JDBC JDBC/Exe.java

Executer:
  java -classpath bin Exe

clean:
	rm -rf bin/*.class

# java -classpath bin:bin/ojdbc6.jar Exe
# javac -d bin -classpath bin/ojdbc6.jar -sourcepath JDBC JDBC/Exe.java
