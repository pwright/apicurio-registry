package io.apicurio.registry.storage.impl.gitops.model.v0;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Jakub Senko <em>m@jsenko.net</em>
 */
@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Content extends HasSchema {

    private String registryId;

    private Long id;

    private String contentHash;

    private String artifactType;

    private String dataFile;
}