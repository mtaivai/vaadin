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

package com.vaadin.tests;

import java.util.Collection;
import java.util.Vector;

import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.VerticalLayout;

/**
 * This test has a somewhat deep layout within one page. At the bottom, Select
 * and Datefield render their popups incorrectly. Popus tend to be "left behind"
 * from the actual components. When the page is even bigger or longer, the
 * popups are eventually rendered outside the visual parts of the page.
 * 
 * @author Ville Ingman
 * 
 */
public class TestSelectAndDatefieldInDeepLayouts extends CustomComponent {

    public TestSelectAndDatefieldInDeepLayouts() {
        final AbstractOrderedLayout root = getOrderedLayout();
        setCompositionRoot(root);

        root.addComponent(getSelect());
        root.addComponent(getDateField());
        root.addComponent(getSelect());
        root.addComponent(getDateField());

        final Panel p1 = getPanel();
        root.addComponent(p1);

        p1.addComponent(getSelect());
        p1.addComponent(getDateField());
        p1.addComponent(getSelect());
        p1.addComponent(getDateField());

        final AbstractOrderedLayout l1 = getOrderedLayout();
        p1.addComponent(l1);

        l1.addComponent(getSelect());
        l1.addComponent(getDateField());
        l1.addComponent(getSelect());
        l1.addComponent(getDateField());

        final Panel p2 = getPanel();
        l1.addComponent(p2);

        p2.addComponent(getSelect());
        p2.addComponent(getDateField());
        p2.addComponent(getSelect());
        p2.addComponent(getDateField());

    }

    VerticalLayout getOrderedLayout() {
        final VerticalLayout l = new VerticalLayout();
        l.setCaption(getCaption("orderedlayout"));
        return l;
    }

    Panel getPanel() {
        final Panel panel = new Panel();
        panel.setCaption(getCaption("panel"));
        return panel;
    }

    Component getSelect() {
        return new Select(getCaption("select"), getSelectOptions());
    }

    Component getDateField() {
        return new DateField(getCaption("datefield"));
    }

    private Collection<String> getSelectOptions() {
        final Collection<String> opts = new Vector<String>(3);
        opts.add(getCaption("opt 1"));
        opts.add(getCaption("opt 2"));
        opts.add(getCaption("opt 3"));
        return opts;
    }

    private String getCaption(String string) {
        return string + (Math.random() * 99999.9);
        // This is Java 5 code:
        // return string + " " + UUID.randomUUID().toString().substring(0, 5);
    }

}