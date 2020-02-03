package app;

import java.io.*;
import java.net.*;

public class HttpServer {
    static final int PORT = 8000;

    public static void main(String[] args) throws IOException {
            HttpServer hs = new HttpServer();
            hs.execute();
    }

    public void execute() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.printf("Server is listening on port %d", PORT);
        try (Socket client = server.accept()) {
            BufferedReader clientInput = new BufferedReader(new InputStreamReader(client.getInputStream())); //gets client http header response
            BufferedInputStream bisHtml = new BufferedInputStream(new FileInputStream("home.html")); //reads the home.html file
            BufferedInputStream bisJson = new BufferedInputStream(new FileInputStream("jsonPage.html")); //reads the jsonPage.html

            OutputStream out = client.getOutputStream();
            String line = clientInput.readLine(); //the first line of client's http header

            //checks if the 5th character in line is a space
            if (Character.toString(line.charAt(5)).equals(" ")) {
                out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                out.write(bisHtml.readAllBytes());
            } else {
                out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                out.write(bisJson.readAllBytes());
            }
            bisHtml.close();
            bisJson.close();
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }
}
