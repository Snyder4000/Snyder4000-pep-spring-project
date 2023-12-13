package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accServ;
    @Autowired
    private MessageService messServ;

    @PostMapping(value = "/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account acc){
        if(acc != null && !acc.getUsername().isEmpty() && acc.getPassword().length() >= 4 && accServ.getAccount(acc) == null){
            Account account = accServ.saveAccount(acc);//need to send proper HttpStatus code: 200 HttpStatus.OK
            return ResponseEntity.status(HttpStatus.OK).body(account);
        }
        else if(acc != null && accServ.getAccount(acc) != null){
            //need to send correct HttpStatus code: 409 HttpStatus.CONFLICT
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(acc);
        } 
        else{
            //need to send correct HttpStatus code: 400 HttpStatus.BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } 
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account acc){
        // TODO: Create this method
        Account login = accServ.getAccount(acc);
        if(login != null && login.getPassword().equals(acc.getPassword())){
            return ResponseEntity.status(HttpStatus.OK).body(login);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message mess){
        if(mess.getMessage_text() != null && mess.getMessage_text().length() <= 255 && mess.getMessage_text().length() >= 1 && accServ.getAccountById(mess.getPosted_by()) != null){
            Message createdMessage = messServ.createMessage(mess);
            return ResponseEntity.status(HttpStatus.OK).body(createdMessage);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping(value = "/messages/{id}")
    public ResponseEntity<Integer> updateMessage(@RequestBody Message mess, @PathVariable("id") int messageId){
        if(messServ.updateMessage(messageId, mess) == 1){
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/messages")
    public List<Message> getAllMessages(){
        return messServ.getAllMessages();
    }

    @GetMapping(value = "/messages/{id}")
    public Message getAllMessagesByID(@PathVariable("id") int idNum){
        return messServ.getAllMessagesByID(idNum);
    }

    @GetMapping(value = "/accounts/{id}/messages")
    public List<Message> getAllMessagesByUserID(@PathVariable("id") int idNum){
        return messServ.getAllMessagesByUserID(idNum);
    }

    @DeleteMapping(value = "/messages/{id}")
    public ResponseEntity<String> deleteMessageById(@PathVariable("id") int idNum){
        if(messServ.getAllMessagesByID(idNum) != null){
            Message dMessage = messServ.getAllMessagesByID(idNum);
            messServ.deleteMessage(dMessage.getMessage_id());
            return ResponseEntity.status(HttpStatus.OK).body("1");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
