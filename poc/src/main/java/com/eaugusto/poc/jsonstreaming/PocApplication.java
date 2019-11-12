package com.eaugusto.poc.jsonstreaming;

import java.io.InputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.eaugusto.poc.jsonstreaming.model.Comment;

@SpringBootApplication
public class PocApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PocApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		URL url = new URL("https://jsonplaceholder.typicode.com/comments");
		try (InputStream is = url.openStream();
				JsonParser parser = Json.createParser(is)) {

			while (parser.hasNext()) {
				if (parser.next() == Event.START_OBJECT) {

					Comment comment = new Comment();
					while(parser.next() == Event.KEY_NAME) {
						String keyName = parser.getString();
						parser.next();//Fetch next value
						switch (keyName) {
						case "id":
							comment.setId(parser.getInt());
							break;
						case "postId":
							comment.setPostId(parser.getInt());
							break;
						case "name":
							comment.setName(parser.getString());
							break;
						case "email":
							comment.setEmail(parser.getString());
							break;
						case "body":
							comment.setBody(parser.getString());
							break;
						}

					}
					System.out.println(comment);
				}
			}
		}
	}
}