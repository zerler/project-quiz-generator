.PHONY = make jar runjar test clean

CLASSPATH = .:./classes/:junit-platform-console-standalone-1.3.2.jar:json-simple-1.1.1.jar

make: 
	javac -cp $(CLASSPATH) -d . application/*.java
	java application.Main

jar: 
	jar cvmf manifest.txt executable.jar .

runjar:
	java -jar executable.jar

test: 
	javac -cp $(CLASSPATH) *.java
	java -jar junit-platform-console-standalone-1.3.2.jar --class-path .:./classes/ -p ""

clean:
	\rm *.class
	\rm executable.jar
