package com.nerb.spiawp;

import android.accessibilityservice.AccessibilityService;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class MyAccessibilityService extends AccessibilityService {


    public String Paquetew1="com.whatsapp";
    public String Paquetew2="com.whatsapp.w4b";

    public AccessibilityNodeInfo nodeuno;


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        nodeuno=event.getSource();
        CharSequence eventPackageName = event.getPackageName();

        if (eventPackageName == null) {
            return;
        }

       // printEventInfo(event);
       // printNodeTreeDepth(event.getSource());

        int eventType = event.getEventType();

        if (eventPackageName.equals(Paquetew1) || eventPackageName.equals(Paquetew2)) {
            if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED || eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
                searchFirefoxUrlBreadth(event.getSource());
            }
        }

    }

    private void searchFirefoxUrlBreadth(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> unvisited = new ArrayList<>();
        unvisited.add(nodeInfo);

        while (!unvisited.isEmpty()) {
            AccessibilityNodeInfo node = unvisited.remove(0);

            if (node == null) {
                continue;
            }

            if (firefoxUrlFound(node)) {
                return;
            }

            for (int i = 0; i < node.getChildCount(); i++) {
                unvisited.add(node.getChild(i));
            }
        }
    }

    @SuppressLint("NewApi")
    private boolean firefoxUrlFound(AccessibilityNodeInfo nodeInfo) {
        if ("android.widget.TextView".equals(nodeInfo.getClassName()) && ("com.whatsapp:id/conversation_contact_name".equals(nodeInfo.getViewIdResourceName()) || "com.whatsapp.w4b:id/conversation_contact_name".equals(nodeInfo.getViewIdResourceName()) )  && nodeInfo.getText() != null)
        {
            Log.e("Nombre Contacto: ", String.valueOf(nodeInfo.getText()));
            return true;
        }
        else{
                return false;
            }
    }


    void printEventInfo(AccessibilityEvent event) {
        Log.d(TAG, "EventType=" + AccessibilityEvent.eventTypeToString(event.getEventType()) + ", package=" + event.getPackageName());
    }

    private static void printNodeTreeDepth(AccessibilityNodeInfo nodeInfo) {
        depthFirstSearchNodeProcessing(nodeInfo, 0);
    }

    @SuppressLint("NewApi")
    public static void depthFirstSearchNodeProcessing(AccessibilityNodeInfo nodeInfo, int depth) {
        if (nodeInfo == null) {
            return;
        }

        Log.e(TAG, depth + "__" + nodeInfo.getClassName() + ", " + nodeInfo.getViewIdResourceName() + ", " + nodeInfo.getText());



        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            depthFirstSearchNodeProcessing(nodeInfo.getChild(i), depth + 1);
        }
    }

    @Override
    public void onInterrupt() {
        Log.e("SERVICIO:","Servicio Interrumpido");
    }

    @Override
    public void onServiceConnected() {
        Log.e("SERVICIO:","Servicio conectado");
    }


}
