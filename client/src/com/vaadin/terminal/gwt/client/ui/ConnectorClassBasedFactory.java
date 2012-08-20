/*
 * Copyright 2011 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.terminal.gwt.client.ui;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.shared.Connector;

public abstract class ConnectorClassBasedFactory<T> {
    public interface Creator<T> {
        public T create();
    }

    private Map<Class<? extends Connector>, Creator<? extends T>> creators = new HashMap<Class<? extends Connector>, Creator<? extends T>>();

    protected void addCreator(Class<? extends Connector> cls,
            Creator<? extends T> creator) {
        creators.put(cls, creator);
    }

    /**
     * Creates a widget using GWT.create for the given connector, based on its
     * {@link AbstractComponentConnector#getWidget()} return type.
     * 
     * @param connector
     * @return
     */
    public T create(Class<? extends Connector> connector) {
        Creator<? extends T> foo = creators.get(connector);
        if (foo == null) {
            throw new RuntimeException(getClass().getName()
                    + " could not find a creator for connector of type "
                    + connector.getName());
        }
        return foo.create();
    }

}