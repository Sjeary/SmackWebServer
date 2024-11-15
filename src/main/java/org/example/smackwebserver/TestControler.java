package org.example.smackwebserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestControler
{
    @GetMapping("/hello")
    public List<String> hello()
    {
        return List.of("hello","word");
    }
}
