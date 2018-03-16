package com.chanven.lib.cptr;

import com.chanven.lib.cptr.indicator.PtrIndicatorEx;

public interface PtrUIHandlerEx {

    /**
     * When the content view has reached top and refresh has been completed, view will be reset.
     *
     * @param frame
     */
    public void onUIReset(PtrFrameLayoutEx frame);

    /**
     * prepare for loading
     *
     * @param frame
     */
    public void onUIRefreshPrepare(PtrFrameLayoutEx frame);

    /**
     * perform refreshing UI
     */
    public void onUIRefreshBegin(PtrFrameLayoutEx frame);

    /**
     * perform UI after refresh
     */
    public void onUIRefreshComplete(PtrFrameLayoutEx frame);

    public void onUIPositionChange(PtrFrameLayoutEx frame, boolean isUnderTouch, byte status, PtrIndicatorEx ptrIndicator);
}
