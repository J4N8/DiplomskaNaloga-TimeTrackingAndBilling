package me.j4n8.diplomskanaloga.frontend.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import me.j4n8.diplomskanaloga.frontend.components.MainLayout;

@Route(value = "", layout = MainLayout.class)
@PermitAll
public class HomepageView extends VerticalLayout {
	public HomepageView(){
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setPadding(false);
		setSpacing(false);
	}
}
