/*
 * Copyright 2012 Google Inc.
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
package elemental.js.svg;
import elemental.svg.SVGStringList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;

import elemental.events.*;
import elemental.util.*;
import elemental.dom.*;
import elemental.html.*;
import elemental.css.*;
import elemental.js.stylesheets.*;
import elemental.js.events.*;
import elemental.js.util.*;
import elemental.js.dom.*;
import elemental.js.html.*;
import elemental.js.css.*;
import elemental.js.stylesheets.*;

import java.util.Date;

public class JsSVGStringList extends JsElementalMixinBase  implements SVGStringList {
  protected JsSVGStringList() {}

  public final native int getNumberOfItems() /*-{
    return this.numberOfItems;
  }-*/;

  public final native String appendItem(String item) /*-{
    return this.appendItem(item);
  }-*/;

  public final native void clear() /*-{
    this.clear();
  }-*/;

  public final native String getItem(int index) /*-{
    return this.getItem(index);
  }-*/;

  public final native String initialize(String item) /*-{
    return this.initialize(item);
  }-*/;

  public final native String insertItemBefore(String item, int index) /*-{
    return this.insertItemBefore(item, index);
  }-*/;

  public final native String removeItem(int index) /*-{
    return this.removeItem(index);
  }-*/;

  public final native String replaceItem(String item, int index) /*-{
    return this.replaceItem(item, index);
  }-*/;
}
