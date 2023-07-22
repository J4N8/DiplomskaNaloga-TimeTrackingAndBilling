package me.j4n8.diplomskanaloga.frontend.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import me.j4n8.diplomskanaloga.frontend.components.MainLayout;

@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class MainView extends VerticalLayout {
	public MainView(){
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setPadding(false);
		setSpacing(false);
		
		add(new Text("Hello world!"));
	}
}
