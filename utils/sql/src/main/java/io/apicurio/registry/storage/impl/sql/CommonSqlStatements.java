/*
 * Copyright 2020 Red Hat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apicurio.registry.storage.impl.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Shared base class for all sql statements.
 * @author eric.wittmann@gmail.com
 */
public abstract class CommonSqlStatements implements SqlStatements {

    /**
     * Constructor.
     */
    public CommonSqlStatements() {
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements.core.storage.jdbc.ISqlStatements#databaseInitialization()
     */
    @Override
    public List<String> databaseInitialization() {
        DdlParser parser = new DdlParser();
        try (InputStream input = getClass().getResourceAsStream(dbType() + ".ddl")) {
            if (input == null) {
                throw new RuntimeException("DDL not found for dbtype: " + dbType());
            }
            return parser.parse(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements.core.storage.jdbc.ISqlStatements#databaseUpgrade(int, int)
     */
    @Override
    public List<String> databaseUpgrade(int fromVersion, int toVersion) {
        List<String> statements = new ArrayList<>();
        DdlParser parser = new DdlParser();
        
        for (int version = fromVersion + 1; version <= toVersion; version++) {
            try (InputStream input = getClass().getResourceAsStream("upgrades/" + version + "/" + dbType() + ".ddl")) {
                statements.addAll(parser.parse(input));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        return statements;
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#getDatabaseVersion()
     */
    @Override
    public String getDatabaseVersion() {
        return "SELECT a.prop_value FROM apicurio a WHERE a.prop_name = ?";
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#insertGlobalRule()
     */
    @Override
    public String insertGlobalRule() {
        return "INSERT INTO globalrules (tenantId, type, configuration) VALUES (?, ?, ?)";
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectGlobalRules()
     */
    @Override
    public String selectGlobalRules() {
        return "SELECT r.type FROM globalrules r WHERE r.tenantId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectGlobalRuleByType()
     */
    @Override
    public String selectGlobalRuleByType() {
        return "SELECT r.* FROM globalrules r WHERE r.tenantId = ? AND r.type = ?";
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#deleteGlobalRule()
     */
    @Override
    public String deleteGlobalRule() {
        return "DELETE FROM globalrules r WHERE r.tenantId = ? AND r.type = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#deleteGlobalRules()
     */
    @Override
    public String deleteGlobalRules() {
        return "DELETE FROM globalrules r WHERE r.tenantId = ?";
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#updateGlobalRule()
     */
    @Override
    public String updateGlobalRule() {
        return "UPDATE globalrules SET configuration = ? WHERE tenantId = ? AND type = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#insertArtifact()
     */
    @Override
    public String insertArtifact() {
        return "INSERT INTO artifacts (tenantId, artifactId, type, createdBy, createdOn) VALUES (?, ?, ?, ?, ?)";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#updateArtifactLatest()
     */
    @Override
    public String updateArtifactLatest() {
        return "UPDATE artifacts SET latest = ? WHERE tenantId = ? AND artifactId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#updateArtifactLatestGlobalId()
     */
    @Override
    public String updateArtifactLatestGlobalId() {
        return "UPDATE artifacts SET latest = (SELECT v.globalId FROM versions v WHERE v.tenantId = ? AND v.artifactId = ? AND v.version = ?) WHERE tenantId = ? AND artifactId = ?";
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#insertVersion()
     */
    @Override
    public String insertVersion(boolean firstVersion) {
        String query;
        if (firstVersion) {
            query = "INSERT INTO versions (globalId, tenantId, artifactId, version, state, name, description, createdBy, createdOn, labels, properties, contentId) VALUES (?, ?, ?, 1, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            query = "INSERT INTO versions (globalId, tenantId, artifactId, version, state, name, description, createdBy, createdOn, labels, properties, contentId) VALUES (?, ?, ?, (SELECT MAX(version) + 1 FROM versions WHERE tenantId = ? AND artifactId = ?), ?, ?, ?, ?, ?, ?, ?, ?)";
        }
        return query;
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectArtifactVersionMetaDataByGlobalId()
     */
    @Override
    public String selectArtifactVersionMetaDataByGlobalId() {
        return "SELECT v.*, a.type FROM versions v JOIN artifacts a ON v.tenantId = a.tenantId AND v.artifactId = a.artifactId WHERE v.globalId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectArtifactVersions()
     */
    @Override
    public String selectArtifactVersions() {
        return "SELECT version FROM versions WHERE tenantId = ? AND artifactId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectArtifactVersionMetaData()
     */
    @Override
    public String selectArtifactVersionMetaData() {
        return "SELECT v.*, a.type FROM versions v JOIN artifacts a ON v.tenantId = a.tenantId AND v.artifactId = a.artifactId WHERE v.tenantId = ? AND v.artifactId = ? AND v.version = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectArtifactMetaDataByContentHash()
     */
    @Override
    public String selectArtifactMetaDataByContentHash() {
        return "SELECT v.*, a.type FROM versions v "
                + "JOIN content c ON v.contentId = c.contentId "
                + "JOIN artifacts a ON v.tenantId = a.tenantId AND v.artifactId = a.artifactId "
                + "WHERE v.tenantId = ? AND v.artifactId = ? AND c.contentHash = ?";
    }
    
    @Override
    public String selectArtifactMetaDataByCanonicalHash() {
        return "SELECT v.*, a.type FROM versions v "
                + "JOIN content c ON v.contentId = c.contentId "
                + "JOIN artifacts a ON v.tenantId = a.tenantId AND v.artifactId = a.artifactId "
                + "WHERE v.tenantId = ? AND v.artifactId = ? AND c.canonicalHash = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectArtifactVersionContentByGlobalId()
     */
    @Override
    public String selectArtifactVersionContentByGlobalId() {
        return "SELECT v.globalId, v.version, c.content FROM versions v JOIN content c ON v.contentId = c.contentId WHERE v.tenantId = ? AND v.globalId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectArtifactVersionContent()
     */
    @Override
    public String selectArtifactVersionContent() {
        return "SELECT v.globalId, v.version, c.content FROM versions v JOIN content c ON v.contentId = c.contentId WHERE v.tenantId = ? AND v.artifactId = ? AND v.version = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectLatestArtifactContent()
     */
    @Override
    public String selectLatestArtifactContent() {
        return "SELECT v.globalId, v.version, c.content FROM artifacts a "
                + "JOIN versions v ON a.tenantId = v.tenantId AND a.latest = v.globalId "
                + "JOIN content c ON v.contentId = c.contentId "
                + "WHERE a.tenantId = ? AND a.artifactId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectLatestArtifactMetaData()
     */
    @Override
    public String selectLatestArtifactMetaData() {
        return "SELECT a.*, v.globalId, v.version, v.state, v.name, v.description, v.labels, v.properties, v.createdBy AS modifiedBy, v.createdOn AS modifiedOn "
                + "FROM artifacts a "
                + "JOIN versions v ON a.tenantId = v.tenantId AND a.latest = v.globalId "
                + "WHERE a.tenantId = ? AND a.artifactId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectContentIdByHash()
     */
    @Override
    public String selectContentIdByHash() {
        return "SELECT c.contentId FROM content c WHERE c.contentHash = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectArtifactRules()
     */
    @Override
    public String selectArtifactRules() {
        return "SELECT r.* FROM rules r WHERE r.tenantId = ? AND r.artifactId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#insertArtifactRule()
     */
    @Override
    public String insertArtifactRule() {
        return "INSERT INTO rules (tenantId, artifactId, type, configuration) VALUES (?, ?, ?, ?)";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectArtifactRuleByType()
     */
    @Override
    public String selectArtifactRuleByType() {
        return "SELECT r.* FROM rules r WHERE r.tenantId = ? AND r.artifactId = ? AND r.type = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#updateArtifactRule()
     */
    @Override
    public String updateArtifactRule() {
        return "UPDATE rules SET configuration = ? WHERE tenantId = ? AND artifactId = ? AND type = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#deleteArtifactRule()
     */
    @Override
    public String deleteArtifactRule() {
        return "DELETE FROM rules WHERE tenantId = ? AND artifactId = ? AND type = ?";
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#deleteArtifactRules()
     */
    @Override
    public String deleteArtifactRules() {
        return "DELETE FROM rules WHERE tenantId = ? AND artifactId = ?";
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#updateArtifactMetaDataLatestVersion()
     */
    @Override
    public String updateArtifactMetaDataLatestVersion() {
        return "UPDATE versions SET name = ?, description = ?, labels = ?, properties = ? WHERE tenantId = ? AND globalId = (SELECT latest FROM artifacts WHERE tenantId = ? AND artifactId = ?)";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#updateArtifactVersionMetaData()
     */
    @Override
    public String updateArtifactVersionMetaData() {
        return "UPDATE versions SET name = ?, description = ?, labels = ?, properties = ? WHERE tenantId = ? AND artifactId = ? AND version = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#deleteLabels()
     */
    @Override
    public String deleteLabels() {
        return "DELETE FROM labels WHERE globalId IN (SELECT globalId FROM versions WHERE tenantId = ? AND artifactId = ?)";
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#deleteLabelsByGlobalId()
     */
    @Override
    public String deleteLabelsByGlobalId() {
        return "DELETE FROM labels WHERE globalId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#deleteProperties()
     */
    @Override
    public String deleteProperties() {
        return "DELETE FROM properties WHERE globalId IN (SELECT globalId FROM versions WHERE tenantId = ? AND artifactId = ?)";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#deletePropertiesByGlobalId()
     */
    @Override
    public String deletePropertiesByGlobalId() {
        return "DELETE FROM properties WHERE globalId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#deleteVersions()
     */
    @Override
    public String deleteVersions() {
        return "DELETE FROM versions WHERE tenantId = ? AND artifactId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#deleteArtifact()
     */
    @Override
    public String deleteArtifact() {
        return "DELETE FROM artifacts WHERE tenantId = ? AND artifactId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectArtifactIds()
     */
    @Override
    public String selectArtifactIds() {
        return "SELECT artifactId FROM artifacts WHERE tenantId = ? LIMIT ?";
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectArtifactMetaDataByGlobalId()
     */
    @Override
    public String selectArtifactMetaDataByGlobalId() {
        return "SELECT a.*, v.globalId, v.version, v.state, v.name, v.description, v.labels, v.properties, v.createdBy AS modifiedBy, v.createdOn AS modifiedOn "
                + "FROM artifacts a "
                + "JOIN versions v ON a.tenantId = v.tenantId AND a.artifactId = v.artifactId "
                + "WHERE v.tenantId = ? AND v.globalId = ?";
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#updateArtifactVersionState()
     */
    @Override
    public String updateArtifactVersionState() {
        return "UPDATE versions SET state = ? WHERE tenantId = ? AND globalId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#deleteVersion()
     */
    @Override
    public String deleteVersion() {
        return "DELETE FROM versions WHERE tenantId = ? AND artifactId = ? AND version = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#deleteVersionLabels()
     */
    @Override
    public String deleteVersionLabels() {
        return "DELETE FROM labels l WHERE l.globalId IN (SELECT v.globalId FROM versions v WHERE v.tenantId = ? AND v.artifactId = ? AND v.version = ?)";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#deleteVersionProperties()
     */
    @Override
    public String deleteVersionProperties() {
        return "DELETE FROM labels l WHERE l.globalId IN (SELECT v.globalId FROM versions v WHERE v.tenantId = ? AND v.artifactId = ? AND v.version = ?)";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#insertLabel()
     */
    @Override
    public String insertLabel() {
        return "INSERT INTO labels (globalId, label) VALUES (?, ?)";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#insertProperty()
     */
    @Override
    public String insertProperty() {
        return "INSERT INTO properties (globalId, pkey, pvalue) VALUES (?, ?, ?)";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectAllArtifactVersions()
     */
    @Override
    public String selectAllArtifactVersions() {
        return "SELECT v.*, a.type FROM versions v "
                + "JOIN artifacts a ON a.tenantId = v.tenantId AND a.artifactId = v.artifactId "
                + "WHERE a.tenantId = ? AND a.artifactId = ? "
                + "ORDER BY v.globalId ASC LIMIT ? OFFSET ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectAllArtifactVersionsCount()
     */
    @Override
    public String selectAllArtifactVersionsCount() {
        return "SELECT COUNT(v.globalId) FROM versions v JOIN artifacts a ON a.tenantId = v.tenantId AND a.artifactId = v.artifactId WHERE a.tenantId = ? AND a.artifactId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectArtifactCountById()
     */
    @Override
    public String selectArtifactCountById() {
        return "SELECT COUNT(a.artifactId) FROM artifacts a WHERE a.tenantId = ? AND a.artifactId = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectArtifactRuleCountByType()
     */
    @Override
    public String selectArtifactRuleCountByType() {
        return "SELECT COUNT(r.type) FROM rules r WHERE r.tenantId = ? AND r.artifactId = ? AND r.type = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectGlobalRuleCountByType()
     */
    @Override
    public String selectGlobalRuleCountByType() {
        return "SELECT COUNT(r.type) FROM globalrules r WHERE r.tenantId = ? AND r.type = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectContentCountByHash()
     */
    @Override
    public String selectContentCountByHash() {
        return "SELECT COUNT(c.contentId) FROM content c WHERE c.contentHash = ?";
    }
    
    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#selectContentById()
     */
    @Override
    public String selectContentById() {
        return "SELECT c.content FROM content c WHERE c.contentId = ?";
    }
}
