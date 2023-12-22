package tn.amin.mpro2.hook.all;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import tn.amin.mpro2.hook.BaseHook;
import tn.amin.mpro2.hook.HookId;
import tn.amin.mpro2.hook.HookTime;
import tn.amin.mpro2.hook.listener.HookListenerResult;
import tn.amin.mpro2.hook.unobfuscation.OrcaUnobfuscator;
import tn.amin.mpro2.orca.OrcaGateway;

public class UnsentNotificationRemoveHook extends BaseHook {
    @Override
    public HookId getId() {
        return HookId.UNSENT_NOTIFICATION_REMOVE;
    }
    @Override
    public HookTime getHookTime() {
        return HookTime.AFTER_DEOBFUSCATION;
    }

    @Override
    protected Set<XC_MethodHook.Unhook> injectInternal(OrcaGateway gateway) {
        Class<?> UnsentNotificationRemoveClass = gateway.unobfuscator.getClass(OrcaUnobfuscator.CLASS_REMOVE_NOTIFICATION_ON_UNSENT);

        if (UnsentNotificationRemoveClass == null)
            throw new RuntimeException(OrcaUnobfuscator.CLASS_REMOVE_NOTIFICATION_ON_UNSENT + " is null");
        Set<XC_MethodHook.Unhook> hooks = new HashSet<>();

        for (Method method : UnsentNotificationRemoveClass.getDeclaredMethods()) {
            XC_MethodHook.Unhook hook = XposedBridge.hookMethod(method, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(false);
                }
            });
            hooks.add(hook);
            notifyListeners((listener) -> ((UnsentNotificationRemoveListener) listener).onUnsentNotificationRemove());
        }
        return hooks;
    }

    public interface UnsentNotificationRemoveListener {

        HookListenerResult<Boolean> onUnsentNotificationRemove();
    }
}
