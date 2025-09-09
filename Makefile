run: build
	java -cp bin Main

build:
	javac -d bin Main.java
	
clean:
	rm -r bin

git:
	git config --global user.name "Tinais24"
	git config --global user.email "urtzigurkewicz@outlook.com"
