package pl.mltk.vaadinauthentication.front.events;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;

@UIScope
@SpringComponent
public class NavigationEventBus {
	
	private EventBus.SessionEventBus eventBus;

	public NavigationEventBus(EventBus.SessionEventBus b) {
		eventBus = b;
	}

	public void post(final Object event) {
		eventBus.publish(EventScope.SESSION, event);
	}

	public void subscribe(final Object object) {
		eventBus.subscribe(object);
	}

	public void unsubscribe(final Object object) {
		eventBus.unsubscribe(object);
	}

}
