package com.dji.sdk.sample.missionmanager;

import android.content.Context;
import android.util.AttributeSet;

import com.dji.sdk.sample.common.Utils;

import dji.sdk.MissionManager.DJICustomMission;
import dji.sdk.MissionManager.DJIFollowMeMission;
import dji.sdk.MissionManager.DJIHotPointMission;
import dji.sdk.MissionManager.DJIMission;
import dji.sdk.MissionManager.DJIPanoramaMission;
import dji.sdk.base.DJIError;

/**
 * Class for half circle panomara mission.
 */
public class HalfCirclePanoramaMissionView extends MissionManagerBaseView {

    public HalfCirclePanoramaMissionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected DJIMission initMission() {
        return new DJIPanoramaMission(DJIPanoramaMission.DJIPanoramaMode.HalfCircle);
    }

    @Override
    public void missionProgressStatus(DJIMission.DJIMissionProgressStatus progressStatus) {
        if (progressStatus == null) {
            return;
        }
        pushSB = new StringBuffer();
        if (progressStatus instanceof DJIPanoramaMission.DJIPanoramaMissionStatus) {

            Utils.addLineToSB(pushSB, "Total capture num", ((DJIPanoramaMission.DJIPanoramaMissionStatus) progressStatus).getTotalNumber());
            Utils.addLineToSB(pushSB, "Current taken num", ((DJIPanoramaMission.DJIPanoramaMissionStatus) progressStatus).getCurrentShotNumber());
            Utils.addLineToSB(pushSB, "Current saved num", ((DJIPanoramaMission.DJIPanoramaMissionStatus) progressStatus).getCurrentSavedNumber());
            DJIError err = progressStatus.getError();
            Utils.addLineToSB(pushSB, "Mission status", err == null ? "Normal" : err.getDescription());

        }
        else if (
                progressStatus instanceof DJIFollowMeMission.DJIFollowMeMissionStatus ||
                        progressStatus instanceof DJIHotPointMission.DJIHotPointMissionStatus ||
                        progressStatus instanceof DJIPanoramaMission.DJIPanoramaMissionStatus ||
                        progressStatus instanceof DJICustomMission.DJICustomMissionProgressStatus
                ) {
            Utils.addLineToSB(pushSB, null, "Received " + progressStatus.getClass().getSimpleName());
        }
        else {
            DJIError err = progressStatus.getError();
            Utils.addLineToSB(pushSB, "Previous mission result", err == null ? "Success" : err.getDescription());
        }
        Utils.setResultToText(mContext, mMissionPushInfoTV, pushSB.toString());
    }
}