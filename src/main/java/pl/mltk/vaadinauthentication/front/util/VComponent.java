package pl.mltk.vaadinauthentication.front.util;

import com.vaadin.server.Responsive;
import com.vaadin.ui.*;

import java.util.HashMap;
import java.util.Map;

public class VComponent {

	AbstractOrderedLayout root = null;
	Map<String, Component> elements = new HashMap<>();
	
	public VComponent(AbstractOrderedLayout c) {
		root = c;
		defaultSettings();
	}
	
	public VComponent defaultSettings() {
		sizeUndefined();
		spacing();
		responsive();
		return this;
	}
	
	public Component build() {
		return root;
	}
	
	public VComponent vertical() {
		root = new VerticalLayout();
		return this;
	}
	
	public VComponent sizeUndefined() {
		root.setSizeUndefined();
		return this;
	}
	
	public VComponent spacing() {
		root.setSpacing(true);
		return this;
	}
	
	public VComponent margin() {
		root.setMargin(true);
		return this;
	}
	
	public VComponent alignedMiddleCenter() {
		root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		return this;
	}
	
	public VComponent alignedTopCenter() {
		root.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		return this;
	}
	
	public VComponent responsive() {
		Responsive.makeResponsive(root);
		return this;
	}
	
	public VComponent styleName(String s) {
		root.addStyleName(s);
		return this;
	}
	
	public VComponent add(String elementName, Component c) {
		root.addComponent(c);
		elements.put(elementName, c);
		return this;
	}
	
	public Component get(String elementName) {
		return elements.get(elementName);
	}
	
	public VComponent actionShortcut(String elementName, int key) {
		Component c = elements.get(elementName);
		if(c instanceof Button) {
			((Button) c).setClickShortcut(key);
		}
		return this;
	}
	
	public VComponent focus(String elementName) {
		Component c = elements.get(elementName);
		if(c instanceof AbstractTextField) {
			((AbstractTextField) c).focus();
		}
		return this;
	}
	
	public VComponent caption(String elementName, String caption) {
		Component c = elements.get(elementName);
		if(c instanceof AbstractComponent) {
			((AbstractComponent) c).setCaption(caption);
		}
		return this;
	}
	
	public VComponent styleName(String elementName, String styleName) {
		Component c = elements.get(elementName);
		if(c instanceof AbstractComponent) {
			((AbstractComponent) c).addStyleName(styleName);
		}
		return this;
	}
	
	public VComponent setRows(String elementName, int n) {
		Component c = elements.get(elementName);
		if(c instanceof TextArea) {
			((TextArea) c).setRows(n);
		}
		return this;
	}
	
	public VComponent setValue(String elementName, Boolean value) {
		Component c = elements.get(elementName);
		if(c instanceof CheckBox) {
			((CheckBox) c).setValue(value);
		}
		return this;
	}
	
	public VComponent enabled(String elementName, boolean value) {
		Component c = elements.get(elementName);
		if(c instanceof AbstractComponent) {
			((AbstractComponent) c).setEnabled(value);
		}
		return this;
	}
	
	public VComponent componentAlignment(String elementName, Alignment alignment) {
		Component c = elements.get(elementName);
		root.setComponentAlignment(c, alignment);
		return this;
	}
}
