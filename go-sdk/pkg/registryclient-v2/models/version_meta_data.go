package models

import (
	i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91 "github.com/microsoft/kiota-abstractions-go/serialization"
	i336074805fc853987abe6f7fe3ad97a6a6f3077a16391fec744f671a015fbd7e "time"
)

type VersionMetaData struct {
	// Stores additional data not described in the OpenAPI description found when deserializing. Can be used for serialization as well.
	additionalData map[string]any
	// The contentId property
	contentId *int64
	// The createdBy property
	createdBy *string
	// The createdOn property
	createdOn *i336074805fc853987abe6f7fe3ad97a6a6f3077a16391fec744f671a015fbd7e.Time
	// The description property
	description *string
	// The globalId property
	globalId *int64
	// An ID of a single artifact group.
	groupId *string
	// The ID of a single artifact.
	id *string
	// The labels property
	labels []string
	// The name property
	name *string
	// User-defined name-value pairs. Name and value must be strings.
	properties Propertiesable
	// Describes the state of an artifact or artifact version.  The following statesare possible:* ENABLED* DISABLED* DEPRECATED
	state *ArtifactState
	// The type property
	typeEscaped *string
	// The version property
	version *string
}

// NewVersionMetaData instantiates a new VersionMetaData and sets the default values.
func NewVersionMetaData() *VersionMetaData {
	m := &VersionMetaData{}
	m.SetAdditionalData(make(map[string]any))
	return m
}

// CreateVersionMetaDataFromDiscriminatorValue creates a new instance of the appropriate class based on discriminator value
// returns a Parsable when successful
func CreateVersionMetaDataFromDiscriminatorValue(parseNode i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) (i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.Parsable, error) {
	return NewVersionMetaData(), nil
}

// GetAdditionalData gets the AdditionalData property value. Stores additional data not described in the OpenAPI description found when deserializing. Can be used for serialization as well.
// returns a map[string]any when successful
func (m *VersionMetaData) GetAdditionalData() map[string]any {
	return m.additionalData
}

// GetContentId gets the contentId property value. The contentId property
// returns a *int64 when successful
func (m *VersionMetaData) GetContentId() *int64 {
	return m.contentId
}

// GetCreatedBy gets the createdBy property value. The createdBy property
// returns a *string when successful
func (m *VersionMetaData) GetCreatedBy() *string {
	return m.createdBy
}

// GetCreatedOn gets the createdOn property value. The createdOn property
// returns a *Time when successful
func (m *VersionMetaData) GetCreatedOn() *i336074805fc853987abe6f7fe3ad97a6a6f3077a16391fec744f671a015fbd7e.Time {
	return m.createdOn
}

// GetDescription gets the description property value. The description property
// returns a *string when successful
func (m *VersionMetaData) GetDescription() *string {
	return m.description
}

// GetFieldDeserializers the deserialization information for the current model
// returns a map[string]func(i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode)(error) when successful
func (m *VersionMetaData) GetFieldDeserializers() map[string]func(i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
	res := make(map[string]func(i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error)
	res["contentId"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetInt64Value()
		if err != nil {
			return err
		}
		if val != nil {
			m.SetContentId(val)
		}
		return nil
	}
	res["createdBy"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetStringValue()
		if err != nil {
			return err
		}
		if val != nil {
			m.SetCreatedBy(val)
		}
		return nil
	}
	res["createdOn"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetTimeValue()
		if err != nil {
			return err
		}
		if val != nil {
			m.SetCreatedOn(val)
		}
		return nil
	}
	res["description"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetStringValue()
		if err != nil {
			return err
		}
		if val != nil {
			m.SetDescription(val)
		}
		return nil
	}
	res["globalId"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetInt64Value()
		if err != nil {
			return err
		}
		if val != nil {
			m.SetGlobalId(val)
		}
		return nil
	}
	res["groupId"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetStringValue()
		if err != nil {
			return err
		}
		if val != nil {
			m.SetGroupId(val)
		}
		return nil
	}
	res["id"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetStringValue()
		if err != nil {
			return err
		}
		if val != nil {
			m.SetId(val)
		}
		return nil
	}
	res["labels"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetCollectionOfPrimitiveValues("string")
		if err != nil {
			return err
		}
		if val != nil {
			res := make([]string, len(val))
			for i, v := range val {
				if v != nil {
					res[i] = *(v.(*string))
				}
			}
			m.SetLabels(res)
		}
		return nil
	}
	res["name"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetStringValue()
		if err != nil {
			return err
		}
		if val != nil {
			m.SetName(val)
		}
		return nil
	}
	res["properties"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetObjectValue(CreatePropertiesFromDiscriminatorValue)
		if err != nil {
			return err
		}
		if val != nil {
			m.SetProperties(val.(Propertiesable))
		}
		return nil
	}
	res["state"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetEnumValue(ParseArtifactState)
		if err != nil {
			return err
		}
		if val != nil {
			m.SetState(val.(*ArtifactState))
		}
		return nil
	}
	res["type"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetStringValue()
		if err != nil {
			return err
		}
		if val != nil {
			m.SetTypeEscaped(val)
		}
		return nil
	}
	res["version"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetStringValue()
		if err != nil {
			return err
		}
		if val != nil {
			m.SetVersion(val)
		}
		return nil
	}
	return res
}

// GetGlobalId gets the globalId property value. The globalId property
// returns a *int64 when successful
func (m *VersionMetaData) GetGlobalId() *int64 {
	return m.globalId
}

// GetGroupId gets the groupId property value. An ID of a single artifact group.
// returns a *string when successful
func (m *VersionMetaData) GetGroupId() *string {
	return m.groupId
}

// GetId gets the id property value. The ID of a single artifact.
// returns a *string when successful
func (m *VersionMetaData) GetId() *string {
	return m.id
}

// GetLabels gets the labels property value. The labels property
// returns a []string when successful
func (m *VersionMetaData) GetLabels() []string {
	return m.labels
}

// GetName gets the name property value. The name property
// returns a *string when successful
func (m *VersionMetaData) GetName() *string {
	return m.name
}

// GetProperties gets the properties property value. User-defined name-value pairs. Name and value must be strings.
// returns a Propertiesable when successful
func (m *VersionMetaData) GetProperties() Propertiesable {
	return m.properties
}

// GetState gets the state property value. Describes the state of an artifact or artifact version.  The following statesare possible:* ENABLED* DISABLED* DEPRECATED
// returns a *ArtifactState when successful
func (m *VersionMetaData) GetState() *ArtifactState {
	return m.state
}

// GetTypeEscaped gets the type property value. The type property
// returns a *string when successful
func (m *VersionMetaData) GetTypeEscaped() *string {
	return m.typeEscaped
}

// GetVersion gets the version property value. The version property
// returns a *string when successful
func (m *VersionMetaData) GetVersion() *string {
	return m.version
}

// Serialize serializes information the current object
func (m *VersionMetaData) Serialize(writer i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.SerializationWriter) error {
	{
		err := writer.WriteInt64Value("contentId", m.GetContentId())
		if err != nil {
			return err
		}
	}
	{
		err := writer.WriteStringValue("createdBy", m.GetCreatedBy())
		if err != nil {
			return err
		}
	}
	{
		err := writer.WriteTimeValue("createdOn", m.GetCreatedOn())
		if err != nil {
			return err
		}
	}
	{
		err := writer.WriteStringValue("description", m.GetDescription())
		if err != nil {
			return err
		}
	}
	{
		err := writer.WriteInt64Value("globalId", m.GetGlobalId())
		if err != nil {
			return err
		}
	}
	{
		err := writer.WriteStringValue("groupId", m.GetGroupId())
		if err != nil {
			return err
		}
	}
	{
		err := writer.WriteStringValue("id", m.GetId())
		if err != nil {
			return err
		}
	}
	if m.GetLabels() != nil {
		err := writer.WriteCollectionOfStringValues("labels", m.GetLabels())
		if err != nil {
			return err
		}
	}
	{
		err := writer.WriteStringValue("name", m.GetName())
		if err != nil {
			return err
		}
	}
	{
		err := writer.WriteObjectValue("properties", m.GetProperties())
		if err != nil {
			return err
		}
	}
	if m.GetState() != nil {
		cast := (*m.GetState()).String()
		err := writer.WriteStringValue("state", &cast)
		if err != nil {
			return err
		}
	}
	{
		err := writer.WriteStringValue("type", m.GetTypeEscaped())
		if err != nil {
			return err
		}
	}
	{
		err := writer.WriteStringValue("version", m.GetVersion())
		if err != nil {
			return err
		}
	}
	{
		err := writer.WriteAdditionalData(m.GetAdditionalData())
		if err != nil {
			return err
		}
	}
	return nil
}

// SetAdditionalData sets the AdditionalData property value. Stores additional data not described in the OpenAPI description found when deserializing. Can be used for serialization as well.
func (m *VersionMetaData) SetAdditionalData(value map[string]any) {
	m.additionalData = value
}

// SetContentId sets the contentId property value. The contentId property
func (m *VersionMetaData) SetContentId(value *int64) {
	m.contentId = value
}

// SetCreatedBy sets the createdBy property value. The createdBy property
func (m *VersionMetaData) SetCreatedBy(value *string) {
	m.createdBy = value
}

// SetCreatedOn sets the createdOn property value. The createdOn property
func (m *VersionMetaData) SetCreatedOn(value *i336074805fc853987abe6f7fe3ad97a6a6f3077a16391fec744f671a015fbd7e.Time) {
	m.createdOn = value
}

// SetDescription sets the description property value. The description property
func (m *VersionMetaData) SetDescription(value *string) {
	m.description = value
}

// SetGlobalId sets the globalId property value. The globalId property
func (m *VersionMetaData) SetGlobalId(value *int64) {
	m.globalId = value
}

// SetGroupId sets the groupId property value. An ID of a single artifact group.
func (m *VersionMetaData) SetGroupId(value *string) {
	m.groupId = value
}

// SetId sets the id property value. The ID of a single artifact.
func (m *VersionMetaData) SetId(value *string) {
	m.id = value
}

// SetLabels sets the labels property value. The labels property
func (m *VersionMetaData) SetLabels(value []string) {
	m.labels = value
}

// SetName sets the name property value. The name property
func (m *VersionMetaData) SetName(value *string) {
	m.name = value
}

// SetProperties sets the properties property value. User-defined name-value pairs. Name and value must be strings.
func (m *VersionMetaData) SetProperties(value Propertiesable) {
	m.properties = value
}

// SetState sets the state property value. Describes the state of an artifact or artifact version.  The following statesare possible:* ENABLED* DISABLED* DEPRECATED
func (m *VersionMetaData) SetState(value *ArtifactState) {
	m.state = value
}

// SetTypeEscaped sets the type property value. The type property
func (m *VersionMetaData) SetTypeEscaped(value *string) {
	m.typeEscaped = value
}

// SetVersion sets the version property value. The version property
func (m *VersionMetaData) SetVersion(value *string) {
	m.version = value
}

type VersionMetaDataable interface {
	i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.AdditionalDataHolder
	i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.Parsable
	GetContentId() *int64
	GetCreatedBy() *string
	GetCreatedOn() *i336074805fc853987abe6f7fe3ad97a6a6f3077a16391fec744f671a015fbd7e.Time
	GetDescription() *string
	GetGlobalId() *int64
	GetGroupId() *string
	GetId() *string
	GetLabels() []string
	GetName() *string
	GetProperties() Propertiesable
	GetState() *ArtifactState
	GetTypeEscaped() *string
	GetVersion() *string
	SetContentId(value *int64)
	SetCreatedBy(value *string)
	SetCreatedOn(value *i336074805fc853987abe6f7fe3ad97a6a6f3077a16391fec744f671a015fbd7e.Time)
	SetDescription(value *string)
	SetGlobalId(value *int64)
	SetGroupId(value *string)
	SetId(value *string)
	SetLabels(value []string)
	SetName(value *string)
	SetProperties(value Propertiesable)
	SetState(value *ArtifactState)
	SetTypeEscaped(value *string)
	SetVersion(value *string)
}
