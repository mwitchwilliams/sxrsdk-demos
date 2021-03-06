/* Copyright 2015 Samsung Electronics Co., LTD
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

package com.samsungxr.keyboard.model;

import com.samsungxr.SXRAndroidResource;
import com.samsungxr.SXRContext;
import com.samsungxr.SXRPicker;
import com.samsungxr.SXRNode;
import com.samsungxr.SXRSphereCollider;
import com.samsungxr.keyboard.util.NodeNames;

public class Dashboard extends SXRNode {

    public static int currentDashboardHashCode;

    public static final float HEIGHT_SYNC_SPEED_FACTOR = 4.0f / 4;
    private static final float X_POSITION_OFFSET = 0.03f;
    public static final float Y_POSITION_OFFSET = -3.0f;
    private static final float Z_POSITION_OFFSET = 0.02f;
    private static final float Y_ANCHOR_POINT = 0.8f;
    private static final float Y_ANCHOR_POINT_THRESHOLD = .05f;

    private boolean heightSyncLocked = false;

    public static float HIGHT = 0.0f;
    public static float WIDTH = 0.0f;
    public boolean onFocus = false;
    private float deltaY;
    private float lastY;

    private float originalRotationX;
    private float originalRotationY;
    private float originalRotationZ;
    private float originalRotationW;

    float distanceToAnchorPoint;

    public boolean isAboveAnchorPoint() {

        if (getTransform().getPositionY() > Y_ANCHOR_POINT) {

            return true;

        }

        return false;
    }

    public Dashboard(SXRContext sxrContext, int gVRAndroidResourceTexture) {

        super(sxrContext, HIGHT, WIDTH);
        setName(NodeNames.DASHBOARD);

        Dashboard.currentDashboardHashCode = this.hashCode();

        originalRotationX = getTransform().getRotationX();
        originalRotationY = getTransform().getRotationY();
        originalRotationZ = getTransform().getRotationZ();
        originalRotationW = getTransform().getRotationW();

        attachComponent(new SXRSphereCollider(sxrContext));
    }

    public void resetRotation() {
        getTransform().setRotation(originalRotationW, originalRotationX, originalRotationY,
                originalRotationZ);
    }

    public void reset() {
        heightSyncLocked = false;
        deltaY = 0;
        resetRotation();
    }

    public void onUpdate(SXRNode sceneObject) {

        distanceToAnchorPoint = Math.abs(getTransform().getPositionY() - Y_ANCHOR_POINT);
        if (distanceToAnchorPoint <= Y_ANCHOR_POINT_THRESHOLD) {
            this.heightSyncLocked = true;
        }

        float[] lookAt = getSXRContext().getMainScene().getMainCameraRig().getLookAt();
        deltaY = lookAt[1] - lastY;
        lastY = lookAt[1];

        onFocus = false;
        if (sceneObject.hashCode() == hashCode()) {
            onFocus = true;
        }
    }

    public float getDeltaY() {
        return deltaY;
    }

    public boolean isHeightSyncLocked() {
        return heightSyncLocked;
    }

    public void show() {
        getSXRContext().getMainScene().addNode(this);
    }

    public void hide() {
        getSXRContext().getMainScene().removeNode(this);
    }

    public static float getZPositionOffset(float f) {

        if (f > 0) {

            return Z_POSITION_OFFSET * -1;
        }
        return Z_POSITION_OFFSET;
    }

    public static float getXPositionOffset(float f) {

        if (f > 0) {

            return X_POSITION_OFFSET * -1;
        }
        return X_POSITION_OFFSET;
    }

}
