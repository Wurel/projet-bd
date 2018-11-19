all: Exe

Exe:
	javac -d bin -classpath bin:bin/ojdbc6.jar -sourcepath JDBC JDBC/Exe.java

Executer:
	java -classpath bin:bin/ojdbc6.jar Exe

clean:
	rm -rf bin/*.class

# java -classpath bin:bin/ojdbc6.jar Exe
# javac -d bin -classpath bin/ojdbc6.jar -sourcepath JDBC JDBC/Exe.java
# export ORACLE_HOME=/opt/oracle
# export LD_LIBRARY_PATH=$ORACLE_HOME:$ORACLE_HOME/bin:$LD_LIBRARY_PATH
# export TNS_ADMIN=$ORACLE_HOME
# export CLASSPATH=$CLASSPATH:$ORACLE_HOME/ojdbc6.jar:.
# export PATH=$ORACLE_HOME:$PATH
