package com.cloudforce.controller;

import com.cloudforce.service.TwilioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/calls")
public class CallController {

	private static final Logger logger = LoggerFactory.getLogger(TwilioService.class);
	
	@Autowired
    private TwilioService twilioService;

    // Endpoint to make an outgoing call
    @PostMapping("/make")
    public ResponseEntity<String> makeCall(@RequestParam String to, @RequestParam(required = false) String twimlUrl) {
        try {
            String callSid = twilioService.makeCall(to, twimlUrl);
            return ResponseEntity.ok("Call initiated successfully. Call SID: " + callSid);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // Endpoint to handle incoming calls (Twilio webhook)
    @PostMapping("/incoming")
    public ResponseEntity<String> handleIncomingCall(@RequestBody String requestBody) {
        return ResponseEntity.ok(twilioService.handleIncomingCall(requestBody));
    }

    // Endpoint to fetch call logs
    @GetMapping("/logs")
    public ResponseEntity<?> getCallLogs() {
        try {
            return ResponseEntity.ok(twilioService.getCallLogs());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/token")
    public ResponseEntity<String> getTwilioToken() {
        try {
            String token = twilioService.generateAccessToken("abc");
            logger.info("AccessToken in Controller Level: {}", token);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}