package com.mono.order.listener;

import com.mono.common.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserEventListener {

    @EventListener
    public void handleUserCreated(UserCreatedEvent event) {
        log.info("Order module received UserCreatedEvent: userId={}, username={}",
                event.getUserId(), event.getUsername());

        // Setup order preferences, welcome discount, etc.
        log.info("Setting up order preferences for new user: {}", event.getUserId());
    }
}