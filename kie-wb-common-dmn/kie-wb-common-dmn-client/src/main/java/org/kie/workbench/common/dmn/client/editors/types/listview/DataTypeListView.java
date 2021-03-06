/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.dmn.client.editors.types.listview;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import elemental2.dom.Element;
import elemental2.dom.HTMLAnchorElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.kie.workbench.common.dmn.client.editors.types.DataType;

import static org.kie.workbench.common.dmn.client.editors.types.listview.DataTypeListItemView.PARENT_UUID_ATTR;
import static org.kie.workbench.common.dmn.client.editors.types.listview.DataTypeListItemView.UUID_ATTR;
import static org.kie.workbench.common.dmn.client.editors.types.listview.common.ElementHelper.insertAfter;

@Templated
@ApplicationScoped
public class DataTypeListView implements DataTypeList.View {

    @DataField("list-items")
    private final HTMLDivElement listItems;

    @DataField("collapsed-description")
    private final HTMLDivElement collapsedDescription;

    @DataField("expanded-description")
    private final HTMLDivElement expandedDescription;

    @DataField("view-more")
    private final HTMLAnchorElement viewMore;

    @DataField("view-less")
    private final HTMLAnchorElement viewLess;

    private DataTypeList presenter;

    @Inject
    public DataTypeListView(final HTMLDivElement listItems,
                            final HTMLDivElement collapsedDescription,
                            final HTMLDivElement expandedDescription,
                            final HTMLAnchorElement viewMore,
                            final HTMLAnchorElement viewLess) {
        this.listItems = listItems;
        this.collapsedDescription = collapsedDescription;
        this.expandedDescription = expandedDescription;
        this.viewMore = viewMore;
        this.viewLess = viewLess;
    }

    @Override
    public void init(final DataTypeList presenter) {
        this.presenter = presenter;
    }

    @PostConstruct
    public void setup() {
        collapseDescription();
    }

    @Override
    public void setupListItems(final List<DataTypeListItem> listItems) {
        this.listItems.innerHTML = "";
        listItems.forEach(this::appendItem);
    }

    @Override
    public void addSubItems(final DataType dataType,
                            final List<DataTypeListItem> listItems) {

        cleanSubTypes(dataType.getUUID());

        Element parent = getDataTypeRow(dataType);

        for (final DataTypeListItem item : listItems) {
            final HTMLElement itemElement = item.getElement();

            insertAfter(itemElement, parent);
            parent = itemElement;
        }
    }

    void cleanSubTypes(final String uuid) {

        final String selector = "[" + PARENT_UUID_ATTR + "=\"" + uuid + "\"]";
        final NodeList<Element> subDataTypeRows = getElement().querySelectorAll(selector);

        for (int i = 0; i < subDataTypeRows.length; i++) {
            final Element item = subDataTypeRows.getAt(i);
            cleanSubTypes(item.getAttribute(UUID_ATTR));
            item.remove();
        }
    }

    @EventHandler("view-more")
    public void onClickViewMore(final ClickEvent event) {
        expandDescription();
    }

    @EventHandler("view-less")
    public void onClickViewLess(final ClickEvent event) {
        collapseDescription();
    }

    private void appendItem(final DataTypeListItem listItem) {
        listItems.appendChild(listItem.getElement());
    }

    void expandDescription() {
        collapsedDescription.hidden = true;
        expandedDescription.hidden = false;
    }

    void collapseDescription() {
        collapsedDescription.hidden = false;
        expandedDescription.hidden = true;
    }

    private Element getDataTypeRow(final DataType dataType) {
        return getElement().querySelector("[" + UUID_ATTR + "=\"" + dataType.getUUID() + "\"]");
    }
}
