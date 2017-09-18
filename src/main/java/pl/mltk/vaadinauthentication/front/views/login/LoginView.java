package pl.mltk.vaadinauthentication.front.views.login;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import pl.mltk.vaadinauthentication.front.MainUI;
import pl.mltk.vaadinauthentication.front.events.NavigationEventBus;
import pl.mltk.vaadinauthentication.front.events.ViewEvent;
import pl.mltk.vaadinauthentication.front.util.VComponent;

@UIScope
@SpringView(name = LoginView.NAME)
public class LoginView extends VerticalLayout implements View {

    public final static String NAME = "login";

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     *
     */
    private static final long serialVersionUID = -6741015911624901049L;

    private static final Logger log = LoggerFactory.getLogger(LoginView.class);

    private VComponent v;

    @Autowired
    NavigationEventBus navigationEventBus;

    private boolean login(String username, String password) {
        log.debug(username + " " + password);
        try {
            UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, password);
            if(authenticationManager == null) {
                log.debug("authenticationManager null");
            }
            Authentication token = authenticationManager
                    .authenticate(upat);
            // Reinitialize the session to protect against session fixation
            // attacks. This does not work
            // with websocket communication.
            VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
            SecurityContextHolder.getContext().setAuthentication(token);
            // Show the main UI
            navigationEventBus.post(new ViewEvent.NavigateToExternalUIEvent(MainUI.UI_URL));
            System.out.println("logged in");
            return true;
        } catch (AuthenticationException ex) {
            System.out.println("not logged in");
            return false;
        }
    }

    void loginClick(Button.ClickEvent e) {
        AbstractTextField p = ((AbstractTextField) v.get("password"));
        AbstractTextField u = ((AbstractTextField) v.get("username"));
        String pword = p.getValue();
        p.setValue("");
        if (!login(u.getValue(), pword)) {
            Notification.show("Login failed");
            u.focus();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        removeAllComponents();

        v = new VComponent(new VerticalLayout())
                .alignedMiddleCenter().styleName("login-panel")
                .add("username", new TextField("Username"))
                .add("password", new PasswordField("Password"))
                .add("button", new Button("Login", this::loginClick))
                .actionShortcut("button", ShortcutAction.KeyCode.ENTER)
                .focus("username");

        addComponent(v.build());
        setComponentAlignment(v.build(), Alignment.MIDDLE_CENTER);

        Notification notification = new Notification(
                "Choppr Biaaaatch :D");
        notification
                .setDescription("<span>Use admin:p or user:p credentials</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.setDelayMsec(20000);
        notification.show(Page.getCurrent());
    }
}
