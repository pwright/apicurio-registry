/**
 * @license
 * Copyright 2020 JBoss Inc
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

import React from "react";
import "./artifacts.css";
import {Button, Flex, FlexItem, Modal, PageSection, PageSectionVariants, Spinner} from '@patternfly/react-core';
import {ArtifactList} from "./components/artifactList";
import {PageComponent, PageProps, PageState} from "../basePage";
import {ArtifactsPageToolbar, ArtifactsPageToolbarFilterCriteria} from "./components/toolbar";
import {ArtifactsPageEmptyState} from "./components/empty";
import {UploadArtifactForm} from "./components/uploadForm";
import {InvalidContentModal} from "../../components/modals";
import {If} from "../../components/common/if";
import {ArtifactsSearchResults, CreateArtifactData, GetArtifactsCriteria, Paging, Services} from "../../../services";
import {SearchedArtifact} from "../../../models";
import {PleaseWaitModal} from "../../components/modals/pleaseWaitModal";
import {RootPageHeader} from "../../components";


/**
 * Properties
 */
// tslint:disable-next-line:no-empty-interface
export interface ArtifactsPageProps extends PageProps {

}

/**
 * State
 */
export interface ArtifactsPageState extends PageState {
    criteria: ArtifactsPageToolbarFilterCriteria;
    isUploadModalOpen: boolean;
    isUploadFormValid: boolean;
    isInvalidContentModalOpen: boolean;
    isPleaseWaitModalOpen: boolean;
    isSearching: boolean;
    paging: Paging;
    results: ArtifactsSearchResults | null;
    uploadFormData: CreateArtifactData | null;
    invalidContentError: any | null;
}

/**
 * The artifacts page.
 */
export class ArtifactsPage extends PageComponent<ArtifactsPageProps, ArtifactsPageState> {

    constructor(props: Readonly<ArtifactsPageProps>) {
        super(props);
    }

    componentDidUpdate(prevProps: Readonly<ArtifactsPageProps>, prevState: Readonly<ArtifactsPageState>, snapshot?: {}) {
        // @ts-ignore
        if (prevProps.location.search != this.props.location.search) {
            this.setMultiState(this.initializePageState(), () => this.search());
        }
    }

    public renderPage(): React.ReactElement {
        return (
            <React.Fragment>
                <PageSection className="ps_artifacts-header" variant={PageSectionVariants.light} padding={{ default: "noPadding" }}>
                    <RootPageHeader tabKey={0} />
                </PageSection>
                <If condition={this.showToolbar}>
                    <PageSection variant={PageSectionVariants.light} padding={{default : "noPadding"}}>
                        <ArtifactsPageToolbar artifacts={this.results()}
                                              criteria={this.state.criteria}
                                              paging={this.state.paging}
                                              onPerPageSelect={this.onPerPageSelect}
                                              onSetPage={this.onSetPage}
                                              onUploadArtifact={this.onUploadArtifact}
                                              onCriteriaChange={this.onFilterChange}/>
                    </PageSection>
                </If>
                <PageSection variant={PageSectionVariants.default} isFilled={true}>
                    {
                        this.state.isSearching ?
                            <Flex>
                                <FlexItem><Spinner size="lg"/></FlexItem>
                                <FlexItem><span>Searching...</span></FlexItem>
                            </Flex>
                        :
                        this.artifactsCount() === 0 ?
                            <ArtifactsPageEmptyState onUploadArtifact={this.onUploadArtifact} isFiltered={this.isFiltered()}/>
                        :
                            <ArtifactList artifacts={this.artifacts()} onGroupClick={this.onGroupClick} />
                    }
                </PageSection>
                <Modal
                    title="Upload Artifact"
                    variant="large"
                    isOpen={this.state.isUploadModalOpen}
                    onClose={this.onUploadModalClose}
                    className="upload-artifact-modal pf-m-redhat-font"
                    actions={[
                        <Button key="upload" variant="primary" data-testid="modal-btn-upload" onClick={this.doUploadArtifact} isDisabled={!this.state.isUploadFormValid}>Upload</Button>,
                        <Button key="cancel" variant="link" data-testid="modal-btn-cancel" onClick={this.onUploadModalClose}>Cancel</Button>
                    ]}
                >
                    <UploadArtifactForm onChange={this.onUploadFormChange} onValid={this.onUploadFormValid} />
                </Modal>
                <InvalidContentModal error={this.state.invalidContentError}
                                     isOpen={this.state.isInvalidContentModalOpen}
                                     onClose={this.closeInvalidContentModal} />
                <PleaseWaitModal message="Creating artifact, please wait..."
                                 isOpen={this.state.isPleaseWaitModalOpen} />
            </React.Fragment>
        );
    }

    protected initializePageState(): ArtifactsPageState {
        let criteria: ArtifactsPageToolbarFilterCriteria = {
            filterSelection: "name",
            filterValue: "",
            ascending: true
        }
        // @ts-ignore
        const location: any = this.props.location;
        if (location && location.search) {
            const params = new URLSearchParams(location.search);
            if (params.get("group")) {
                criteria = {
                    filterSelection: "group",
                    filterValue: params.get("group") as string,
                    ascending: true
                }
            }
        }
        return {
            criteria,
            invalidContentError: null,
            isInvalidContentModalOpen: false,
            isPleaseWaitModalOpen: false,
            isSearching: false,
            isUploadFormValid: false,
            isUploadModalOpen: false,
            paging: {
                page: 1,
                pageSize: 10
            },
            results: null,
            uploadFormData: null
        };
    }

    // @ts-ignore
    protected createLoaders(): Promise {
        return this.search();
    }

    private onUploadArtifact = (): void => {
        this.setSingleState("isUploadModalOpen", true);
    };

    private onUploadModalClose = (): void => {
        this.setSingleState("isUploadModalOpen", false);
    };

    private onArtifactsLoaded(results: ArtifactsSearchResults): void {
        this.setMultiState({
            isSearching: false,
            results
        });
    }

    private doUploadArtifact = (): void => {
        this.onUploadModalClose();
        this.pleaseWait(true);
        if (this.state.uploadFormData !== null) {
            // If no groupId is provided, set it to the "default" group
            if (!this.state.uploadFormData.groupId) {
                this.state.uploadFormData.groupId = "default";
            }
            Services.getGroupsService().createArtifact(this.state.uploadFormData).then(metaData => {
                const groupId: string = metaData.groupId ? metaData.groupId : "default";
                const artifactLocation: string = this.linkTo(`/artifacts/${ encodeURIComponent(groupId) }/${ encodeURIComponent(metaData.id) }`);
                Services.getLoggerService().info("[ArtifactsPage] Artifact successfully uploaded.  Redirecting to details: ", artifactLocation);
                this.navigateTo(artifactLocation)();
            }).catch( error => {
                this.pleaseWait(false);
                if (error && error.error_code === 400) {
                    this.handleInvalidContentError(error);
                } else {
                    this.handleServerError(error, "Error uploading artifact.");
                }
            });
        }
    };

    private results(): ArtifactsSearchResults {
        return this.state.results ? this.state.results : {
            artifacts: [],
            count: 0,
            page: 1,
            pageSize: 10
        };
    }

    private artifacts(): SearchedArtifact[] {
        return this.state.results ? this.state.results.artifacts : [];
    }

    private artifactsCount(): number {
        return this.state.results ? this.state.results.artifacts.length : 0;
    }

    private onFilterChange = (criteria: ArtifactsPageToolbarFilterCriteria): void => {
        this.setMultiState({
            criteria,
            isSearching: true
        }, () => {
            this.search();
        });
    };

    private isFiltered(): boolean {
        return !!this.state.criteria.filterValue;
    }

    // @ts-ignore
    private search(): Promise {
        const gac: GetArtifactsCriteria = {
            sortAscending: this.state.criteria.ascending,
            type: this.state.criteria.filterSelection,
            value: this.state.criteria.filterValue
        };
        return Services.getGroupsService().getArtifacts(gac, this.state.paging).then(results => {
            this.onArtifactsLoaded(results);
        }).catch(error => {
            this.handleServerError(error, "Error searching for artifacts.");
        });
    }

    private onSetPage = (event: any, newPage: number, perPage?: number): void => {
        const paging: Paging = {
            page: newPage,
            pageSize: perPage ? perPage : this.state.paging.pageSize
        };
        this.setMultiState({
            isSearching: true,
            paging
        }, () => {
            this.search();
        });
    };

    private onPerPageSelect = (event: any, newPerPage: number): void => {
        const paging: Paging = {
            page: this.state.paging.page,
            pageSize: newPerPage
        };
        this.setMultiState({
            isSearching: true,
            paging
        }, () => {
            this.search();
        });
    };

    private onUploadFormValid = (isValid: boolean): void => {
        this.setSingleState("isUploadFormValid", isValid);
    };

    private onUploadFormChange = (data: CreateArtifactData): void => {
        this.setSingleState("uploadFormData", data);
    };

    private closeInvalidContentModal = (): void => {
        this.setSingleState("isInvalidContentModalOpen", false);
    };

    private pleaseWait = (isOpen: boolean): void => {
        this.setSingleState("isPleaseWaitModalOpen", isOpen);
    };

    private handleInvalidContentError(error: any): void {
        Services.getLoggerService().info("[ArtifactsPage] Invalid content error:", error);
        this.setMultiState({
            invalidContentError: error,
            isInvalidContentModalOpen: true
        });
    }

    private onGroupClick = (groupId: string): void => {
        Services.getLoggerService().info("[ArtifactsPage] Filtering by group: ", groupId);
        this.setSingleState("criteria", {
            filterSelection: "group",
            filterValue: groupId,
            ascending: this.state.criteria.ascending
        }, () => {
            this.search();
        });
    };

    private showToolbar = (): boolean => {
        if (this.state.isLoading) {
            return false;
        }
        return true;
    }

}
