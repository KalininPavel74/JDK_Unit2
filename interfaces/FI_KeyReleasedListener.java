package org.unit1.interfaces;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@FunctionalInterface
public interface FI_KeyReleasedListener extends KeyListener {

    @Override
    default void keyPressed(KeyEvent e) {
    }

    @Override
    default void keyTyped(KeyEvent e) {
    }
}