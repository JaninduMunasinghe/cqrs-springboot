package com.cqrs.command.api.exception;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

import javax.annotation.Nonnull;
import javax.management.ListenerNotFoundException;

public class ProductServiceErrorHandler implements ListenerInvocationErrorHandler {

    @Override
    public void onError(@Nonnull Exception exception, @Nonnull EventMessage<?> event, @Nonnull EventMessageHandler eventHandler) throws Exception {
        throw exception;
    }
}
