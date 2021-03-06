package com.dji.sdk.sample.remotecontroller;

import android.content.Context;
import android.util.AttributeSet;

import com.dji.sdk.sample.common.BasePushDataView;
import com.dji.sdk.sample.common.DJISampleApplication;
import com.dji.sdk.sample.utils.DJIModuleVerificationUtil;

import dji.sdk.Products.DJIAircraft;
import dji.sdk.RemoteController.DJIRemoteController;

/**
 * Class for getting remote controller information.
 */
public class PushRemoteControllerDataView extends BasePushDataView{

    private DJIRemoteController mRemoteController;

    public PushRemoteControllerDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (DJIModuleVerificationUtil.isRemoteControllerAvailable()) {
            mRemoteController = ((DJIAircraft) DJISampleApplication.getProductInstance())
                    .getRemoteController();

            mRemoteController.setHardwareStateUpdateCallback(
                    new DJIRemoteController.RCHardwareStateUpdateCallback() {
                        @Override
                        public void onHardwareStateUpdate(DJIRemoteController djiRemoteController,
                                                          DJIRemoteController.DJIRCHardwareState djircHardwareState) {
                            mStringBuffer.delete(0, mStringBuffer.length());

                            mStringBuffer.append("FlightModeSwitch: ").
                                    append(djircHardwareState.flightModeSwitch.mode.name()).append("\n");
                            mStringBuffer.append("OnClickGoHomeBtn: ").
                                    append(djircHardwareState.goHomeButton.buttonDown).append("\n");
                            mStringBuffer.append("RightHorizontalChanged: ")
                                    .append(djircHardwareState.rightHorizontal.value).append("\n");

                            mHandler.sendEmptyMessage(CHANGE_TEXT_VIEW);
                        }
                    });
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (DJIModuleVerificationUtil.isRemoteControllerAvailable()) {
            mRemoteController = ((DJIAircraft) DJISampleApplication.getProductInstance())
                    .getRemoteController();

            mRemoteController.setHardwareStateUpdateCallback(null);
        }
    }

    @Override
    protected String getOSDTextResourceString() {
        return "Osd textview location";
    }
}
