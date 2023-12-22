package tn.amin.mpro2.features.state;

import androidx.annotation.Nullable;

import tn.amin.mpro2.debug.Logger;
import tn.amin.mpro2.features.Feature;
import tn.amin.mpro2.features.FeatureId;
import tn.amin.mpro2.features.FeatureType;
import tn.amin.mpro2.hook.HookId;
import tn.amin.mpro2.hook.all.UnsentNotificationRemoveHook;
import tn.amin.mpro2.hook.listener.HookListenerResult;
import tn.amin.mpro2.orca.OrcaGateway;

public class UnsentNotificationRemoveFeature extends Feature implements UnsentNotificationRemoveHook.UnsentNotificationRemoveListener {

    public UnsentNotificationRemoveFeature(OrcaGateway gateway) {
        super(gateway);
    }

    @Nullable
    @Override
    public String getPreferenceKey() {
        return "mpro_unsentNotif";
    }
    @Override
    public FeatureId getId() {
        return FeatureId.UNSENT_NOTIFICATION_REMOVE;
    }

    @Override
    public FeatureType getType() {
        return FeatureType.CHECKABLE_STATE;
    }

    @Override
    public HookId[] getHookIds() {
        return new HookId[] {HookId.UNSENT_NOTIFICATION_REMOVE};
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }
    @Override
    public HookListenerResult<Boolean> onUnsentNotificationRemove() {
        Logger.verbose("isEnabled: " + isEnabled());
        return HookListenerResult.consume(true);
    }
}
