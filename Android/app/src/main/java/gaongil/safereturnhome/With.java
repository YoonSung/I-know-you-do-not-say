package gaongil.safereturnhome;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import org.androidannotations.annotations.EApplication;


@EApplication
public class With extends Application {
    public static Bus eventBus = new Bus(ThreadEnforcer.ANY);
}
