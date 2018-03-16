package com.chanven.lib.cptr;

import com.chanven.lib.cptr.indicator.PtrIndicatorEx;

/**
 * A single linked list to wrap PtrUIHandler
 */
class PtrUIHandlerHolderEx implements PtrUIHandlerEx {

    private PtrUIHandlerEx mHandler;
    private PtrUIHandlerHolderEx mNext;

    private boolean contains(PtrUIHandlerEx handler) {
        return mHandler != null && mHandler == handler;
    }

    private PtrUIHandlerHolderEx() {

    }

    public boolean hasHandler() {
        return mHandler != null;
    }

    private PtrUIHandlerEx getHandler() {
        return mHandler;
    }

    public static void addHandler(PtrUIHandlerHolderEx head, PtrUIHandlerEx handler) {

        if (null == handler) {
            return;
        }
        if (head == null) {
            return;
        }
        if (null == head.mHandler) {
            head.mHandler = handler;
            return;
        }

        PtrUIHandlerHolderEx current = head;
        for (; ; current = current.mNext) {

            // duplicated
            if (current.contains(handler)) {
                return;
            }
            if (current.mNext == null) {
                break;
            }
        }

        PtrUIHandlerHolderEx newHolder = new PtrUIHandlerHolderEx();
        newHolder.mHandler = handler;
        current.mNext = newHolder;
    }

    public static PtrUIHandlerHolderEx create() {
        return new PtrUIHandlerHolderEx();
    }

    public static PtrUIHandlerHolderEx removeHandler(PtrUIHandlerHolderEx head, PtrUIHandlerEx handler) {
        if (head == null || handler == null || null == head.mHandler) {
            return head;
        }

        PtrUIHandlerHolderEx current = head;
        PtrUIHandlerHolderEx pre = null;
        do {

            // delete current: link pre to next, unlink next from current;
            // pre will no change, current move to next element;
            if (current.contains(handler)) {

                // current is head
                if (pre == null) {

                    head = current.mNext;
                    current.mNext = null;

                    current = head;
                } else {

                    pre.mNext = current.mNext;
                    current.mNext = null;
                    current = pre.mNext;
                }
            } else {
                pre = current;
                current = current.mNext;
            }

        } while (current != null);

        if (head == null) {
            head = new PtrUIHandlerHolderEx();
        }
        return head;
    }

    @Override
    public void onUIReset(PtrFrameLayoutEx frame) {
        PtrUIHandlerHolderEx current = this;
        do {
            final PtrUIHandlerEx handler = current.getHandler();
            if (null != handler) {
                handler.onUIReset(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayoutEx frame) {
        if (!hasHandler()) {
            return;
        }
        PtrUIHandlerHolderEx current = this;
        do {
            final PtrUIHandlerEx handler = current.getHandler();
            if (null != handler) {
                handler.onUIRefreshPrepare(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayoutEx frame) {
        PtrUIHandlerHolderEx current = this;
        do {
            final PtrUIHandlerEx handler = current.getHandler();
            if (null != handler) {
                handler.onUIRefreshBegin(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayoutEx frame) {
        PtrUIHandlerHolderEx current = this;
        do {
            final PtrUIHandlerEx handler = current.getHandler();
            if (null != handler) {
                handler.onUIRefreshComplete(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayoutEx frame, boolean isUnderTouch, byte status, PtrIndicatorEx ptrIndicator) {
        PtrUIHandlerHolderEx current = this;
        do {
            final PtrUIHandlerEx handler = current.getHandler();
            if (null != handler) {
                handler.onUIPositionChange(frame, isUnderTouch, status, ptrIndicator);
            }
        } while ((current = current.mNext) != null);
    }
}
