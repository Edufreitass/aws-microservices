package br.com.siecola.service;

import br.com.siecola.enums.EventType;
import br.com.siecola.model.Envelope;
import br.com.siecola.model.Product;
import br.com.siecola.model.ProductEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(ProductPublisher.class);

    private final AmazonSNS snsClient;
    private final Topic productEventsTopic;
    private final ObjectMapper objectMapper;

    public ProductPublisher(AmazonSNS snsClient,
                            @Qualifier("productEventsTopic") Topic productEventsTopic,
                            ObjectMapper objectMapper) {
        this.snsClient = snsClient;
        this.productEventsTopic = productEventsTopic;
        this.objectMapper = objectMapper;
    }

    public void publisherProductEvent(Product product, EventType eventType, String username) {
        ProductEvent productEvent = new ProductEvent();
        productEvent.setProductId(product.getId());
        productEvent.setCode(product.getCode());
        productEvent.setUsername(username);

        Envelope envelope = new Envelope();
        envelope.setEventType(eventType);

        try {
            envelope.setData(objectMapper.writeValueAsString(productEvent));

            snsClient.publish(
                    productEventsTopic.getTopicArn(),
                    objectMapper.writeValueAsString(envelope)
            );
        } catch (JsonProcessingException e) {
            LOG.error("Failed to create product event message");
        }
    }
}
