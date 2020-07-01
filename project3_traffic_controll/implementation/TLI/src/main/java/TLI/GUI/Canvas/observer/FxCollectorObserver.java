package TLI.GUI.Canvas.observer;

import TLI.drawable.Drawable;

import java.util.Collection;

public interface FxCollectorObserver {
    void updateDrawables(Collection<Drawable> drawables);
}
