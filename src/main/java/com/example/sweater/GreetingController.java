package com.example.sweater;

import com.example.sweater.domain.Message;
import com.example.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false,
            defaultValue="World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model){
        Iterable<Message> massages = messageRepository.findAll();
        model.put("massages", massages);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String text, @RequestParam String teg,
                      Map<String, Object> model){
        Message mess = new Message(text, teg);
        messageRepository.save(mess);

        Iterable<Message> massages = messageRepository.findAll();
        model.put("massages", massages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, @RequestParam String teg, Map<String, Object> model){
        Iterable<Message> messages;
        if(filter != null && filter.isEmpty()){
            messages = messageRepository.findByTag(filter);
        }else{
             messages = messageRepository.findAll();
        }

        model.put("massages", messages);
        return "main";
    }
}
