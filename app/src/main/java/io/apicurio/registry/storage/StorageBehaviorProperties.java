package io.apicurio.registry.storage;

import io.apicurio.common.apps.config.Info;
import io.apicurio.registry.storage.RegistryStorage.RetrievalBehavior;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StorageBehaviorProperties {

    @ConfigProperty(name = "artifacts.skip.disabled.latest", defaultValue = "true")
    @Info(category = "storage", description = "Skip artifact versions with DISABLED state when retrieving latest artifact version", availableSince = "2.4.2-SNAPSHOT")
    boolean skipLatestDisabledArtifacts;

    public RetrievalBehavior getDefaultArtifactRetrievalBehavior() {
        if (skipLatestDisabledArtifacts) {
            return RetrievalBehavior.SKIP_DISABLED_LATEST;
        } else {
            return RetrievalBehavior.DEFAULT;
        }
    }
}
