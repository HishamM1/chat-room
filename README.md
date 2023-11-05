### Description
A Multi-Client Chat Server using Sockets and Threads in Java is a server application that allows multiple clients to connect and chat with each other simultaneously. It utilizes sockets for communication between the server and clients, and threads for handling each client independently.

### Project Requirements

- JDK 17
- JavaFX
- MySQL

### Project Structure

- The "chat-client" folder contains the client-side of the project.
- The "chat-server" folder contains the server-side of the project.
- The "lib" folder contains the required JavaFX JAR files.
- The "chat_room.sql" file contains the database schema for the server project.
- The "mysql-connector-java-8.0.30" JAR file is used to connect to the MySQL database and is required for the server project.

### Instructions

## Database Setup

1. Open your MySQL admin panel, whether using XAMPP or MySQL Workbench.
2. Create a database named "chat_room."
3. Import the "chat_room.sql" into the database. Alternatively, you can create a "users" table with the following structure:

   ```sql
   CREATE TABLE `users` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `username` varchar(255) NOT NULL UNIQUE,
     `password` varchar(255) NOT NULL,
     `status` enum('Available','Busy') DEFAULT 'Busy'
   );
   ```

## Server Setup

1. Open the server project using either IntelliJ IDEA or NetBeans.
    - For IntelliJ:
        - Press File
	    - Choose Project Structure
	    - Choose Modules from Project Settengs
	    - On the right half side of the window press the + button
	    - Choose JARs
	    - Open the complete project folder
	    - Choose the `mysql-connector-java-8.0.30` jar file and Choose all the jars in `lib` folder
	    - Press OK
	    - Press Apply and close the window
    - For NetBeans:
        - Click right click on the server project in netbeans
	    - Press File
	    - Choose Project Properites
	    - Choose Run
	    - Make the main class is `Main`
	    - In VM Options we need to add JavaFX we will write this
	    ` --module-path [lib folder path] --add-modules=javafx.controls,javafx.fxml `
	    - If JavaFX plugin doesn't exist press Tools and choose plugins then choose Available Plugins and install JavaFX
	    - Now go to the Dependencies folder on the left check if mysql-connector-java jar file exists
	    - If not click left click on the Dependencies folder and choose Add Dependency
	    - In Group ID write mysql
	    - In Artifact ID write mysql-connector-java
	    - In Version write 8.0.30
	    - Now press Add


## Client Setup

1. Open the server project using either IntelliJ IDEA or NetBeans.

   - For IntelliJ IDEA:
        - Click on "File" in the menu.
        - Select "Project Structure".
        - In the "Project Settings" section, choose "Modules".
        - On the right side of the window, click the "+" button.
        - Choose "JARs" and navigate to the project folder.
        - Open the "lib" folder and add all the JAR files from it.
        - Click "OK".
        - Apply the changes and close the window.

   - For NetBeans:
        - Right-click on the server project in NetBeans.
        - Select "Project Properties".
        - Go to the "Run" category.
        - Make sure the main class is set to "Client".
        - In the "VM Options" field, add the following line to enable JavaFX:
          `--module-path [path to the "lib" folder] --add-modules=javafx.controls,javafx.fxml`
        - If the JavaFX plugin is not installed, go to "Tools" -> "Plugins", select "Available Plugins", and install JavaFX.

2. If the client and server are on different machines but on the same network, follow these additional steps:

   	- Obtain the IP address of the WiFi.
        - Open the "SocketHandler" file in the client project.
        - Locate the line that says:
          ```
          socket = new Socket("192.168.1.10", 6968);
          ```
          Replace `"192.168.1.10"` with the IP address of the WiFi.
        - Make sure the port number is the same in both the server and client projects, even if they are on the same machine.


## Running the Project
    - Make sure the server is running.
    - Run the client.
    - If you want to run the admin GUI, run the Main file in the server project.
