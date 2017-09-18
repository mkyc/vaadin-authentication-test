package pl.mltk.vaadinauthentication.front.views;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import pl.mltk.vaadinauthentication.front.MainUI;

@UIScope
@SpringView(name = DefaultView.NAME)
public class DefaultView extends VerticalLayout implements View {
    public static final String NAME = "";


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        addComponent(new Label("This is the default view"));

        MenuBar logoutMenu = new MenuBar();
        logoutMenu.addItem("Logout", VaadinIcons.SIGN_OUT, new MenuBar.Command() {

            /**
             *
             */
            private static final long serialVersionUID = -7152775664441097183L;

            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                VaadinSession.getCurrent().getSession().invalidate();
                UI.getCurrent().getPage().setLocation(MainUI.UI_URL);
            }
        });

        logoutMenu.addStyleName("user-menu");
        addComponent(logoutMenu);
    }
}
