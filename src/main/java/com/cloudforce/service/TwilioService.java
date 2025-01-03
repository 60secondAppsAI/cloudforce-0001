package com.cloudforce.service;

import java.net.URI;
import java.util.HashMap;    // Import HashMap
import java.util.Map;        // Import Map

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;   // Import Twilio class
import com.twilio.base.ResourceSet;
import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.VoiceGrant;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Call.Status;
import com.twilio.type.PhoneNumber;


@Service
public class TwilioService {

    private static final Logger logger = LoggerFactory.getLogger(TwilioService.class);

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Value("${twilio.api.key}")
    private String twilioApiKey;

    @Value("${twilio.api.secret}")
    private String twilioApiSecret;
    
    private String twilioTwimlAppSid;
    private String sixtySecondTwimlAppSid;

    public TwilioService() {
        accountSid = "ACc3c9b7414837342105bba11952fc1a1d";
        authToken = "3ae0ba7779c314640eb5441505ecfd30";
        twilioPhoneNumber = "+18557480216";
        twilioApiKey = "SK10aa7d67df5b2bedb81a88b231b95239";
        twilioApiSecret = "l2ockseh5ia7Qp8T4Z4PoUn6HGt1JSKj";
        sixtySecondTwimlAppSid = "APa8666ba50892ce9a2c9879b5bfa18806"; //60secondAppsTwiMLApp
        twilioTwimlAppSid = "AP93814101f26f8afcaed520fe582f3cb1"; //VoiceSDKTwiMLApp

        logger.info("TwilioService initialized with account SID: {}", accountSid);
        if (accountSid == null || accountSid.isEmpty() || authToken == null || authToken.isEmpty()) {
            logger.error("Twilio account SID or auth token is missing");
            throw new IllegalArgumentException("Twilio account SID and auth token must not be null or empty.");
        }
        Twilio.init(accountSid, authToken); // Initialize Twilio client
    }    

    // Method to make an outgoing call
    public String makeCall(String to, String twimlUrl) throws Exception {
        //if (twimlUrl == null || twimlUrl.isEmpty()) {
            twimlUrl = "https://60secondappstwimlfunctions-2413.twil.io/callEnnis";
        //}

        // Directly pass twimlUrl as a string without needing Uri class
        Call call = Call.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioPhoneNumber),
                new URI(twimlUrl) // twimlUrl is now a simple string here
        ).create();

        return call.getSid();
    }

    // Method to handle incoming calls (for Twilio webhook)
    public String handleIncomingCall(String requestBody) {
        Map<String, String> response = new HashMap<>();
        response.put("Response", "<Response><Say>Welcome to our service!</Say></Response>");
        return response.get("Response");
    }

    // Method to retrieve call logs
    public ResourceSet<Call> getCallLogs() {
        return Call.reader()
                .setStatus(Status.COMPLETED)
                .read();
    }

    public String generateAccessToken(String identity) {
        // Define a VoiceGrant for the token
        VoiceGrant voiceGrant = new VoiceGrant();
        voiceGrant.setOutgoingApplicationSid(twilioTwimlAppSid);  // Set this to your Voice application SID
        voiceGrant.setIncomingAllow(true);  // Allow incoming calls if necessary

        // Build the AccessToken
        AccessToken token = new AccessToken.Builder(accountSid, twilioApiKey, twilioApiSecret)
                .identity(identity)  // Identity of the client user
                .grant(voiceGrant)  // Add the Voice Grant to the token
                .build();

        logger.info("AccessToken generated: {}", token.toJwt());
        return token.toJwt();  // Return the JWT token for the client
    }
}
