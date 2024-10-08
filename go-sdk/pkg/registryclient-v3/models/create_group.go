package models

import (
	i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91 "github.com/microsoft/kiota-abstractions-go/serialization"
)

type CreateGroup struct {
	// Stores additional data not described in the OpenAPI description found when deserializing. Can be used for serialization as well.
	additionalData map[string]any
	// The description property
	description *string
	// An ID of a single artifact group.
	groupId *string
	// User-defined name-value pairs. Name and value must be strings.
	labels Labelsable
}

// NewCreateGroup instantiates a new CreateGroup and sets the default values.
func NewCreateGroup() *CreateGroup {
	m := &CreateGroup{}
	m.SetAdditionalData(make(map[string]any))
	return m
}

// CreateCreateGroupFromDiscriminatorValue creates a new instance of the appropriate class based on discriminator value
// returns a Parsable when successful
func CreateCreateGroupFromDiscriminatorValue(parseNode i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) (i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.Parsable, error) {
	return NewCreateGroup(), nil
}

// GetAdditionalData gets the AdditionalData property value. Stores additional data not described in the OpenAPI description found when deserializing. Can be used for serialization as well.
// returns a map[string]any when successful
func (m *CreateGroup) GetAdditionalData() map[string]any {
	return m.additionalData
}

// GetDescription gets the description property value. The description property
// returns a *string when successful
func (m *CreateGroup) GetDescription() *string {
	return m.description
}

// GetFieldDeserializers the deserialization information for the current model
// returns a map[string]func(i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode)(error) when successful
func (m *CreateGroup) GetFieldDeserializers() map[string]func(i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
	res := make(map[string]func(i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error)
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
	res["labels"] = func(n i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.ParseNode) error {
		val, err := n.GetObjectValue(CreateLabelsFromDiscriminatorValue)
		if err != nil {
			return err
		}
		if val != nil {
			m.SetLabels(val.(Labelsable))
		}
		return nil
	}
	return res
}

// GetGroupId gets the groupId property value. An ID of a single artifact group.
// returns a *string when successful
func (m *CreateGroup) GetGroupId() *string {
	return m.groupId
}

// GetLabels gets the labels property value. User-defined name-value pairs. Name and value must be strings.
// returns a Labelsable when successful
func (m *CreateGroup) GetLabels() Labelsable {
	return m.labels
}

// Serialize serializes information the current object
func (m *CreateGroup) Serialize(writer i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.SerializationWriter) error {
	{
		err := writer.WriteStringValue("description", m.GetDescription())
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
		err := writer.WriteObjectValue("labels", m.GetLabels())
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
func (m *CreateGroup) SetAdditionalData(value map[string]any) {
	m.additionalData = value
}

// SetDescription sets the description property value. The description property
func (m *CreateGroup) SetDescription(value *string) {
	m.description = value
}

// SetGroupId sets the groupId property value. An ID of a single artifact group.
func (m *CreateGroup) SetGroupId(value *string) {
	m.groupId = value
}

// SetLabels sets the labels property value. User-defined name-value pairs. Name and value must be strings.
func (m *CreateGroup) SetLabels(value Labelsable) {
	m.labels = value
}

type CreateGroupable interface {
	i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.AdditionalDataHolder
	i878a80d2330e89d26896388a3f487eef27b0a0e6c010c493bf80be1452208f91.Parsable
	GetDescription() *string
	GetGroupId() *string
	GetLabels() Labelsable
	SetDescription(value *string)
	SetGroupId(value *string)
	SetLabels(value Labelsable)
}
