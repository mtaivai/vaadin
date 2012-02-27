/*
@VaadinApache2LicenseForJavaFiles@
 */
package com.vaadin.terminal.gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;

public class VSliderPaintable extends VAbstractPaintableWidget {

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {

        getWidgetForPaintable().client = client;
        getWidgetForPaintable().id = uidl.getId();

        // Ensure correct implementation
        super.updateFromUIDL(uidl, client);
        if (!isRealUpdate(uidl)) {
            return;
        }

        getWidgetForPaintable().immediate = getState().isImmediate();
        getWidgetForPaintable().disabled = getState().isDisabled();
        getWidgetForPaintable().readonly = getState().isReadOnly();

        getWidgetForPaintable().vertical = uidl.hasAttribute("vertical");

        // TODO should these style names be used?
        String style = getState().getStyle();

        if (getWidgetForPaintable().vertical) {
            getWidgetForPaintable().addStyleName(
                    VSlider.CLASSNAME + "-vertical");
        } else {
            getWidgetForPaintable().removeStyleName(
                    VSlider.CLASSNAME + "-vertical");
        }

        getWidgetForPaintable().min = uidl.getDoubleAttribute("min");
        getWidgetForPaintable().max = uidl.getDoubleAttribute("max");
        getWidgetForPaintable().resolution = uidl.getIntAttribute("resolution");
        getWidgetForPaintable().value = new Double(
                uidl.getDoubleVariable("value"));

        getWidgetForPaintable().setFeedbackValue(getWidgetForPaintable().value);

        getWidgetForPaintable().buildBase();

        if (!getWidgetForPaintable().vertical) {
            // Draw handle with a delay to allow base to gain maximum width
            Scheduler.get().scheduleDeferred(new Command() {
                public void execute() {
                    getWidgetForPaintable().buildHandle();
                    getWidgetForPaintable().setValue(
                            getWidgetForPaintable().value, false);
                }
            });
        } else {
            getWidgetForPaintable().buildHandle();
            getWidgetForPaintable().setValue(getWidgetForPaintable().value,
                    false);
        }
    }

    @Override
    public VSlider getWidgetForPaintable() {
        return (VSlider) super.getWidgetForPaintable();
    }

    @Override
    protected Widget createWidget() {
        return GWT.create(VSlider.class);
    }

}