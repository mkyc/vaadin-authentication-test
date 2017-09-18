package pl.mltk.vaadinauthentication.front;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import pl.mltk.vaadinauthentication.front.auth.SecurityUtils;
import pl.mltk.vaadinauthentication.front.events.NavigationEventBus;
import pl.mltk.vaadinauthentication.front.events.ViewEvent;
import pl.mltk.vaadinauthentication.front.views.DefaultView;
import pl.mltk.vaadinauthentication.front.views.login.LoginView;

@SpringUI
@SpringViewDisplay
public class MainUI extends UI {

    public static final String UI_URL = "";

    @Autowired
    NavigationEventBus entranceEventBus;

    @Autowired
    SecurityUtils securityUtils;

    @Autowired
    LoginView loginView;

    @Autowired
    DefaultView defaultView;

    @Override
    protected void init(VaadinRequest request) {
        // Subscribe to navigateToView events.
        entranceEventBus.subscribe(this);
        if (!securityUtils.isLoggedIn()) {
            buildNotLoggedInElements();
            getNavigator().navigateTo(LoginView.NAME);
        } else {
            buildLoggedInElements();
            getNavigator().navigateTo(DefaultView.NAME);
        }
    }

    private void buildNotLoggedInElements() {
        getNavigator().addView(LoginView.NAME, loginView);
    }

    private void buildLoggedInElements() {
        getNavigator().addView(DefaultView.NAME, defaultView);
    }

    @EventBusListenerMethod(scope = EventScope.SESSION)
    public void navigateToView(final ViewEvent.NavigateToViewEvent event) {
        this.getUI().getNavigator().navigateTo(event.getViewName());
    }

    @EventBusListenerMethod(scope = EventScope.SESSION)
    public void navigateToExternalUI(final ViewEvent.NavigateToExternalUIEvent event) {
        this.getPage().setLocation("/" + event.getUiName());
    }

    /**
     *
     * @param event
     */
    @EventBusListenerMethod(scope = EventScope.SESSION)
    public void navigateToView(final ViewEvent.NavigateToViewWithSessionStoreIDEvent event) {
        this.getUI().getNavigator().navigateTo(event.getViewName() +"/" + event.getSessionStoreID());
    }
}
