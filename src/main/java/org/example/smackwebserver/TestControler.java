package org.example.smackwebserver;

import org.example.smackwebserver.WebSocket.UserMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TestControler
{
    @GetMapping("/hello")
    public List<String> hello()
    {
        return List.of("hello","word");
    }

    @GetMapping("/test/UserMessage")
    public String testUserMessage() {
        UserMessage userMessage = UserMessage.genUserMessage(123,345, "Hi, this is a test message", new Date());
        return UserMessage.serializeUserMessage(userMessage);
    }
}
