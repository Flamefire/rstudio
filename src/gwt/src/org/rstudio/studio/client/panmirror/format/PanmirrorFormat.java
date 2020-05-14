/*
 * PanmirrorMarkdownFormat.java
 *
 * Copyright (C) 2009-20 by RStudio, PBC
 *
 * Unless you have received this program directly from RStudio pursuant
 * to the terms of a commercial license agreement with RStudio, then
 * this program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */

package org.rstudio.studio.client.panmirror.format;


import org.rstudio.studio.client.panmirror.PanmirrorWriterOptions;

import jsinterop.annotations.JsType;

@JsType
public class PanmirrorFormat
{   
   public String pandocMode;
   public String pandocExtensions;
   public PanmirrorRmdExtensions rmdExtensions;
   public PanmirrorHugoExtensions hugoExtensions;
   public String[] docTypes;
   public PanmirrorWriterOptions writerOptions;
}

