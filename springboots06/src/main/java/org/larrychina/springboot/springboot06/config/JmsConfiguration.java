package org.larrychina.springboot.springboot06.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.*;
import java.io.Serializable;

@Configuration
@EnableJms
public class JmsConfiguration {


    @Autowired
    private ConnectionFactory connectionFactory ;
    @Bean
    public DefaultJmsListenerContainerFactory myFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(myMessageConverter());
        return factory;
    }

    private MessageConverter myMessageConverter() {
        return new MessageConverter() {
            @Override
            public Message toMessage(Object o, Session session) throws JMSException, MessageConversionException {
                return session.createObjectMessage((Serializable) o) ;
            }


            @Override
            public Object fromMessage(Message message) throws JMSException, MessageConversionException {

                if(message instanceof BytesMessage){
                    byte [] bytes = new byte[(int) ((BytesMessage) message).getBodyLength()];
                    ((BytesMessage) message).readBytes(bytes) ;
                    String string = new String(bytes);
                    return string;
                } else if(message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    return textMessage.getText() ;
                }
                System.out.println("========================================================");
                System.out.println(message);
                System.out.println("========================================================");
                return null;
            }
        };
    }

}
