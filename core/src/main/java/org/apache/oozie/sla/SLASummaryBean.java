/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.oozie.sla;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.oozie.client.event.SLAEvent;
import org.apache.oozie.client.rest.JsonBean;
import org.apache.oozie.util.DateUtils;
import org.apache.openjpa.persistence.jdbc.Index;
import org.json.simple.JSONObject;

@Entity
@Table(name = "SLA_SUMMARY")
@NamedQueries({
 @NamedQuery(name = "GET_SLA_SUMMARY", query = "select OBJECT(w) from SLASummaryBean w where w.jobId = :id") })
/**
 * Class to store all the SLA related details (summary) per job
 */
public class SLASummaryBean implements JsonBean {

    @Id
    @Basic
    @Column(name = "job_id")
    private String jobId;

    @Basic
    @Column(name = "user")
    private String user;

    @Basic
    @Index
    @Column(name = "app_name")
    private String appName;

    @Basic
    @Index
    @Column(name = "parent_id")
    private String parentId;

    @Basic
    @Index
    @Column(name = "nominal_time")
    private Timestamp nominalTimeTS = null;

    @Basic
    @Column(name = "expected_start")
    private Timestamp expectedStartTS = null;

    @Basic
    @Column(name = "expected_end")
    private Timestamp expectedEndTS = null;

    @Basic
    @Column(name = "expected_duration")
    private long expectedDuration;

    @Basic
    @Column(name = "actual_start")
    private Timestamp actualStartTS = null;

    @Basic
    @Column(name = "actual_end")
    private Timestamp actualEndTS = null;

    @Basic
    @Column(name = "actual_duration")
    private long actualDuration = -1;

    @Basic
    @Column(name = "job_status")
    private String jobStatus;

    @Basic
    @Column(name = "event_status")
    private String eventStatus;

    @Basic
    @Column(name = "sla_status")
    private String slaStatus;

    @Basic
    @Index
    @Column(name = "sla_processed")
    private byte slaProcessed = 0;

    @Basic
    @Index
    @Column(name = "last_modified")
    private Timestamp lastModifiedTS = null;

    public SLASummaryBean() {
    }

    public SLASummaryBean(SLACalcStatus slaCalc) {
        SLARegistrationBean reg = slaCalc.getSLARegistrationBean();
        setJobId(slaCalc.getJobId());
        setAppName(reg.getAppName());
        setExpectedStart(reg.getExpectedStart());
        setExpectedEnd(reg.getExpectedEnd());
        setExpectedDuration(reg.getExpectedDuration());
        setJobStatus(slaCalc.getJobStatus());
        setSLAStatus(slaCalc.getSLAStatus());
        setEventStatus(slaCalc.getEventStatus());
        setSlaProcessed(slaCalc.getSlaProcessed());
        setLastModifiedTime(slaCalc.getLastModifiedTime());
        setUser(reg.getUser());
        setParentId(reg.getParentId());
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Date getNominalTime() {
        return DateUtils.toDate(nominalTimeTS);
    }

    public void setNominalTime(Date nominalTime) {
        this.nominalTimeTS = DateUtils.convertDateToTimestamp(nominalTime);
    }


    public Date getExpectedStart() {
        return DateUtils.toDate(expectedStartTS);
    }

    public void setExpectedStart(Date expectedStart) {
        this.expectedStartTS = DateUtils.convertDateToTimestamp(expectedStart);
    }

    public Date getExpectedEnd() {
        return DateUtils.toDate(expectedEndTS);
    }

    public void setExpectedEnd(Date expectedEnd) {
        this.expectedEndTS = DateUtils.convertDateToTimestamp(expectedEnd);
    }

    public long getExpectedDuration() {
        return expectedDuration;
    }

    public void setExpectedDuration(long expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    public Date getActualStart() {
        return DateUtils.toDate(actualStartTS);
    }

    public void setActualStart(Date actualStart) {
        this.actualStartTS = DateUtils.convertDateToTimestamp(actualStart);
    }

    public Date getActualEnd() {
        return DateUtils.toDate(actualEndTS);
    }

    public void setActualEnd(Date actualEnd) {
        this.actualEndTS = DateUtils.convertDateToTimestamp(actualEnd);
    }

    public long getActualDuration() {
        return actualDuration;
    }

    public void setActualDuration(long actualDuration) {
        this.actualDuration = actualDuration;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String status) {
        this.jobStatus = status;
    }

    public SLAEvent.EventStatus getEventStatus() {
        return (eventStatus != null ? SLAEvent.EventStatus.valueOf(eventStatus) : null);
    }

    public void setEventStatus(SLAEvent.EventStatus eventStatus) {
        this.eventStatus = (eventStatus != null ? eventStatus.name() : null);
    }

    public SLAEvent.SLAStatus getSLAStatus() {
        return (slaStatus != null ? SLAEvent.SLAStatus.valueOf(slaStatus) : null);
    }

    public void setSLAStatus(SLAEvent.SLAStatus stage) {
        this.slaStatus = (stage != null ? stage.name() : null);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public byte getSlaProcessed() {
        return slaProcessed;
    }

    public void setSlaProcessed(int slaProcessed) {
        this.slaProcessed = (byte) slaProcessed;
    }

    public Date getLastModifiedTime() {
        return DateUtils.toDate(lastModifiedTS);
    }

    public void setLastModifiedTime(Date lastModified) {
        this.lastModifiedTS = DateUtils.convertDateToTimestamp(lastModified);
    }

    @Override
    public JSONObject toJSONObject() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JSONObject toJSONObject(String timeZoneId) {
        // TODO Auto-generated method stub
        return null;
    }

}