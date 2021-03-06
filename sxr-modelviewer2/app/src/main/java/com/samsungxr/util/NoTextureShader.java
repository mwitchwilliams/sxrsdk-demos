/*
 * Copyright 2015 Samsung Electronics Co., LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package com.samsungxr.util;

import com.samsungxr.SXRContext;
import com.samsungxr.shaders.SXRPhongShader;
import com.samsungxr.modelviewer2.R;
import com.samsungxr.utility.TextFile;

import android.content.Context;

public class NoTextureShader extends SXRPhongShader {
    private static String surfShader = null;

    public NoTextureShader(SXRContext sxrContext) {
        super(sxrContext);
        if (surfShader == null) {
            Context context = sxrContext.getContext();
            surfShader = TextFile.readTextFile(context, R.raw.notexture_surface);
            setSegment("FragmentSurface", surfShader);
        }
    }
}
