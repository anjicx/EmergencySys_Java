/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbb;

/**
 *
 * @author USER
 */
import java.io.IOException;
import java.net.ServerSocket;

public class PortUtil {
    public static boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            return true; // Ako se port može koristiti
        } catch (IOException e) {
            return false; // Port je već zauzet
        }
    }
}
