package com.cqrs.command.api.events;

import com.cqrs.command.api.data.Product;
import com.cqrs.command.api.data.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product")
public class ProductEventsHandler {

    private ProductRepository productRepository;

    public ProductEventsHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @EventHandler
    public void on(ProductCreatedEvent event) throws Exception {
        Product product = new Product();
        BeanUtils.copyProperties(event, product);
        productRepository.save(product);

    }

}
