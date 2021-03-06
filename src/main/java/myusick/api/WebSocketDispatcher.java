package myusick.api;

import myusick.model.dto.PublicationsDTO;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import javax.ws.rs.core.MediaType;

/**
 * Created by david on 30/04/2015.
 */
public class WebsocketDispatcher {

    public void dispatch(PublicationsDTO message, String listener) {
        SseBroadcaster b = WebsocketProvider.getListenerMap().get(listener);
        if(b != null){
            System.out.println("To: "+listener+";; Who: "+message.getId()+";; Text: "+message.getContent());
            OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
            OutboundEvent event = eventBuilder.name("message")
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .data(PublicationsDTO.class, message)
                    .build();

            b.broadcast(event);
        }
    }
}
