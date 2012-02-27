/*
@VaadinApache2LicenseForJavaFiles@
 */
package com.vaadin.terminal.gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.ComponentState;
import com.vaadin.terminal.gwt.client.EventHelper;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VButtonPaintable.ButtonClientToServerRpc;

public class VNativeButtonPaintable extends VAbstractPaintableWidget {

    @Override
    public void init() {
        super.init();

        ButtonClientToServerRpc rpcProxy = GWT
                .create(ButtonClientToServerRpc.class);
        getWidgetForPaintable().buttonRpcProxy = initRPC(rpcProxy);
    }

    @Override
    protected boolean delegateCaptionHandling() {
        return false;
    }

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {

        // Ensure correct implementation,
        // but don't let container manage caption etc.
        super.updateFromUIDL(uidl, client);
        if (!isRealUpdate(uidl)) {
            return;
        }

        getWidgetForPaintable().disableOnClick = getState().isDisableOnClick();
        getWidgetForPaintable().focusHandlerRegistration = EventHelper
                .updateFocusHandler(this, client,
                        getWidgetForPaintable().focusHandlerRegistration);
        getWidgetForPaintable().blurHandlerRegistration = EventHelper
                .updateBlurHandler(this, client,
                        getWidgetForPaintable().blurHandlerRegistration);

        // Save details
        getWidgetForPaintable().client = client;
        getWidgetForPaintable().paintableId = uidl.getId();

        // Set text
        getWidgetForPaintable().setText(getState().getCaption());

        // handle error
        if (uidl.hasAttribute("error")) {
            if (getWidgetForPaintable().errorIndicatorElement == null) {
                getWidgetForPaintable().errorIndicatorElement = DOM
                        .createSpan();
                getWidgetForPaintable().errorIndicatorElement
                        .setClassName("v-errorindicator");
            }
            getWidgetForPaintable().getElement().insertBefore(
                    getWidgetForPaintable().errorIndicatorElement,
                    getWidgetForPaintable().captionElement);

        } else if (getWidgetForPaintable().errorIndicatorElement != null) {
            getWidgetForPaintable().getElement().removeChild(
                    getWidgetForPaintable().errorIndicatorElement);
            getWidgetForPaintable().errorIndicatorElement = null;
        }

        if (uidl.hasAttribute(ATTRIBUTE_ICON)) {
            if (getWidgetForPaintable().icon == null) {
                getWidgetForPaintable().icon = new Icon(client);
                getWidgetForPaintable().getElement().insertBefore(
                        getWidgetForPaintable().icon.getElement(),
                        getWidgetForPaintable().captionElement);
            }
            getWidgetForPaintable().icon.setUri(uidl
                    .getStringAttribute(ATTRIBUTE_ICON));
        } else {
            if (getWidgetForPaintable().icon != null) {
                getWidgetForPaintable().getElement().removeChild(
                        getWidgetForPaintable().icon.getElement());
                getWidgetForPaintable().icon = null;
            }
        }

    }

    @Override
    protected Widget createWidget() {
        return GWT.create(VNativeButton.class);
    }

    @Override
    public VNativeButton getWidgetForPaintable() {
        return (VNativeButton) super.getWidgetForPaintable();
    }

    @Override
    public ButtonState getState() {
        return (ButtonState) super.getState();
    }

    @Override
    protected ComponentState createState() {
        return GWT.create(ButtonState.class);
    }
}