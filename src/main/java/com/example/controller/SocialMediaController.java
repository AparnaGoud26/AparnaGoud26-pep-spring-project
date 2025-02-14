package com.example.controller;

    import com.example.entity.Account;
    import com.example.entity.Message;
    import com.example.service.AccountService;
    import com.example.service.MessageService;

   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    
    import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("/api")

public class SocialMediaController {
     
     private final AccountService accountService;
     private final MessageService messageService;
        @Autowired
        public SocialMediaController(AccountService accountService, MessageService messageService) {
            this.accountService = accountService;
            this.messageService = messageService;
        }
    
        // -- User Registration & Login Endpoints ---
        
        @PostMapping("/register")
        public ResponseEntity<?> registerUser(@RequestBody Account account) {
            Account createdAccount = accountService.registerAccount(account);
            if (createdAccount == null) {
                return ResponseEntity.status(409).body("Username already exists");
            }
            return ResponseEntity.ok(createdAccount);
        }
    
    
        @PostMapping("/login")
        public ResponseEntity<?> loginUser(@RequestBody Account account) {
            Account authenticatedAccount = accountService.login(account.getUsername(), account.getPassword());
            if (authenticatedAccount == null) {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
            return ResponseEntity.ok(authenticatedAccount);
        }
    
    
        // ---  Message Endpoints ---
    
        // Create a new message
        @PostMapping
        public ResponseEntity<Message> createMessage(@RequestBody Message message) {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.ok(createdMessage);
        }        

    // Get all messages
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    // Get a message by ID
    @GetMapping("/{userId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable int userId) {
        List<Message> messages = messageService.getMessagesByUserId(userId);
        return ResponseEntity.ok(messages);
    }


    @DeleteMapping("/{id}")
     public ResponseEntity<Integer> deleteMessage(@PathVariable int id) {
    int rowsDeleted = messageService.deleteMessage(id);
    return ResponseEntity.ok(rowsDeleted); // Return number of rows deleted
   }


    // Update message text
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMessage(@PathVariable Integer id, @RequestBody String newMessageText) {
        messageService.updateMessage(id, newMessageText);
        return ResponseEntity.noContent().build();
    }
   
    
}
