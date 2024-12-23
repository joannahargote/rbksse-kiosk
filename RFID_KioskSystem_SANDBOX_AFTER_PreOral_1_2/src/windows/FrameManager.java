/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package windows;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FrameManager {

    private static List<JFrame> openFrames = new ArrayList<>();

    public static void addFrame(JFrame frame) {
        openFrames.add(frame);
    }

    public static void closeAllFrames() {
        for (JFrame frame : openFrames) {
            frame.dispose();
        }
        openFrames.clear();
    }

    public static void removeFrame(JFrame frame) {
        openFrames.remove(frame);
    }
}
