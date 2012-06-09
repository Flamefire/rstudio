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
package elemental.dom;

import elemental.events.*;
import elemental.util.*;
import elemental.dom.*;
import elemental.html.*;
import elemental.css.*;
import elemental.stylesheets.*;

import java.util.Date;

/**
  * <p><span>NOTE:&nbsp;This is not implemented in Mozilla</span></p>
<p>Read-only reference to a DTD entity. Also inherits the methods and properties of <a title="En/DOM/Node" class="internal" rel="internal" href="https://developer.mozilla.org/en/DOM/Node"><code>Node</code></a>.</p>
  */
public interface Entity extends Node {

  String getNotationName();

  String getPublicId();

  String getSystemId();
}
