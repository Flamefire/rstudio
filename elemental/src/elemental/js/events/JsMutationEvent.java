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
package elemental.js.events;
import elemental.dom.Node;
import elemental.events.MutationEvent;
import elemental.events.Event;
import elemental.js.dom.JsNode;

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

public class JsMutationEvent extends JsEvent  implements MutationEvent {
  protected JsMutationEvent() {}

  public final native int getAttrChange() /*-{
    return this.attrChange;
  }-*/;

  public final native String getAttrName() /*-{
    return this.attrName;
  }-*/;

  public final native String getNewValue() /*-{
    return this.newValue;
  }-*/;

  public final native String getPrevValue() /*-{
    return this.prevValue;
  }-*/;

  public final native JsNode getRelatedNode() /*-{
    return this.relatedNode;
  }-*/;

  public final native void initMutationEvent(String type, boolean canBubble, boolean cancelable, Node relatedNode, String prevValue, String newValue, String attrName, int attrChange) /*-{
    this.initMutationEvent(type, canBubble, cancelable, relatedNode, prevValue, newValue, attrName, attrChange);
  }-*/;
}
